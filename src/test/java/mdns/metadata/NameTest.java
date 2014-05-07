package mdns.metadata;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by Sherwin on 4/4/14.
 */
public class NameTest {

    @Test
    public void testFromString() {
        Name name = Name.fromString("www.example.com.");
        assertEquals(3, name.getLabels().size());
    }

    @Test
    public void testGetByteLength() {
        Name name = Name.fromString("service.protocal.domain.");
        assertEquals(25, name.getByteLength());
    }
}
