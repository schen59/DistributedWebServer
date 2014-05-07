package mdns;

import core.Service;
import core.ServiceInstance;
import mdns.enums.RRClass;
import mdns.enums.RRType;
import mdns.metadata.MetaData;
import mdns.metadata.Name;
import mdns.rdata.PTRRData;

import java.nio.ByteBuffer;

/**
 * Class for dns resource record.
 * @author Shaofeng Chen
 * @since 3/28/14
 */
public class ResourceRecord extends MetaData {
    private final Name name;
    private final RRType rrType;
    private final RRClass rrClass;
    private final long ttl;
    private final int rLength;
    private final MetaData rData;

    public ResourceRecord(Name name, RRType rrType, RRClass rrClass, long ttl,
            int rLength, MetaData rData) {
        this.name = name;
        this.rrType = rrType;
        this.rrClass = rrClass;
        this.ttl = ttl;
        this.rLength = rLength;
        this.rData = rData;
    }

    public static ResourceRecord from(ServiceInstance serviceInstance) {
        Service service = serviceInstance.getService();
        MetaData rData = PTRRData.from(serviceInstance);
        return new ResourceRecord(Name.fromString(service.toString(), 3), RRType.PTR,
                RRClass.IN, 0, rData.getByteLength(), rData);
    }

    public Name getName() {
        return name;
    }

    public RRType getRRType() {
        return rrType;
    }

    public RRClass getRRClass() {
        return rrClass;
    }

    public long getTtl() {
        return ttl;
    }

    public MetaData getRData() {
        return rData;
    }

    public byte[] toByteArray() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(getByteLength());
        byteBuffer.put(name.toByteArray());
        byteBuffer.putShort((short) rrType.getValue());
        byteBuffer.putShort((short) rrClass.getValue());
        byteBuffer.putInt((int) ttl);
        byteBuffer.putShort((short) rLength);
        byteBuffer.put(rData.toByteArray());
        return byteBuffer.array();
    }

    public int getByteLength() {
        return name.getByteLength() + RRType.getByteLength() + RRClass.getByteLength() + rData
                .getByteLength() + 6;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof ResourceRecord)) {
            return false;
        }
        ResourceRecord resourceRecord = (ResourceRecord) object;
        if (resourceRecord == this || resourceRecord.getName().toString().equalsIgnoreCase(name
                .toString()) && resourceRecord.getRData().equals(rData)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return name.toString().hashCode();
    }
}
