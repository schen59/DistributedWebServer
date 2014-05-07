package common;

import util.CommonUtil;

import static common.Messages.EOF_ERR;

/**
 * Class which holds the byte stream of the response data.
 * @author Shaofeng Chen
 * @since 2/21/14
 */
public class RawMessage {
    private byte[] data;
    private int cursor;

    public RawMessage(byte[] data) {
        this.data = data;
        cursor = 0;
    }

    /**
     * Get one byte from the response data stream.
     * @return byte
     */
    public byte getByte() {
        checkEnoughDataLeft(1);
        return data[cursor++];
    }

    /**
     * Get unsigned 8 bits from the response data stream.
     * @return int
     */
    public int getU8() {
        checkEnoughDataLeft(1);
        return CommonUtil.getU8From(data[cursor++]);
    }

    /**
     * Get unsigned 8 bits from the response data stream without changing the cursor.
     * @return int
     */
    public int peekU8() {
        checkEnoughDataLeft(0);
        return CommonUtil.getU8From(data[cursor]);
    }

    /**
     * Get unsigned 16 bits from the response data stream.
     * @return int
     */
    public int getU16() {
        checkEnoughDataLeft(2);
        byte high = data[cursor++];
        byte low = data[cursor++];
        return CommonUtil.toU16From(high, low);
    }

    /**
     * Get unsigned 32 bits from the resposne data stream.
     * @return long
     */
    public long getU32() {
        checkEnoughDataLeft(4);
        byte first = data[cursor++];
        byte second = data[cursor++];
        byte third = data[cursor++];
        byte fourth = data[cursor++];
        return CommonUtil.toU32From(first, second, third, fourth);
    }

    /**
     * Get pointer from the response data stream.
     * @return int
     */
    public int getPointer() {
        checkEnoughDataLeft(2);
        byte high = (byte)(data[cursor++] & 0x3f);
        byte low = data[cursor++];
        return CommonUtil.toU16From(high, low);
    }

    /**
     * Seek the cursor to specified location.
     * @param cursor
     */
    public void seek(int cursor) {
        this.cursor = cursor;
    }

    /**
     * Get the location of current cursor.
     * @return int
     */
    public int getCursor() {
        return cursor;
    }

    private void checkEnoughDataLeft(int needed) {
        if (cursor+needed > data.length) {
            throw new DPSException(EOF_ERR);
        }
    }
}
