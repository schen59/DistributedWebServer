package mdns.discoverer;

/**
 * Discoverer which implements apple's Suppression of Duplicate Response algorithm.
 * @author Shaofeng Chen
 * @since 4/6/14
 */
public class BonjourDiscoverer extends Discoverer {

    public BonjourDiscoverer() {
        super();
    }

    @Override
    protected void startQueryer() {
        new BonjourQueryer(this).start();
    }
}
