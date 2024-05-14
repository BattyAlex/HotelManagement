package Server;

import Model.Staff;
import com.google.gson.Gson;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
/**
 * The {@code HotelManagementCommunicator} class implements the Runnable interface to handle
 * communication with the hotel management system
 */

public class HotelManagementCommunicator implements Runnable
{
  private final Socket socket;
  private final UDPBroadcaster broadcaster;
  private final Gson gson;
  private BufferedReader reader;
  private PrintWriter writer;

  /**
   * Constructs a new {@code HotelManagementCommunicator} with the specified socket and UDP broadcaster.
   * @param socket  the socket for communication
   * @param broadcaster  the UDP broadcaster for message broadcasting
   */

  public HotelManagementCommunicator(Socket socket, UDPBroadcaster broadcaster)
  {
    this.socket = socket;
    this.broadcaster = broadcaster;
    this.gson = new Gson();
  }

  /**
   * Handles communication over the socket. This method listens for incoming messages, processes them
   * and sends appropriate responses. It runs in a loop until the connection is closed or an error occurs.
   * @throws IOException if an I/0 error occurs while communicating.
   */

  private void communicate() throws IOException
  {
    InputStream inputStream = socket.getInputStream();
    OutputStream outputStream = socket.getOutputStream();
    reader = new BufferedReader(new InputStreamReader(inputStream));
    writer = new PrintWriter(outputStream);
    try
    {
      loop: while (true)
      {
        String text = (String) reader.readLine();
        if(text == null)
        {
          break loop;
        }
        else if(text.equals("Login attempt"))
        {
          writer.println("Which staff?");
          writer.flush();
          text = reader.readLine();
          Staff confirmation = gson.fromJson(text, Staff.class);
          Staff loginRequest = UserDAO.getInstance().getStaffBasedOnUsername(
              confirmation.getUsername());
          if(loginRequest == null)
          {
            writer.println("Invalid username");
          }
          else if (loginRequest.equals(confirmation))
          {
            writer.println("Approved");
          }
          else
          {
            writer.println("Rejected");
          }
          writer.flush();
        }
      }
    }
    finally
    {
      synchronized (broadcaster)
      {
        socket.close();
      }
    }
  }

  /**
   * Runs the communication process. The method is invoked when the thread is started.
   * It handles any exception that may occur.
   */
  @Override public void run()
  {
    try
    {
      communicate();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
}
