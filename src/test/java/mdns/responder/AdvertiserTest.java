package mdns.responder;

import core.Service;
import core.ServiceInstance;
import mdns.advertiser.Advertiser;
import mdns.advertiser.BonjourAdvertiser;

import org.junit.Ignore;
import org.junit.Test;

/**
 * Test cases for Advertiser class.
 * @author Shaofeng Chen
 * @since 3/29/14
 */
public class AdvertiserTest {

    @Test
    @Ignore("Live test.")
    public void testRun() {
        Service service = new Service("testService", "testProtocal", "testDomain.");
        ServiceInstance serviceInstance = new ServiceInstance("testInstance1", service);
        Advertiser advertiser = new BonjourAdvertiser();
        advertiser.setServiceInstance(serviceInstance);
        advertiser.run();
    }
}
