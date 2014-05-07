package mdns.discoverer;

import common.DPSException;
import common.RawMessage;
import core.ServiceInstance;
import core.ServiceResource;
import mdns.DNSMessage;
import mdns.Question;
import mdns.ResourceRecord;
import mdns.builder.DNSMessageBuilder;
import mdns.factory.MetadataFactory;
import util.UDPSocketUtil;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Set;

import static common.Messages.GET_SERVICE_RESOURCE_ERR;

/**
 * Queryer which is responsible for getting service resource from given service instance.
 * @author Shaofeng Chen
 * @since 4/6/14
 */
public class ResourceQueryer {

    public static ServiceResource getServiceResource(ServiceInstance serviceInstance,
            String group, int port) {
        DatagramSocket socket = UDPSocketUtil.createDatagramSocket();
        DNSMessage query = buildQuery(serviceInstance);
        UDPSocketUtil.send(socket, query, group, port);
        DatagramPacket packet = UDPSocketUtil.receiveFrom(socket);
        DNSMessage result = MetadataFactory.createDNSMessage(new RawMessage(packet.getData()));
        Set<ResourceRecord> answers = result.getAnswers();
        if (answers.size() != 1) {
            throw new DPSException(String.format(GET_SERVICE_RESOURCE_ERR, serviceInstance));
        }
        ResourceRecord[] answersArray = answers.toArray(new ResourceRecord[answers.size()]);
        return ServiceResource.from(answersArray[0]);
    }

    private static DNSMessage buildQuery(ServiceInstance serviceInstance) {
        DNSMessageBuilder dnsMessageBuilder = new DNSMessageBuilder();
        return dnsMessageBuilder.asQuery()
                .addQuestion(Question.from(serviceInstance))
                .build();
    }
}
