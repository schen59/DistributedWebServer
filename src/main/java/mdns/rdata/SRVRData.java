package mdns.rdata;

import core.ServiceResource;
import mdns.metadata.MetaData;
import mdns.metadata.Name;

import java.nio.ByteBuffer;

/**
 * Class for SRV type rdata object.
 * @author Shaofeng Chen
 * @since 3/28/14
 */
public class SRVRData extends MetaData {
    private final int priority;
    private final int weight;
    private final int port;
    private final Name target;

    public SRVRData(int priority, int weight, int port, Name target) {
        this.priority = priority;
        this.weight = weight;
        this.port = port;
        this.target = target;
    }

    public static SRVRData from(ServiceResource serviceResource) {
        return new SRVRData(serviceResource.getPriority(), serviceResource.getWeight(),
                serviceResource.getPort(), serviceResource.getTarget());
    }

    public int getPriority() {
        return priority;
    }

    public int getWeight() {
        return weight;
    }

    public int getPort() {
        return port;
    }

    public Name getTarget() {
        return target;
    }

    public byte[] toByteArray() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(getByteLength());
        byteBuffer.putShort((short) priority);
        byteBuffer.putShort((short) weight);
        byteBuffer.putShort((short) port);
        byteBuffer.put(target.toByteArray());
        return byteBuffer.array();
    }

    public int getByteLength() {
        return target.getByteLength() + 6;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof SRVRData)) {
            return false;
        }
        SRVRData srvrData = (SRVRData) object;
        if (srvrData.getPort() == port && srvrData.getTarget().toString().equalsIgnoreCase(target
                .toString())) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return target.toString().hashCode();
    }
}
