package mdns.discoverer;

import common.RawMessage;
import core.Service;
import core.ServiceInstance;
import mdns.DNSMessage;
import mdns.ResourceRecord;
import mdns.factory.MetadataFactory;
import util.UDPSocketUtil;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.logging.Logger;

/**
 * Discoverer class which is responsible for discovering service instances of a specific service
 * type.
 * @author Shaofeng Chen
 * @since 3/30/14
 */
public abstract class Discoverer extends Thread {
    protected Service neededService;
    protected final DatagramSocket socket;
    private final Set<ServiceInstance> discoveredServiceInstances;
    private static final Logger logger = Logger.getLogger(Discoverer.class.getName());

    public Discoverer() {
        socket = UDPSocketUtil.createDatagramSocket();
        discoveredServiceInstances = new CopyOnWriteArraySet<ServiceInstance>();
    }

    public void run() {
        startQueryer();
        while(true) {
            DatagramPacket packet = UDPSocketUtil.receiveFrom(socket);
            DNSMessage dnsMessage = MetadataFactory.createDNSMessage(new RawMessage(packet
                    .getData()));
            Set<ResourceRecord> answers = dnsMessage.getAnswers();
            for (ResourceRecord answer : answers) {
                ServiceInstance serviceInstance = ServiceInstance.from(answer);
                discoveredServiceInstances.add(serviceInstance);
                logger.info(String.format("Discovered service instance %s.", serviceInstance));
            }
        }
    }

    public void setNeededService(Service neededService) {
        this.neededService = neededService;
    }

    public DatagramSocket getDatagramSocket() {
        return socket;
    }

    public Service getNeededService() {
        return neededService;
    }

    public Set<ServiceInstance> getDiscoveredServiceInstances() {
        return discoveredServiceInstances;
    }

    /**
     * Start queryer to query for service instances.
     */
    protected abstract void startQueryer();
}
