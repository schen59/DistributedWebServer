package util;

import common.DPSException;
import mdns.DNSMessage;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import static common.Constants.UDP_BUFFER_SIZE;
import static common.Messages.CREATE_BOUND_PACKET_ERR;
import static common.Messages.CREATE_UDP_SOCKET_ERR;
import static common.Messages.CREATE_UNBOUND_PACKET_ERR;
import static common.Messages.INET_UNKNOWN_HOST_ERR;
import static common.Messages.RECEIVE_PACKET_ERR;
import static common.Messages.UNKNOWN_INTERFACE_ERR;

/**
 * Utility class for udp related socket operations.
 * @author Shaofeng Chen
 * @since 3/29/14
 */
public class UDPSocketUtil {

    public static DatagramSocket createDatagramSocket() {
        try {
            return new DatagramSocket();
        } catch (IOException ex) {
            throw new DPSException(CREATE_UDP_SOCKET_ERR, ex);
        }
    }

    public static void sendDatagramPacket(DatagramSocket socket, DatagramPacket packet) {
        try {
            socket.send(packet);
        } catch (IOException ex) {
            throw new DPSException(CREATE_UNBOUND_PACKET_ERR, ex);
        }
    }

    public static void send(DatagramSocket socket, DNSMessage dnsMessage, String adress,
            int port) {
        byte[] data = dnsMessage.toByteArray();
        DatagramPacket packet = new DatagramPacket(data, data.length,
                UDPSocketUtil.inetAddressFromString(adress), port);
        UDPSocketUtil.sendDatagramPacket(socket, packet);
    }

    public static void send(DNSMessage dnsMessage, SocketAddress remoteAddress) {
        byte[] data = dnsMessage.toByteArray();
        try {
            DatagramPacket packet = new DatagramPacket(data, data.length, remoteAddress);
            DatagramSocket socket = createDatagramSocket();
            sendDatagramPacket(socket, packet);
        } catch (SocketException ex) {
            throw new DPSException(String.format(CREATE_BOUND_PACKET_ERR, remoteAddress));
        }
    }

    public static InetAddress inetAddressFromString(String host) {
        try {
            return InetAddress.getByName(host);
        } catch (UnknownHostException ex) {
            throw new DPSException(String.format(INET_UNKNOWN_HOST_ERR, host));
        }
    }

    public static DatagramPacket receiveFrom(DatagramSocket socket) {
        byte[] buffer = new byte[UDP_BUFFER_SIZE];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        try {
            socket.receive(packet);
        } catch (IOException ex) {
            throw new DPSException(RECEIVE_PACKET_ERR, ex);
        }
        return packet;
    }

    public static NetworkInterface getNetworkInterface(String name) {
        try {
            return NetworkInterface.getByName(name);
        } catch (SocketException ex) {
            throw new DPSException(String.format(UNKNOWN_INTERFACE_ERR, name));
        }
    }
}
