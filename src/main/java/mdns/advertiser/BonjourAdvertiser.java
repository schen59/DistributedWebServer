package mdns.advertiser;

import mdns.DNSMessage;
import mdns.ResourceRecord;
import mdns.builder.DNSMessageBuilder;
import util.UDPSocketUtil;

import java.util.logging.Logger;

/**
 * Advertiser by using Bonjour exponential backoff algorithm.
 * @author Shaofeng Chen
 * @since 4/6/14
 */
public class BonjourAdvertiser extends Advertiser {

    private static final Logger logger = Logger.getLogger(Advertiser.class.getName());

    public BonjourAdvertiser() {
        super();
    }

    @Override
    protected void advertise(String group, int port) {
        DNSMessage advertisement = buildAdvertisement();
        UDPSocketUtil.send(socket, advertisement, group, port);
        logger.info(String.format("Advertising service instance %s to %s at port %s.",
                serviceInstance, group, port));
    }

    private DNSMessage buildAdvertisement() {
        DNSMessageBuilder dnsMessageBuilder = new DNSMessageBuilder();
        dnsMessageBuilder.asResponse();
        ResourceRecord answer = ResourceRecord.from(serviceInstance);
        dnsMessageBuilder.addAnswer(answer);
        return dnsMessageBuilder.build();
    }
}
