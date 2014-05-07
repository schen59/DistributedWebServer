package proxyserver;

import core.Service;
import core.ServiceInstance;
import core.ServiceResource;
import mdns.advertiser.Advertiser;
import mdns.advertiser.BonjourAdvertiser;
import mdns.discoverer.BonjourDiscoverer;
import mdns.discoverer.Discoverer;
import mdns.metadata.Name;
import mdns.responder.Responder;
import proxyserver.cache.Cache;
import proxyserver.cache.FIFOCache;
import proxyserver.exceptions.ProxyServerException;
import proxyserver.request.HttpRequest;
import proxyserver.request.HttpRequestHandler;
import proxyserver.response.HttpResponse;
import util.SocketUtil;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

import static common.Messages.NEED_PORT_ARG_INFO;
import static common.Messages.PORT_NUMBER_FMT_ERR;

/**
 * Class for multi-threaded proxy server for http request.
 * @author Shaofeng Chen
 * @since 2/8/14
 */
public class ProxyServer {
    private final int port;
    private ServerSocket serverSocket;
    private final CacheServer cacheServer;
    private static final Logger logger = Logger.getLogger(ProxyServer.class.getName());

    public ProxyServer(int port, CacheServer cacheServer) {
        this.port = port;
        this.cacheServer = cacheServer;
        serverSocket = SocketUtil.createServerSocket(port);
    }

    public synchronized boolean isCached(HttpRequest httpRequest) {
        return cacheServer.isCached(httpRequest);
    }

    public synchronized HttpResponse getCache(HttpRequest httpRequest) {
        return cacheServer.getCache(httpRequest);
    }

    public synchronized void addCache(HttpRequest httpRequest, HttpResponse httpResponse) {
        cacheServer.addCache(httpRequest, httpResponse);
    }

    public HttpResponse getFromCacheCluster(HttpRequest httpRequest) {
        return cacheServer.getFromCluster(httpRequest);
    }

    /**
     * Handle a http request from client.
     * @param client
     */
    private void handle(Socket client) {
        HttpRequestHandler httpRequestHandler = new HttpRequestHandler(client, this);
        httpRequestHandler.start();
    }

    public void run() {
        logger.info("Proxy server running on port:" + port);
        cacheServer.start();
        while (true) {
            Socket client = SocketUtil.acceptClient(serverSocket);
            handle(client);
        }
    }

    public static void main(String[] args) {
        try {
            String name = args[0];
            int cachePort = Integer.parseInt(args[1]);
            int httpPort = Integer.parseInt(args[2]);
            Cache cache = new FIFOCache(1000);
            //Cache cache = new DiskCache("tmp/", new ResponseBuilder());
            //Cache cache = new NullCache();
            Service service = new Service("cs621-cache", "tcp", "local.");
            ServiceInstance serviceInstance = new ServiceInstance(name, service);
            ServiceResource serviceResource = new ServiceResource(serviceInstance,
                    Name.fromString("localhost", 1), cachePort, 1, 1);
            Advertiser advertiser = new BonjourAdvertiser();
            Discoverer discoverer = new BonjourDiscoverer();
            Responder responder = new Responder(advertiser, discoverer);
            responder.register(service);
            responder.register(serviceResource);
            CacheServer cacheServer = new CacheServer(cache, name, cachePort, responder);
            ProxyServer proxyServer = new ProxyServer(httpPort, cacheServer);
            proxyServer.run();
        } catch (ArrayIndexOutOfBoundsException ex) {
            throw new ProxyServerException(NEED_PORT_ARG_INFO, ex);
        } catch (NumberFormatException ex) {
            throw new ProxyServerException(PORT_NUMBER_FMT_ERR, ex);
        }
    }
}
