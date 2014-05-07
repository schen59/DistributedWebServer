package proxyserver;

import core.Service;
import core.ServiceInstance;
import core.ServiceResource;
import mdns.advertiser.Advertiser;
import mdns.advertiser.BonjourAdvertiser;
import mdns.discoverer.BonjourDiscoverer;
import mdns.discoverer.Discoverer;
import mdns.discoverer.ResourceQueryer;
import mdns.metadata.Name;
import mdns.responder.Responder;
import proxyserver.cache.Cache;
import proxyserver.cache.FIFOCache;
import proxyserver.request.CacheRequestHandler;
import proxyserver.request.HttpRequest;
import proxyserver.response.HttpResponse;
import util.SocketUtil;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Set;
import java.util.logging.Logger;

import static common.Constants.MDNS_PORT;
import static common.Constants.MDNS_GROUP;

/**
 * Cache server which is responsible for answering cache request.
 * @author Shaofeng Chen
 * @since 4/6/14
 */
public class CacheServer extends Thread {

    private final String name;
    private final int port;
    private final Cache cache;
    private final ServerSocket serverSocket;
    private final Responder responder;
    private static final Logger logger = Logger.getLogger(CacheServer.class.getName());

    public CacheServer(Cache cache, String name, int port, Responder responder) {
        this.cache = cache;
        this.name = name;
        this.port = port;
        serverSocket = SocketUtil.createServerSocket(port);
        this.responder = responder;
    }

    public boolean isCached(HttpRequest httpRequest) {
        return cache.isCached(httpRequest);
    }

    public HttpResponse getCache(HttpRequest httpRequest) {
        return cache.get(httpRequest);
    }

    public void addCache(HttpRequest httpRequest, HttpResponse httpResponse) {
        cache.add(httpRequest, httpResponse);
    }

    public HttpResponse getFromCluster(HttpRequest httpRequest) {
        Set<ServiceInstance> serviceInstances = responder.getAllDiscoveredServiceInstances();
        HttpResponse httpResponse = null;
        for (ServiceInstance serviceInstance : serviceInstances) {
            if (!serviceInstance.getInstanceName().equalsIgnoreCase(name)) {
                httpResponse = getResponseFrom(httpRequest, serviceInstance);
            }
            if (httpResponse !=null && !httpResponse.isNullResponse()) {
                return httpResponse;
            }
        }
        return httpResponse;
    }

    private HttpResponse getResponseFrom(HttpRequest httpRequest, ServiceInstance serviceInstance) {
        ServiceResource serviceResource = ResourceQueryer.getServiceResource(serviceInstance,
                MDNS_GROUP, MDNS_PORT);
        return httpRequest.execute(serviceResource.getTarget().toString(), serviceResource.getPort());
    }

    public void run() {
        logger.info(String.format("Cache server running on port %s.", port));
        responder.start();
        while (true) {
            Socket client = SocketUtil.acceptClient(serverSocket);
            handler(client);
        }
    }

    public void handler(Socket client) {
        CacheRequestHandler cacheRequestHandler = new CacheRequestHandler(client, this);
        cacheRequestHandler.start();
    }

    public static void main(String[] argv) {
        String name = argv[0];
        int port = Integer.parseInt(argv[1]);
        Service service = new Service("cs621-cache", "tcp", "local.");
        ServiceInstance serviceInstance = new ServiceInstance(name, service);
        ServiceResource serviceResource = new ServiceResource(serviceInstance,
                Name.fromString("localhost", 1), port, 1, 1);
        Advertiser advertiser = new BonjourAdvertiser();
        Discoverer discoverer = new BonjourDiscoverer();
        Responder responder = new Responder(advertiser, discoverer);
        responder.register(service);
        responder.register(serviceResource);
        CacheServer cacheServer = new CacheServer(new FIFOCache(1000), name, port, responder);
        cacheServer.start();
    }
}
