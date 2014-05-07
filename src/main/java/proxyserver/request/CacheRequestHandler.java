package proxyserver.request;

import proxyserver.CacheServer;
import proxyserver.builder.RequestBuilder;
import proxyserver.builder.ResponseBuilder;
import proxyserver.exceptions.ProxyServerException;
import proxyserver.response.NullResponse;
import proxyserver.response.Response;
import util.SocketUtil;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Logger;

import static common.Messages.REPLY_CLIENT_ERR;

/**
 * Class which is responsible for handling cache request.
 * @author Shaofeng Chen
 * @since 4/17/14
 */
public class CacheRequestHandler extends Thread {

    private Socket client;
    private CacheServer cacheServer;
    private static final Logger logger = Logger.getLogger(CacheRequestHandler.class.getName());

    public CacheRequestHandler(Socket client, CacheServer cacheServer) {
        this.client = client;
        this.cacheServer = cacheServer;
    }

    public void run() {
        RequestBuilder requestBuilder = new RequestBuilder();
        ResponseBuilder responseBuilder = new ResponseBuilder();
        HttpRequest httpRequest = new HttpRequest(requestBuilder, responseBuilder);
        httpRequest.buildRequest(SocketUtil.getInputStream(client));
        logger.info(String.format("Handle cache request: %s.", httpRequest.getUri()));
        Response response;
        if (cacheServer.isCached(httpRequest)) {
            response = cacheServer.getCache(httpRequest);
            logger.info(String.format("Serve cache request %s from cache.",
                    httpRequest.getUri()));
        } else {
            response = new NullResponse();
            logger.info(String.format("Miss cache request %s.", httpRequest.getUri()));
        }
        replyToClient(response);
        SocketUtil.closeSocket(client);
    }

    /**
     * Return the http response to original socket client.
     * @param httpResponse
     */
    private void replyToClient(Response httpResponse) {
        try {
            DataOutputStream outputStream = new DataOutputStream(SocketUtil.getOutputStream
                    (client));
            outputStream.write(httpResponse.getResponse());
        } catch (IOException ex) {
            throw new ProxyServerException(REPLY_CLIENT_ERR, ex);
        }
    }
}
