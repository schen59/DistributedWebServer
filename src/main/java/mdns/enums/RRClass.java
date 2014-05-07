package mdns.enums;

import static common.Constants.RRCLASS_BYTE_LEN;

/**
 * Class for RRClass enumerate type.
 * @author Shaofeng Chen
 * @since 3/27/14
 */
public class RRClass extends Enumeratable {
    public static final RRClass IN = new RRClass(1, "IN");
    public static final RRClass CH = new RRClass(3, "CH");
    public static final RRClass HS = new RRClass(4, "HS");
    public static final RRClass NONE = new RRClass(254, "NONE");
    public static final RRClass ANY = new RRClass(255, "ANY");

    private RRClass(int value, String name) {
        super(value, name);
    }

    /**
     * Get byte length of RRClass type data, which is unsigned 16 bit.
     * @return int
     */
    public static int getByteLength() {
        return RRCLASS_BYTE_LEN;
    }
}
