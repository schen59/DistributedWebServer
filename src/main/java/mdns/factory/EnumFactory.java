package mdns.factory;

import mdns.enums.Opcode;
import mdns.enums.RRClass;
import mdns.enums.RRType;
import mdns.enums.Rcode;
import common.DPSException;

import static common.Messages.UNKNOWN_RR_TYPE;
import static common.Messages.UNKNOWN_RR_CLASS;
import static common.Messages.UNKNOWN_OPCODE;
import static common.Messages.UNKNOWN_RCODE;
import static common.Constants.*;

/**
 * Factory class to create enumerate type object from integer value.
 * @author Shaofeng Chen
 * @since 2/22/14
 */
public class EnumFactory {

    /**
     * Create RRType object from integer value.
     * @param type
     * @return RRType
     */
    public static RRType createRRType(int type) {
        switch (type) {
            case RRTYPE_A:
                return RRType.A;
            case RRTYPE_NS:
                return RRType.NS;
            case RRTYPE_CNAME:
                return RRType.CNAME;
            case RRTYPE_SOA:
                return RRType.SOA;
            case RRTYPE_WKS:
                return RRType.WKS;
            case RRTYPE_PTR:
                return RRType.PTR;
            case RRTYPE_MX:
                return RRType.MX;
            case RRTYPE_AAAA:
                return RRType.AAAA;
            case RRTYPE_SRV:
                return RRType.SRV;
            case RRTYPE_A6:
                return RRType.A6;
            case RRTYPE_ANY:
                return RRType.ANY;
            default:
                throw new DPSException(String.format(UNKNOWN_RR_TYPE, type));
        }
    }

    /**
     * Create RRType object from string value.
     * @param type
     * @return RRType
     */
    public static RRType createRRType(String type) {
        if (type.equalsIgnoreCase(QTYPE_A)) {
            return RRType.A;
        } else if (type.equalsIgnoreCase(QTYPE_NS)) {
            return RRType.NS;
        } else if (type.equalsIgnoreCase(QTYPE_MX)) {
            return RRType.MX;
        } else if (type.equalsIgnoreCase(QTYPE_SOA)) {
            return RRType.SOA;
        } else {
            throw new DPSException(String.format(UNKNOWN_RR_TYPE, type));
        }
    }

    /**
     * Create RRClass type object from integer value.
     * @param value
     * @return RRClass
     */
    public static RRClass createRRClass(int value) {
        switch (value) {
            case RRCLASS_IN:
                return RRClass.IN;
            case RRCLASS_CH:
                return RRClass.CH;
            case RRCLASS_HS:
                return RRClass.HS;
            case RRCLASS_NONE:
                return RRClass.NONE;
            case RRCLASS_ANY:
                return RRClass.ANY;
            default:
                throw new DPSException(String.format(UNKNOWN_RR_CLASS, value));
        }
    }

    /**
     * Create Opcode type object from integer value.
     * @param value
     * @return Opcode
     */
    public static Opcode createOpcode(int value) {
        switch (value) {
            case OPCODE_QUERY:
                return Opcode.QUERY;
            case OPCODE_IQUERY:
                return Opcode.IQUERY;
            case OPCODE_STATUS:
                return Opcode.STATUS;
            default:
                throw new DPSException(String.format(UNKNOWN_OPCODE, value));
        }
    }

    /**
     * Create Rcode type object from integer value.
     * @param value
     * @return Rcode
     */
    public static Rcode createRcode(int value) {
        switch (value) {
            case RCODE_NO_ERROR:
                return Rcode.NO_ERROR;
            case RCODE_FMT_ERROR:
                return Rcode.FORMAT_ERROR;
            case RCODE_SERVER_FAILURE:
                return Rcode.SERVER_FAILURE;
            case RCODE_NAME_ERROR:
                return Rcode.NAME_ERROR;
            case RCODE_NOT_IMPLEMENTED:
                return Rcode.NOT_IMPLEMENTED;
            case RCODE_REFUSED:
                return Rcode.REFUSED;
            default:
                throw new DPSException(String.format(UNKNOWN_RCODE, value));
        }
    }
}
