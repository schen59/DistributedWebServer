package common;

/**
 * Common exception class for distributed web server project.
 * @author Shaofeng Chen
 * @since 3/28/14
 */
public class DPSException extends RuntimeException {
    public DPSException(String message) {
        super(message);
    }

    public DPSException(String message, Throwable cause) {
        super(message, cause);
    }

    public DPSException(Throwable cause) {
        super(cause);
    }
}
