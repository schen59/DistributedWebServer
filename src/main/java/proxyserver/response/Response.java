package proxyserver.response;

/**
 * Created by Sherwin on 4/7/14.
 */
public abstract class Response {
    public abstract byte[] getResponse();

    public boolean isNullResponse() {
        byte[] data = getResponse();
        String responseString = new String(data);
        return responseString.equalsIgnoreCase("No response.");
    }
}
