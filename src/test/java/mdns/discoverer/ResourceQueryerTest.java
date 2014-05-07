package mdns.discoverer;

import core.Service;
import core.ServiceInstance;
import core.ServiceResource;

import org.junit.Ignore;
import org.junit.Test;

/**
 * Test cases for ResourceQueryer.
 * @author Shaofeng Chen
 * @since 4/6/14
 */
public class ResourceQueryerTest {
    @Test
    @Ignore("Live test.")
    public void testGetResourceService() {
        Service service = new Service("testService", "testProtocal", "testDomain.");
        ServiceInstance serviceInstance = new ServiceInstance("instance2", service);
        ServiceResource serviceResource = ResourceQueryer.getServiceResource(serviceInstance,
                "224.0.0.1", 5353);
        System.out.println(serviceResource.getServiceInstance());
        System.out.println(serviceResource.getTarget());
    }
}
