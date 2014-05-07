package proxyserver.response;

import proxyserver.builder.ResponseBuilder;

import java.io.InputStream;

/**
 * Class to deal with http response related behavior.
 * @author Shaofeng Chen
 * @since 2/8/14
 */
public class HttpResponse extends Response {
    private ResponseBuilder responseBuilder;

    public HttpResponse(ResponseBuilder responseBuilder) {
        this.responseBuilder = responseBuilder;
    }

    /**
     * Build http response from input stream.
     * @param inputStream
     */
    public void buildResponse(InputStream inputStream) {
        responseBuilder.build(inputStream);
    }

    public byte[] getResponse() {
        return responseBuilder.getResponse();
    }
}
