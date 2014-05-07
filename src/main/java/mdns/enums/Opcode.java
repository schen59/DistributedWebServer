package mdns.enums;

/**
 * Class for Opcode enumerate type in header.
 * @author Shaofeng Chen
 * @since 3/17/14
 */
public class Opcode extends Enumeratable {
    public static final Opcode QUERY = new Opcode(0, "QUERY");
    public static final Opcode IQUERY = new Opcode(1, "IQUERY");
    public static final Opcode STATUS = new Opcode(2, "STATUS");

    private Opcode(int value, String name) {
        super(value, name);
    }
}
