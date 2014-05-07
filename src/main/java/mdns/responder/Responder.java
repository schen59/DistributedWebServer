package mdns.responder;

import common.DPSException;
import core.Service;
import core.ServiceInstance;
import core.ServiceResource;
import mdns.Question;
import mdns.ResourceRecord;
import mdns.advertiser.Advertiser;
import mdns.discoverer.Discoverer;
import mdns.enums.RRType;
import mdns.factory.RDataFactory;
import mdns.metadata.MetaData;
import util.UDPSocketUtil;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.SocketAddress;
import java.net.StandardProtocolFamily;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.logging.Logger;

import static common.Constants.MDNS_GROUP;
import static common.Constants.MDNS_PORT;
import static common.Messages.RCV_MSG_ERR;
import static common.Messages.CREATE_CHANNEL_ERR;
import static common.Messages.JOIN_GROUP_ERR;

/**
 * Class which is responsible for service advertising and discovering.
 * @author Shaofeng Chen
 * @since 3/29/14
 */
public class Responder extends Thread {

    private final Advertiser advertiser;
    private final Discoverer discoverer;
    private Service neededService;
    private ServiceResource registeredServiceResource;
    private Set<ServiceInstance> discoveredServiceInstances;
    private DatagramChannel datagramChannel;
    private static final Logger logger = Logger.getLogger(Responder.class.getName());

    public Responder(Advertiser advertiser, Discoverer discoverer) {
        this.advertiser = advertiser;
        this.discoverer = discoverer;
        discoveredServiceInstances = new CopyOnWriteArraySet<ServiceInstance>();
        createMulticastDatagramChannel(MDNS_PORT);
    }

    public void register(ServiceResource serviceResource) {
        registeredServiceResource = serviceResource;
        advertiser.setServiceInstance(registeredServiceResource.getServiceInstance());
    }

    public void register(Service neededService) {
        this.neededService = neededService;
        discoverer.setNeededService(this.neededService);
    }

    public Set<ServiceInstance> getAllDiscoveredServiceInstances() {
        Set<ServiceInstance> allDiscoveredServiceInstances = new HashSet<ServiceInstance>();
        allDiscoveredServiceInstances.addAll(discoveredServiceInstances);
        allDiscoveredServiceInstances.addAll(discoverer.getDiscoveredServiceInstances());
        return allDiscoveredServiceInstances;
    }

    public boolean canAnswerQuestion(Question question) {
        if (registeredServiceResource == null) {
            return false;
        }
        RRType rrType = question.getQueryType();
        if (rrType.equals(RRType.PTR)) {
            Service queriedService = Service.from(question.getName().toString());
            return registeredServiceResource.provides(queriedService);
        } else if (rrType.equals(RRType.SRV)) {
            ServiceInstance serviceInstance = ServiceInstance.from(question.getName().toString());
            return registeredServiceResource.provides(serviceInstance);
        } else {
            return false;
        }
    }

    public ResourceRecord answerQuestion(Question question) {
        MetaData rData = RDataFactory.createRDataWithType(registeredServiceResource,
                question.getQueryType());
        ResourceRecord answer = new ResourceRecord(question.getName(),
                question.getQueryType(), question.getQueryClass(), 0, rData.getByteLength(),
                rData);
        return answer;
    }

    public void addAnswer(ResourceRecord answer) {
        if (answer.getRRType().equals(RRType.PTR)) {
            ServiceInstance serviceInstance = ServiceInstance.from(answer);
            discoveredServiceInstances.add(serviceInstance);
        }
    }

    public void run() {
        if (advertiser != null) {
            advertiser.start();
        }
        if (discoverer != null) {
            discoverer.start();
        }
        while (true) {
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            SocketAddress remoteAddress = receiveDNSMessage(byteBuffer);
            handleMessage(byteBuffer.array(), remoteAddress);
            logger.info(String.format("Discovered service instances: %s",
                    StringUtils.join(getAllDiscoveredServiceInstances(), ",")));
        }
    }

    private SocketAddress receiveDNSMessage(ByteBuffer byteBuffer) {
        try {
            SocketAddress remoteAddress = datagramChannel.receive(byteBuffer);
            return remoteAddress;
        } catch (IOException ex) {
            throw new DPSException(RCV_MSG_ERR, ex);
        }
    }

    private void createMulticastDatagramChannel(int port) {
        NetworkInterface ni = UDPSocketUtil.getNetworkInterface("en0");
        try {
            datagramChannel = DatagramChannel.open(StandardProtocolFamily.INET)
                    .setOption(StandardSocketOptions.SO_REUSEADDR, true)
                    .bind(new InetSocketAddress(port))
                    .setOption(StandardSocketOptions.IP_MULTICAST_IF, ni);
        } catch (IOException ex) {
            throw new DPSException(String.format(CREATE_CHANNEL_ERR, port));
        }
        joinGroup(MDNS_GROUP, ni);
    }

    private void joinGroup(String group, NetworkInterface ni) {
        InetAddress groupInetAddress = UDPSocketUtil.inetAddressFromString(group);
        try {
            datagramChannel.join(groupInetAddress, ni);
        } catch (IOException ex) {
            throw new DPSException(String.format(JOIN_GROUP_ERR, group, ni.getName()), ex);
        }
    }

    private void handleMessage(byte[] message, SocketAddress remoteAddress) {
        new MessageHandler(message, remoteAddress, this).start();
    }
}
