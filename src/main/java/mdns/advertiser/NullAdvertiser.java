package mdns.advertiser;

/**
 * Advertiser which advertises nothing.
 * @author Shaofeng Chen
 * @since 4/6/14
 */
public class NullAdvertiser extends Advertiser {

    public NullAdvertiser() {
        super();
    }

    protected void advertise(String group, int port) {}
}
