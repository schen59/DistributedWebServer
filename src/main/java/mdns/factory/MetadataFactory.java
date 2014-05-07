package mdns.factory;

import common.RawMessage;
import mdns.DNSMessage;
import mdns.Question;
import mdns.ResourceRecord;
import mdns.builder.DNSMessageBuilder;
import mdns.enums.RRClass;
import mdns.enums.RRType;
import mdns.metadata.Header;
import mdns.metadata.Label;
import mdns.metadata.MetaData;
import mdns.metadata.Name;

import java.util.ArrayList;
import java.util.List;

/**
 * Factory to create metadata object.
 * @author Shaofeng Chen
 * @since 3/31/14
 */
public class MetadataFactory {

    public static Header createHeader(RawMessage rawMessage) {
        int id = rawMessage.getU16();
        int flags = rawMessage.getU16();
        int qdcount = rawMessage.getU16();
        int ancount = rawMessage.getU16();
        int nscount = rawMessage.getU16();
        int arcount = rawMessage.getU16();
        Header header = new Header();
        header.withId(id)
                .withFlags(flags)
                .withQdcount(qdcount)
                .withAncount(ancount)
                .withNscount(nscount)
                .withArcount(arcount);
        return header;
    }

    public static Label createLabel(RawMessage rawMessage) {
        int componentLength = rawMessage.getU8();
        String name = "";
        while (componentLength-- > 0) {
            name += (char)(rawMessage.getU8());
        }
        return Label.fromString(name);
    }

    public static Name createName(RawMessage rawMessage) {
        int componentLength = rawMessage.peekU8();
        Name name = new Name();
        while (componentLength != 0) {
            Label label = createLabel(rawMessage);
            name.addLabel(label);
            componentLength = rawMessage.peekU8();
        }
        rawMessage.getU8();
        return name;
    }

    /**
     * Create Question type object from response data.
     *
     * @param rawMessage
     * @param qdcounts
     * @return Question
     */
    public static List<Question> createQuestions(RawMessage rawMessage, int qdcounts) {
        List<Question> questions = new ArrayList<Question>();
        while (qdcounts-- > 0) {
            Name name = createName(rawMessage);
            RRType qtype = EnumFactory.createRRType(rawMessage.getU16());
            RRClass qclass = EnumFactory.createRRClass(rawMessage.getU16());
            questions.add(new Question(name, qtype, qclass));
        }
        return questions;
    }

    /**
     * Create ResourceRecord type object from response data.
     * @param rawMessage
     * @return ResourceRecord
     */
    public static ResourceRecord createResourceRecord(RawMessage rawMessage) {
        Name name = createName(rawMessage);
        RRType rrType = EnumFactory.createRRType(rawMessage.getU16());
        RRClass rrClass = EnumFactory.createRRClass(rawMessage.getU16());
        long ttl = rawMessage.getU32();
        int rLength = rawMessage.getU16();
        MetaData rData = RDataFactory.createRDataWithType(rawMessage, rrType);
        return new ResourceRecord(name, rrType, rrClass, ttl, rLength, rData);
    }

    /**
     * Create the specified number of ResourceRecord type objects from response data.
     * @param rawMessage
     * @param counts
     * @return List
     */
    public static List<ResourceRecord> createResourceRecords(RawMessage rawMessage, int counts) {
        List<ResourceRecord> resourceRecords = new ArrayList<ResourceRecord>();
        while (counts-- > 0) {
            ResourceRecord resourceRecord = createResourceRecord(rawMessage);
            resourceRecords.add(resourceRecord);
        }
        return resourceRecords;
    }

    public static DNSMessage createDNSMessage(RawMessage rawMessage) {
        Header header = createHeader(rawMessage);
        int qdcount = header.getQdcount();
        List<Question> questions = createQuestions(rawMessage, qdcount);
        int ancount = header.getAncount();
        List<ResourceRecord> answers = createResourceRecords(rawMessage, ancount);
        DNSMessageBuilder dnsMessageBuilder = new DNSMessageBuilder();
        return dnsMessageBuilder.withHeader(header)
                .withQuestions(questions)
                .withAnswers(answers)
                .build();
    }
}
