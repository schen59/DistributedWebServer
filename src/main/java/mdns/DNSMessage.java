package mdns;

import mdns.metadata.Header;
import mdns.metadata.MetaData;

import java.nio.ByteBuffer;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Class for dns message.
 * @author Shaofeng Chen
 * @since 3/28/14
 */
public class DNSMessage extends MetaData {
    private Header header;
    private Set<Question> questions;
    private Set<ResourceRecord> answers;

    public DNSMessage() {
        questions = new HashSet<Question>();
        answers = new HashSet<ResourceRecord>();
    }

    public DNSMessage withHeader(Header header) {
        this.header = header;
        return this;
    }

    public boolean isQuery() {
        return header.isQuery();
    }

    public boolean isEmpty() {
        return questions.isEmpty() && answers.isEmpty();
    }

    public Header getHeader() {
        return header;
    }

    public Set<Question> getQuestions() {
        return questions;
    }

    public Set<ResourceRecord> getAnswers() {
        return answers;
    }

    public DNSMessage withQuestions(List<Question> questions) {
        for (Question question : questions) {
            this.questions.add(question);
        }
        return this;
    }

    public DNSMessage withAnswers(List<ResourceRecord> answers) {
        for (ResourceRecord answer : answers) {
            this.answers.add(answer);
        }
        return this;
    }

    public byte[] toByteArray() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(getByteLength());
        byteBuffer.put(header.toByteArray());
        byteBuffer.put(toByteArray(questions));
        byteBuffer.put(toByteArray(answers));
        return byteBuffer.array();
    }

    public int getByteLength() {
        int byteLength = 0;
        for (Question question : questions) {
            byteLength += question.getByteLength();
        }
        for (ResourceRecord answer : answers) {
            byteLength += answer.getByteLength();
        }
        return byteLength + header.getByteLength();
    }
}
