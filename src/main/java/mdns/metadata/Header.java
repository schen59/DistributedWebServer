package mdns.metadata;

import java.nio.ByteBuffer;
import java.util.Random;

/**
 * Metadata class for DNS header object.
 * @author Shaofeng Chen
 * @since 3/27/14
 */
public class Header extends MetaData {
    private int id;
    private int flags;
    private int qdcount;
    private int ancount;
    private int nscount;
    private int arcount;

    private static Random random = new Random();

    public Header() {
        id = random.nextInt(0xffff);
        flags = 0;
        qdcount = 0;
        ancount = 0;
        nscount = 0;
        arcount = 0;
    }

    public Header withId(int id) {
        this.id = id;
        return this;
    }

    public Header withFlags(int flags) {
        this.flags = flags;
        return this;
    }

    public Header withQdcount(int qdcount) {
        this.qdcount = qdcount;
        return this;
    }

    public Header withAncount(int ancount) {
        this.ancount = ancount;
        return this;
    }

    public Header withNscount(int nscount) {
        this.nscount = nscount;
        return this;
    }

    public Header withArcount(int arcount) {
        this.arcount = arcount;
        return this;
    }

    public boolean isQuery() {
        return ((flags & (1 << 15)) >> 15) == 0;
    }

    public int getQdcount() {
        return qdcount;
    }

    public int getAncount() {
        return ancount;
    }

    public int getNscount() {
        return nscount;
    }

    public int getArcount() {
        return arcount;
    }

    public byte[] toByteArray() {
        int bytesLength = getByteLength();
        ByteBuffer byteBuffer = ByteBuffer.allocate(bytesLength);
        byteBuffer.putShort((short) id);
        byteBuffer.putShort((short) flags);
        byteBuffer.putShort((short) qdcount);
        byteBuffer.putShort((short) ancount);
        byteBuffer.putShort((short) nscount);
        byteBuffer.putShort((short) arcount);
        return byteBuffer.array();
    }

    public int getByteLength() {
        return 12;
    }
}
