package mdns.responder;

import core.Service;
import mdns.discoverer.BonjourDiscoverer;
import mdns.discoverer.Discoverer;

import org.junit.Ignore;
import org.junit.Test;

/**
 * Created by Sherwin on 3/30/14.
 */
public class DiscovererTest {

    @Test
    @Ignore("Live test.")
    public void testRun() {
        Service service = new Service("testService", "testProtocal", "testDomain.");
        Discoverer discoverer = new BonjourDiscoverer();
        discoverer.setNeededService(service);
        discoverer.run();
    }
}
