package mdns.discoverer;

import common.DPSException;
import core.Service;
import core.ServiceInstance;
import mdns.DNSMessage;
import mdns.Question;
import mdns.ResourceRecord;
import mdns.builder.DNSMessageBuilder;
import util.UDPSocketUtil;

import java.util.logging.Logger;

import static common.Constants.MDNS_GROUP;
import static common.Constants.MDNS_PORT;
import static common.Messages.QUERYER_INTERRUPT;

/**
 * Queryer which issues service query request by using apple's Suppression of Duplicate Response
 * algorithm.
 * @author Shaofeng Chen
 * @since 4/5/14
 */
public class BonjourQueryer extends Thread {
    private final Discoverer discoverer;
    private int queryInterval;
    private static final Logger logger = Logger.getLogger(BonjourQueryer.class.getName());
    private static final int INIT_QUERY_INTERVAL_MSEC = 1000;
    private static final int BACKOFF_RATIO = 2;

    public BonjourQueryer(Discoverer discoverer) {
        this.discoverer = discoverer;
        queryInterval = INIT_QUERY_INTERVAL_MSEC;
    }

    public void run() {
        while (true) {
            query(MDNS_GROUP, MDNS_PORT);
            try {
                sleep(queryInterval);
                queryInterval *= BACKOFF_RATIO;
            } catch (InterruptedException e) {
                throw new DPSException(QUERYER_INTERRUPT, e);
            }
        }
    }

    public void query(String group, int port) {
        DNSMessage query = buildQuery();
        UDPSocketUtil.send(discoverer.getDatagramSocket(), query, group, port);
        logger.info(String.format("Query for service %s in %s at port %s.", discoverer.getNeededService(),
                group, port));
    }

    private DNSMessage buildQuery() {
        Service neededService = discoverer.getNeededService();
        DNSMessageBuilder dnsMessageBuilder = new DNSMessageBuilder();
        dnsMessageBuilder.asQuery()
                .addQuestion(Question.from(neededService));
        for (ServiceInstance serviceInstance : discoverer.getDiscoveredServiceInstances()) {
            ResourceRecord answer = ResourceRecord.from(serviceInstance);
            dnsMessageBuilder.addAnswer(answer);
        }
        return dnsMessageBuilder.build();
    }
}
