package mdns.enums;

/**
 * Class for Rcode enumerate type in header.
 * @author Shaofeng Chen
 * @since 3/27/14
 */
public class Rcode extends Enumeratable {
    public static final Rcode NO_ERROR = new Rcode(0, "NO_ERROR");
    public static final Rcode FORMAT_ERROR = new Rcode(1, "FORMAT_ERROR");
    public static final Rcode SERVER_FAILURE = new Rcode(2, "SERVER_FAILURE");
    public static final Rcode NAME_ERROR = new Rcode(3, "NAME_ERROR");
    public static final Rcode NOT_IMPLEMENTED = new Rcode(4, "NOT_IMPLEMENTED");
    public static final Rcode REFUSED = new Rcode(5, "REFUSED");

    private Rcode(int value, String name) {
        super(value, name);
    }
}
