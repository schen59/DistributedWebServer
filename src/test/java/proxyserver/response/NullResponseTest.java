package proxyserver.response;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Test cases for NullResponse class.
 * @author Shaofeng Chen
 * @since 4/7/14
 */
public class NullResponseTest {

    @Test
    public void testGetResponse() {
        Response nullResponse = new NullResponse();
        byte[] data = nullResponse.getResponse();
        assertTrue(new String(data).equalsIgnoreCase("No response."));
    }
}
