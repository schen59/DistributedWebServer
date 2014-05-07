package mdns;

import core.Service;
import core.ServiceInstance;
import mdns.enums.RRClass;
import mdns.enums.RRType;
import mdns.metadata.MetaData;
import mdns.metadata.Name;

import java.nio.ByteBuffer;

/**
 * Class for dns question.
 * @author Shaofeng Chen
 * @since 3/28/14
 */
public class Question extends MetaData {
    private final Name name;
    private final RRType queryType;
    private final RRClass queryClass;

    public Question(Name name, RRType queryType) {
        this.name = name;
        this.queryType = queryType;
        queryClass = RRClass.IN;
    }

    public Question(Name name, RRType queryType, RRClass queryClass) {
        this.name = name;
        this.queryType = queryType;
        this.queryClass = queryClass;
    }

    public static Question from(Service service) {
        return new Question(Name.fromString(service.toString(), 3), RRType.PTR, RRClass.IN);
    }

    public static Question from(ServiceInstance serviceInstance) {
        return new Question(Name.fromString(serviceInstance.toString(), 4), RRType.SRV,
                RRClass.IN);
    }

    public Name getName() {
        return name;
    }

    public RRType getQueryType() {
        return queryType;
    }

    public RRClass getQueryClass() {
        return queryClass;
    }

    public byte[] toByteArray() {
        int bytesLength = getByteLength();
        ByteBuffer byteBuffer = ByteBuffer.allocate(bytesLength);
        byteBuffer.put(name.toByteArray());
        byteBuffer.putShort((short) queryType.getValue());
        byteBuffer.putShort((short) queryClass.getValue());
        return byteBuffer.array();
    }

    public int getByteLength() {
        return name.getByteLength() + RRType.getByteLength() +
                RRClass.getByteLength();
    }
}
