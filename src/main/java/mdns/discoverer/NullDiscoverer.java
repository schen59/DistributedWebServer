package mdns.discoverer;

/**
 * Discoverer which discovers nothing.
 * @author Shaofeng Chen
 * @since 4/6/14
 */
public class NullDiscoverer extends Discoverer {

    public NullDiscoverer() {
        super();
    }

    @Override
    protected void startQueryer() {}
}
