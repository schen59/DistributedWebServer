package util;

import common.DPSException;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static common.Messages.UNKNOWN_HOST;

/**
 * Common utility functions.
 * @author Shaofeng Chen
 * @since 2/18/14
 */
public class CommonUtil {

    /**
     * Convert two bytes into unsigned 16 bits integer.
     * @param high
     * @param low
     * @return int
     */
    public static int toU16From(byte high, byte low) {
        int b1 = getU8From(high);
        int b2 = getU8From(low);
        return (b1 << 8) + b2;
    }

    /**
     * Convert integer to unsigned 16 bits integer.
     * @param value
     * @return int
     */
    public static int getU16From(int value) {
        return value & 0xffff;
    }

    /**
     * Convert byte to unsigned 8 bits integer.
     * @param b
     * @return int
     */
    public static int getU8From(byte b) {
        return b & 0xff;
    }

    /**
     * Convert four bytes to unsigned 32 bits integer.
     * @param first
     * @param second
     * @param third
     * @param fourth
     * @return long
     */
    public static  long toU32From(byte first, byte second, byte third, byte fourth) {
        int high = toU16From(first, second);
        int low = toU16From(third, fourth);
        return (high << 16) + low;
    }

    /**
     * Get InetAddress for specified server name.
     * @param serverName
     * @return InetAddress
     */
    public static InetAddress getInetAddressFrom(String serverName) {
        try {
            return InetAddress.getByName(serverName);
        } catch (UnknownHostException ex) {
            throw new DPSException(String.format(UNKNOWN_HOST, serverName), ex);
        }
    }

    /**
     * Check if the specified integer is pointer.
     * @param b
     * @return boolean
     */
    public static boolean isPointer(int b) {
        return b >= 192;
    }

    public static boolean isLabelEnds(int b) {
        return b == 0;
    }

    /**
     * Get components for query name.
     * @param name
     * @return String[]
     */
    public static String[] getComponents(String name, int limit) {
        return name.split("\\.", limit);
    }

    public static String getDateString() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static void validateDomain(String domain) {
        if (!domain.endsWith(".")) {
            throw new DPSException(String.format("Illegal domain %s, " +
                    "domain should end with '.'.", domain));
        }
    }
}
