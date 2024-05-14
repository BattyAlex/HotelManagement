package Client;

import java.io.IOException;
import java.net.*;
import java.nio.channels.AsynchronousCloseException;

/**
 * The MessageListener class implements Runnable and is responsible for listening to multicast message
 * from a specified group address and port
 */

public class MessageListener implements Runnable
{
  private final MulticastSocket multicastSocket;
  private final InetSocketAddress socketAddress;
  private final NetworkInterface netInterface;
  private final HotelClientImplementation client;

  /**
   * Constructs a new MessageListener with the specified client, group address and port.
   * @param client  the client implementation that will handle the received messages
   * @param groupAddress the multicast group address to join
   * @param port the port number tp listen on
   * @throws IOException if an I/O error occurs
   */

  public MessageListener(HotelClientImplementation client, String groupAddress,
      int port) throws IOException
  {
    this.client = client;
    multicastSocket = new MulticastSocket(port);
    InetAddress group = InetAddress.getByName(groupAddress);
    socketAddress = new InetSocketAddress(group, port);
    netInterface = NetworkInterface.getByInetAddress(group);
  }

  /**
   * Listens for incoming multicast messages and passes them to the client for handling.
   * This method runs in an infinite loop until interrupted or an error occurs.
   */

  @Override public void run()
  {
    try
    {
      listen();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }

  /**
   * Joins the multicast group and continuously listens for incoming datagram packets
   * Each received message is passed to the client for processing
   * @throws IOException if an I/O error occurs.
   */

  private void listen() throws IOException
  {
    multicastSocket.joinGroup(socketAddress, netInterface);
    try
    {
      byte[] content = new byte[32768];
      while (true)
      {
        DatagramPacket packet = new DatagramPacket(content, content.length);
        multicastSocket.receive(packet);
        String message = new String(packet.getData(), 0, packet.getLength());
        client.receiveBroadcast(message);
      }
    }
    catch (SocketException e)
    {
      if (!(e.getCause() instanceof AsynchronousCloseException))
        throw e;
    }
  }

  /**
   * Leaves the multicast group and closes the multicast socket.
   *
   * @throws IOException if an I/O error occurs.
   */

  public void close() throws IOException
  {
    multicastSocket.leaveGroup(socketAddress, netInterface);
    multicastSocket.close();
  }
}
