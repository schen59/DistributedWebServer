package mdns.metadata;

import java.nio.ByteBuffer;

/**
 * Metadata class for label object.
 * @author Shaofeng Chen
 * @since 3/27/14
 */
public class Label extends MetaData {
    private final int length;
    private final String name;

    private Label(String name) {
        this.name = name;
        length = name.length();
    }

    public static Label fromString(String name) {
        return new Label(name);
    }

    @Override
    public String toString() {
        return name;
    }

    public int getLength() {
        return length;
    }

    public int getByteLength() {
        return length + 1;
    }

    public byte[] toByteArray() {
        ByteBuffer buffer = ByteBuffer.allocate(getByteLength());
        buffer.put((byte)length);
        for (char c : name.toCharArray()) {
            buffer.put((byte)c);
        }
        return buffer.array();
    }
}
