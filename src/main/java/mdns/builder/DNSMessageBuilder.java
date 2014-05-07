package mdns.builder;

import mdns.DNSMessage;
import mdns.Question;
import mdns.ResourceRecord;
import mdns.metadata.Header;

import java.util.ArrayList;
import java.util.List;

/**
 * Builder to build DNSMessage type object.
 * @author Shaofeng Chen
 * @since 3/29/14
 */
public class DNSMessageBuilder {

    private Header header;
    private List<Question> questions;
    private List<ResourceRecord> answers;

    public DNSMessageBuilder() {
        header = new Header();
        questions = new ArrayList<Question>();
        answers = new ArrayList<ResourceRecord>();
    }

    public DNSMessageBuilder asQuery() {
        header.withFlags(0x0000);
        return this;
    }

    public DNSMessageBuilder asResponse() {
        header.withFlags(0x8000);
        return this;
    }

    public DNSMessageBuilder withHeader(Header header) {
        this.header = header;
        return this;
    }

    public DNSMessageBuilder withQuestions(List<Question> questions) {
        for (Question question : questions) {
            addQuestion(question);
        }
        return this;
    }

    public DNSMessageBuilder withAnswers(List<ResourceRecord> answers) {
        for (ResourceRecord answer : answers) {
            addAnswer(answer);
        }
        return this;
    }

    public DNSMessageBuilder addQuestion(Question question) {
        header.withQdcount(header.getQdcount() + 1);
        questions.add(question);
        return this;
    }

    public DNSMessageBuilder addAnswer(ResourceRecord answer) {
        header.withAncount(header.getAncount() + 1);
        answers.add(answer);
        return this;
    }

    public DNSMessage build() {
        DNSMessage dnsMessage = new DNSMessage();
        dnsMessage.withHeader(header)
                .withQuestions(questions)
                .withAnswers(answers);
        return dnsMessage;
    }
}
