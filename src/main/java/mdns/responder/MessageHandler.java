package mdns.responder;

import common.RawMessage;
import mdns.DNSMessage;
import mdns.Question;
import mdns.ResourceRecord;
import mdns.builder.DNSMessageBuilder;
import mdns.factory.MetadataFactory;
import util.UDPSocketUtil;

import java.net.SocketAddress;
import java.util.Set;

/**
 * Handler for DNS message.
 * @author Shaofeng Chen
 * @since 3/31/14
 */
public class MessageHandler extends Thread {

    private byte[] message;
    private Responder responder;
    private SocketAddress remoteAddress;

    public MessageHandler(byte[] message, SocketAddress remoteAddress, Responder responder) {
        this.message = message.clone();
        this.remoteAddress = remoteAddress;
        this.responder = responder;
    }

    public void run() {
        RawMessage rawMessage = new RawMessage(message);
        DNSMessage incomingDnsMessage = MetadataFactory.createDNSMessage(rawMessage);
        Set<Question> questions = incomingDnsMessage.getQuestions();
        Set<ResourceRecord> answers = incomingDnsMessage.getAnswers();
        if (incomingDnsMessage.isQuery()) {
            DNSMessageBuilder dnsMessageBuilder = new DNSMessageBuilder();
            dnsMessageBuilder.asResponse();
            for (Question question : questions) {
                if (responder.canAnswerQuestion(question)) {
                    ResourceRecord answer = responder.answerQuestion(question);
                    if (!answers.contains(answer)) {
                        dnsMessageBuilder.addAnswer(answer);
                    }
                }
            }
            DNSMessage response = dnsMessageBuilder.build();
            if (!response.isEmpty()) {
                UDPSocketUtil.send(response, remoteAddress);
            }
        } else {
            for (ResourceRecord answer : answers) {
                responder.addAnswer(answer);
            }
        }
    }
}
