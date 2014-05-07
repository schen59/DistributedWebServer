package mdns.metadata;

import mdns.rdata.PTRRData;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

/**
 * Test cases for Metadata class.
 * @author Shaofeng Chen
 * @since 4/6/14
 */
public class MetaDataTest {

    @Test
    public void testGetByteLength() {
        PTRRData ptrrData1 = new PTRRData(Name.fromString("service1.protocal.domain."));
        PTRRData ptrrData2 = new PTRRData(Name.fromString("service2.protocal.domain."));
        Set<MetaData> metadatas = new HashSet<MetaData>();
        metadatas.add(ptrrData1);
        metadatas.add(ptrrData2);
        int byteLength = MetaData.getByteLength(metadatas);
        assertEquals(52, byteLength);
    }

    @Test
    public void testToByteArray() {
        PTRRData ptrrData1 = new PTRRData(Name.fromString("service1.protocal.domain."));
        PTRRData ptrrData2 = new PTRRData(Name.fromString("service2.protocal.domain."));
        Set<MetaData> metadatas = new HashSet<MetaData>();
        metadatas.add(ptrrData1);
        metadatas.add(ptrrData2);
        byte[] data = MetaData.toByteArray(metadatas);
        assertEquals(52, data.length);
    }
}
