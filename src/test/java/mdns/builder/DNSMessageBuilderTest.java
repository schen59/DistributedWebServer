package mdns.builder;

import mdns.DNSMessage;
import mdns.Question;
import mdns.ResourceRecord;
import mdns.enums.RRClass;
import mdns.enums.RRType;
import mdns.metadata.MetaData;
import mdns.metadata.Name;
import mdns.rdata.PTRRData;

import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Test cases for DNSMessageBuilder.
 * @author Shaofeng Chen
 * @since 3/29/14
 */
public class DNSMessageBuilderTest {

    @Test
    public void testAsQuery() {
        DNSMessageBuilder dnsMessageBuilder = new DNSMessageBuilder();
        dnsMessageBuilder.asQuery();
        DNSMessage dnsMessage = dnsMessageBuilder.build();
        assertTrue(dnsMessage.getHeader().isQuery());
    }

    @Test
    public void testAsResponse() {
        DNSMessageBuilder dnsMessageBuilder = new DNSMessageBuilder();
        dnsMessageBuilder.asResponse();
        DNSMessage dnsMessage = dnsMessageBuilder.build();
        assertFalse(dnsMessage.getHeader().isQuery());
    }

    @Test
    public void testAddQuestion() {
        DNSMessageBuilder dnsMessageBuilder = new DNSMessageBuilder();
        Name name = Name.fromString("testQuestion");
        Question question = new Question(name, RRType.PTR, RRClass.IN);
        dnsMessageBuilder.addQuestion(question);
        DNSMessage dnsMessage = dnsMessageBuilder.build();
        Set<Question> questions = dnsMessage.getQuestions();
        assertEquals(1, questions.size());
        assertEquals(1, dnsMessage.getHeader().getQdcount());
        assertTrue(questions.contains(question));
    }

    @Test
    public void testAddAnswer() {
        DNSMessageBuilder dnsMessageBuilder = new DNSMessageBuilder();
        MetaData ptrrData = new PTRRData(Name.fromString("testInstance.testService.testProtocal" +
                ".testDomain."));
        ResourceRecord answer = new ResourceRecord(Name.fromString("testAnswer"), RRType.PTR,
                RRClass.IN, 0, ptrrData.getByteLength(), ptrrData);
        dnsMessageBuilder.addAnswer(answer);
    }
}
