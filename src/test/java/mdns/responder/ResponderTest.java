package mdns.responder;

import core.Service;
import core.ServiceInstance;
import core.ServiceResource;
import mdns.advertiser.Advertiser;
import mdns.advertiser.BonjourAdvertiser;
import mdns.advertiser.NullAdvertiser;
import mdns.discoverer.BonjourDiscoverer;
import mdns.discoverer.Discoverer;
import mdns.discoverer.NullDiscoverer;
import mdns.metadata.Name;

import org.junit.Ignore;
import org.junit.Test;

/**
 * Test cases for Responder class.
 * @author Shaofeng Chen
 * @since 3/30/14
 */
public class ResponderTest {

    @Test
    @Ignore("Live test.")
    public void testRunNoAdvertiserNoDiscoverer() {
        Service service = new Service("testService", "testProtocal", "testDomain.");
        ServiceInstance serviceInstance = new ServiceInstance("instance2", service);
        ServiceResource serviceResource = new ServiceResource(serviceInstance,
                Name.fromString("localhost", 1), 8888, 1, 1);
        Advertiser advertiser = new NullAdvertiser();
        Discoverer discoverer = new NullDiscoverer();
        Responder responder = new Responder(advertiser, discoverer);
        responder.register(service);
        responder.register(serviceResource);
        responder.run();
    }

    @Test
    @Ignore("Live test.")
    public void testRunWithAdvertiser() {
        Service service = new Service("testService", "testProtocal", "testDomain.");
        ServiceInstance serviceInstance = new ServiceInstance("instance1", service);
        ServiceResource serviceResource = new ServiceResource(serviceInstance,
                Name.fromString("localhost", 1), 8888, 1, 1);
        Advertiser advertiser = new BonjourAdvertiser();
        Discoverer discoverer = new NullDiscoverer();
        Responder responder = new Responder(advertiser, discoverer);
        responder.register(serviceResource);
        responder.run();
    }

    @Test
    @Ignore("Live test.")
    public void testRunWithDiscoverer() {
        Service service = new Service("testService", "testProtocal", "testDomain.");
        ServiceInstance serviceInstance = new ServiceInstance("instance1", service);
        ServiceResource serviceResource = new ServiceResource(serviceInstance,
                Name.fromString("localhost", 1), 8888, 1, 1);
        Advertiser advertiser = new NullAdvertiser();
        Discoverer discoverer = new BonjourDiscoverer();
        Responder responder = new Responder(advertiser, discoverer);
        responder.register(service);
        responder.register(serviceResource);
        responder.run();
    }

    @Test
    @Ignore("Live test.")
    public void testRunWithAdvertiserDiscoverer() {
        Service service = new Service("testService", "testProtocal", "testDomain.");
        ServiceInstance serviceInstance = new ServiceInstance("instance2", service);
        ServiceResource serviceResource = new ServiceResource(serviceInstance,
                Name.fromString("localhost", 1), 8888, 1, 1);
        Advertiser advertiser = new BonjourAdvertiser();
        Discoverer discoverer = new BonjourDiscoverer();
        Responder responder = new Responder(advertiser, discoverer);
        responder.register(service);
        responder.register(serviceResource);
        responder.run();
    }
}
