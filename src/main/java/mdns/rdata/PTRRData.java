package mdns.rdata;

import core.Service;
import core.ServiceInstance;
import mdns.metadata.MetaData;
import mdns.metadata.Name;

import java.nio.ByteBuffer;

/**
 * Class for PTR type rdata object.
 * @author Shaofeng Chen
 * @since 3/29/14
 */
public class PTRRData extends MetaData {

    private final Name name;

    public PTRRData(Name name) {
        this.name = name;
    }

    public Name getName() {
        return name;
    }

    public byte[] toByteArray() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(getByteLength());
        byteBuffer.put(name.toByteArray());
        return byteBuffer.array();
    }

    public int  getByteLength() {
        return name.getByteLength();
    }

    public static PTRRData from(ServiceInstance serviceInstance) {
        return new PTRRData(Name.fromString(serviceInstance.toString(), 4));
    }

    public static PTRRData from(Service service) {
        return new PTRRData(Name.fromString(service.toString(), 3));
    }

    @Override
    public String toString() {
        return name.toString();
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof PTRRData)) {
            return false;
        }
        PTRRData ptrrData = (PTRRData) object;
        if (ptrrData.toString().equalsIgnoreCase(toString())) {
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
