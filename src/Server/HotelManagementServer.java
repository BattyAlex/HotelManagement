package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * The HotelManagementServer class sets up a servet that listens for client connections
 * on port 8080 and uses a UPD broadcaster to communicate with clients.
 */

public class HotelManagementServer
{
  /**
   * The main method that serves as the entry point of the application.
   * It initializes the server socket and UDP broadcaster, and continuously listens for client connections.
   * @param args Command-line arguments
   * @throws IOException If an I/O error occurs when opening the socket.
   */
  public static void main(String[] args) throws IOException
  {
    ServerSocket serverSocket = new ServerSocket(8080);
    UDPBroadcaster broadcaster = new UDPBroadcaster("230.0.0.0", 8888);
    while (true)
    {
      System.out.println("Server is ready for input port 8080");
      Socket socket = serverSocket.accept();
      HotelManagementCommunicator communicator = new HotelManagementCommunicator(socket, broadcaster);
      Thread communicatorThread = new Thread(communicator);
      communicatorThread.start();
    }
  }
}
