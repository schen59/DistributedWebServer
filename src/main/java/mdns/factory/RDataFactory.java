package mdns.factory;

import common.DPSException;
import common.RawMessage;
import core.ServiceResource;
import mdns.builder.SRVRDataBuilder;
import mdns.enums.RRType;
import mdns.metadata.MetaData;
import mdns.metadata.Name;
import mdns.rdata.PTRRData;
import mdns.rdata.SRVRData;

import static common.Constants.RRTYPE_PTR;
import static common.Constants.RRTYPE_SRV;
import static common.Messages.UNKNOWN_RR_TYPE;
import static common.Messages.CREATE_RDATA_ERR;

/**
 * Factory to create rdata object.
 * @author Shaofeng Chen
 * @since 3/31/14
 */
public class RDataFactory {

    public static MetaData createRDataWithType(ServiceResource serviceResource, RRType rrType) {
        if (rrType.equals(RRType.PTR)) {
            return PTRRData.from(serviceResource.getServiceInstance());
        } else if (rrType.equals(RRType.SRV)) {
            return SRVRData.from(serviceResource);
        } else {
            throw new DPSException(String.format(CREATE_RDATA_ERR, rrType));
        }
    }

    public static MetaData createRDataWithType(RawMessage rawMessage, RRType rrType) {
        switch (rrType.getValue()) {
            case RRTYPE_PTR:
                return createPTRRData(rawMessage);
            case RRTYPE_SRV:
                return createSRVRData(rawMessage);
            default:
                throw new DPSException(String.format(UNKNOWN_RR_TYPE, rrType));
        }
    }

    private static PTRRData createPTRRData(RawMessage rawMessage) {
        Name name = MetadataFactory.createName(rawMessage);
        return new PTRRData(name);
    }

    private static SRVRData createSRVRData(RawMessage rawMessage) {
        int priority = rawMessage.getU16();
        int weight = rawMessage.getU16();
        int port = rawMessage.getU16();
        Name target = MetadataFactory.createName(rawMessage);
        SRVRDataBuilder srvrDataBuilder = new SRVRDataBuilder();
        return srvrDataBuilder.withPriority(priority)
                .withWeight(weight)
                .withPort(port)
                .withTarget(target)
                .build();
    }
}
