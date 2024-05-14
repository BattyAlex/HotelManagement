package Server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * The UDPBroadcaster class is responsible for broadcasting messages over UDP
 * to a specified group address and port
 */

public class UDPBroadcaster
{
  private final InetAddress group;
  private final int port;

  /**
   * Constructs a UDPBroadcaster with the specified group address and port.
   * @param groupAddress The multicast group address to witch messages will be sent.
   * @param port The port number on which to send messages
   * @throws IOException If an error occurs while getting the group address.
   */

  public UDPBroadcaster(String groupAddress, int port) throws IOException {
    this.group = InetAddress.getByName(groupAddress);
    this.port = port;
  }

  /**
   * Broadcasts a message to the group address and port specified during construction.
   *
   * @param message The message to broadcast
   * @throws IOException If an I/O error occurs while broadcasting the message.
   */

  public synchronized void broadcast(String message) throws IOException {
    try(DatagramSocket socket = new DatagramSocket()) {
      byte[] content = message.getBytes();
      DatagramPacket packet = new DatagramPacket(content, content.length, group, port);
      socket.send(packet);
    }
  }
}
