package mdns.builder;

import mdns.metadata.Name;
import mdns.rdata.SRVRData;

/**
 * Builder to build SRVRData type object.
 * @author Shaofeng Chen
 * @since 3/29/14
 */
public class SRVRDataBuilder {
    private int priority;
    private int weight;
    private int port;
    private Name target;

    public SRVRDataBuilder withPriority(int priority) {
        this.priority = priority;
        return this;
    }

    public SRVRDataBuilder withWeight(int weight) {
        this.weight = weight;
        return this;
    }

    public SRVRDataBuilder withPort(int port) {
        this.port = port;
        return this;
    }

    public SRVRDataBuilder withTarget(String target) {
        this.target = Name.fromString(target);
        return this;
    }

    public SRVRDataBuilder withTarget(Name target) {
        this.target = target;
        return this;
    }

    public SRVRData build() {
        return new SRVRData(priority, weight, port, target);
    }
}
