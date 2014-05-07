package mdns.metadata;

import java.nio.ByteBuffer;
import java.util.Collection;

/**
 * Common class for metadata type object.
 * @author Shaofeng Chen
 * @since 3/29/14
 */
public abstract class MetaData {

    /**
     * Get byte length of metadata object.
     * @return int
     */
    public abstract int getByteLength();

    /**
     * Get the total byte length of the given collection of metadata type objects.
     * @param metaDatas
     * @return int
     */
    public static int getByteLength(Collection<? extends MetaData> metaDatas) {
        int byteLength = 0;
        for (MetaData metaData : metaDatas) {
            byteLength += metaData.getByteLength();
        }
        return byteLength;
    }

    /**
     * Convert the metadata object to bytes.
     * @return byte[]
     */
    public abstract byte[] toByteArray();

    /**
     * Convert a collection of metadata type objects to bytes.
     * @param metaDatas
     * @return byte[]
     */
    public static byte[] toByteArray(Collection<? extends MetaData> metaDatas) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(getByteLength(metaDatas));
        for (MetaData metaData : metaDatas) {
            byteBuffer.put(metaData.toByteArray());
        }
        return byteBuffer.array();
    }
}
