package mdns.advertiser;

import common.DPSException;
import core.ServiceInstance;
import util.UDPSocketUtil;

import java.net.DatagramSocket;

import static common.Constants.MDNS_GROUP;
import static common.Constants.MDNS_PORT;
import static common.Messages.ADVERTISER_INTERRUPT;

/**
 * Common class for advertiser which advertises service instance with MDNS messages.
 * @author Shaofeng Chen
 * @since 3/29/14
 */
public abstract class Advertiser extends Thread {

    protected ServiceInstance serviceInstance;
    private int intervalInMilliSecond;
    protected DatagramSocket socket;
    private static final int INIT_ADVERTISE_INTERVAL_MSEC = 1000;
    private static final int BACKOFF_RATIO = 2;

    public Advertiser() {
        intervalInMilliSecond = INIT_ADVERTISE_INTERVAL_MSEC;
        socket = UDPSocketUtil.createDatagramSocket();
    }

    public void setServiceInstance(ServiceInstance serviceInstance) {
        this.serviceInstance = serviceInstance;
    }

    @Override
    public void run() {
        while (true) {
            try {
                sleep(intervalInMilliSecond);
                intervalInMilliSecond *= BACKOFF_RATIO;
            } catch (InterruptedException e) {
                throw new DPSException(ADVERTISER_INTERRUPT, e);
            }
            advertise(MDNS_GROUP, MDNS_PORT);
        }

    }

    /**
     * Advertise service instance with MDNS message in the given group at the given port.
     * @param group
     * @param port
     */
    protected abstract void advertise(String group, int port);
}
