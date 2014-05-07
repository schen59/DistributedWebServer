package common;

/**
 * Error messages.
 * @author Shaofeng Chen
 * @since 2/23/14
 */
public class Messages {

    public static final String UNKNOWN_RR_TYPE = "Unknown RR type %s.";
    public static final String UNKNOWN_RR_CLASS = "Unknown RR class %s.";
    public static final String UNKNOWN_OPCODE = "Unknown Opcode %s.";
    public static final String UNKNOWN_RCODE = "Unknown Rcode %s.";
    public static final String MESSAGE_ID_ERR = "DNS message id should be 16 bits long.";
    public static final String EOF_ERR = "EOF error.";
    public static final String UNKNOWN_HOST = "Unknow host %s.";
    public static final String SEND_PACKET_ERR = "Can not send packet to %s.";
    public static final String CREATE_UDP_SOCKET_ERR = "Can not create datagrame socket.";
    public static final String CREATE_UNBOUND_PACKET_ERR = "Can not create datagram packet.";
    public static final String CREATE_BOUND_PACKET_ERR = "Can not create packet to socket address" +
            " %s.";
    public static final String TIMEOUT_ERR = "Timeout while receiving data.";
    public static final String RECEIVE_PACKET_ERR = "Can not receive packet.";
    public static final String UNKNOWN_REQ_TYPE = "Unkown request type %s.";

    public static final String READ_HEADER_ERR = "Error while reading headers from input stream.";
    public static final String READ_BODY_ERR = "Error while reading response body from input " +
            "stream.";
    public static final String CREATE_SERVER_SOCK_ERR = "Error creating server socket at port: %s.";
    public static final String ACCEPT_SOCK_ERR = "Error accepting socket from client.";
    public static final String NEED_PORT_ARG_INFO = "Need port number as argument.";
    public static final String PORT_NUMBER_FMT_ERR = "Please give port number as integer.";
    public static final String REPLY_CLIENT_ERR = "Error replying back to client.";
    public static final String SEND_TO_HOST_ERR = "Error sending request to host: %s.";
    public static final String GET_INPUT_STREAM_ERR = "Error getting input stream from socket.";
    public static final String GET_OUTPUT_STEAM_ERR = "Error getting output stream from socket.";
    public static final String CLOSE_SOCK_ERR = "Error closing socket.";
    public static final String UNKNOWN_HOST_ERR = "Error creating socket to unknown host: %s.";
    public static final String INET_UNKNOWN_HOST_ERR = "Error getting inet address from unknown " +
            "host %s.";
    public static final String CREATE_DIR_ERR = "Error creating directories on path: %s.";
    public static final String NO_ALGORITHM_ERR = "No such algorithm %s exists.";
    public static final String UNSUPPORTED_ENCODING_ERR = "Unsupported encoding %s.";
    public static final String NO_FILE_ERR = "File does not exist on path: %s.";
    public static final String WRITE_RESPONSE_ERR = "Error writing response to file path: %s.";

    public static final String SERVICE_TYPE_ERR = "Can not construct Service type from %s.";
    public static final String SERVICE_INST_ERR = "Can not construct service instance from %s.";
    public static final String NON_PTR_ERR = "Can not construct ServiceInstance object from non PTR " +
            "type resource record.";
    public static final String NON_SRV_ERR = "Can not construct ServiceResource type object from non " +
            "SRV type resource record.";

    public static final String ADVERTISER_INTERRUPT = "Advertiser interrupted.";
    public static final String QUERYER_INTERRUPT = "BonjourQueryer interrupted.";

    public static final String GET_SERVICE_RESOURCE_ERR = "Unable to get service resource for %s.";
    public static final String CREATE_RDATA_ERR = "Can not create rdata with type %s.";
    public static final String RCV_MSG_ERR = "Error when receiving dns message.";
    public static final String CREATE_CHANNEL_ERR = "Can not create multicast datagram channel on " +
            "port %s.";
    public static final String JOIN_GROUP_ERR = "Can not join group %s on interface %s.";
    public static final String UNKNOWN_INTERFACE_ERR = "Unknown network interface %s.";
}
