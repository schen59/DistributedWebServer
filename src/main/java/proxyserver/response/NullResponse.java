package proxyserver.response;

/**
 * Created by Sherwin on 4/7/14.
 */
public class NullResponse extends Response {

    public byte[] getResponse() {
        String noResponse = "No response.";
        return noResponse.getBytes();
    }
}
