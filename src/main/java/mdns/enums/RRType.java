package mdns.enums;

import static common.Constants.RRTYPE_BYTE_LEN;

/**
 * Class for RRType enumerate type.
 * @author Shaofeng Chen
 * @since 3/27/14
 */
public class RRType extends Enumeratable {
    public static final RRType A = new RRType(1, "A");
    public static final RRType NS = new RRType(2, "NS");
    public static final RRType CNAME = new RRType(5, "CNAME");
    public static final RRType SOA = new RRType(6, "SOA");
    public static final RRType WKS = new RRType(11, "WKS");
    public static final RRType PTR = new RRType(12, "PTR");
    public static final RRType MX = new RRType(15, "MX");
    public static final RRType AAAA = new RRType(28, "AAAA");
    public static final RRType SRV = new RRType(33, "SRV");
    public static final RRType A6 = new RRType(38, "A6");
    public static final RRType ANY = new RRType(255, "ANY");

    private RRType(int value, String name) {
        super(value, name);
    }

    /**
     * Get byte length of RRType data, which is unsigned 16 bit.
     * @return int
     */
    public static int getByteLength() {
        return RRTYPE_BYTE_LEN;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof RRType)) {
            return false;
        }
        RRType rrType = (RRType) object;
        if (rrType == this || rrType.getValue() == getValue()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return getName().hashCode();
    }
}
