package mdns.rdata;

import mdns.ResourceRecord;
import mdns.enums.RRClass;
import mdns.enums.RRType;
import mdns.metadata.Name;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test cases for ResourceRecord class.
 * @author Shaofeng Chen
 * @since 4/6/14
 */
public class ResourceRecordTest {

    @Test
    public void testGetByteLength() {
        Name service = Name.fromString("service.protocal.domain.");
        Name instance = Name.fromString("instance.service.protocal.domain.");
        PTRRData ptrrData = new PTRRData(instance);
        ResourceRecord resourceRecord = new ResourceRecord(service, RRType.PTR, RRClass.IN, 0,
                ptrrData.getByteLength(), ptrrData);
        assertEquals(69, resourceRecord.getByteLength());
    }
}
