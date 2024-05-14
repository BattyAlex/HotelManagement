package Server;

import Model.Room;
import Model.Staff;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
/**
 * The {@code HotelManagementCommunicator} class implements the Runnable interface to handle
 * communication with the hotel management system
 */


public class HotelManagementCommunicator implements Runnable
{
  private final Socket socket;
  private final UDPBroadcaster broadcaster;
  private ObjectInputStream reader;
  private ObjectOutputStream writer;

  /**
   * Constructs a new {@code HotelManagementCommunicator} with the specified socket and UDP broadcaster.
   * @param socket  the socket for communication
   * @param broadcaster  the UDP broadcaster for message broadcasting
   */

  public HotelManagementCommunicator(Socket socket, UDPBroadcaster broadcaster)
  {
    this.socket = socket;
    this.broadcaster = broadcaster;
  }

  /**
   * Handles communication over the socket. This method listens for incoming messages, processes them
   * and sends appropriate responses. It runs in a loop until the connection is closed or an error occurs.
   * @throws IOException if an I/0 error occurs while communicating.
   */

  private void communicate() throws IOException
  {
    reader = new ObjectInputStream(socket.getInputStream());
    writer = new ObjectOutputStream(socket.getOutputStream());
    try
    {
      loop: while (true)
      {
        String text = (String) reader.readObject();
        if(text == null)
        {
          break loop;
        }
        else if(text.equals("Login attempt"))
        {
          writer.writeObject("Which staff?");
          writer.flush();
          Staff confirmation = (Staff) reader.readObject();
          Staff loginRequest = UserDAO.getInstance().getStaffBasedOnUsername(
              confirmation.getUsername());
          if(loginRequest == null)
          {
            writer.writeObject("Invalid username");
          }
          else if (loginRequest.equals(confirmation))
          {
            writer.writeObject("Approved");
          }
          else
          {
            writer.writeObject("Rejected");
          }
          writer.flush();
        }
        else if (text.equals("Requesting All Rooms"))
        {
          ArrayList<Room> sendOver = RoomDAO.getInstance().getAllRooms();
          writer.writeObject(sendOver);
          writer.flush();
        }
      }
    }
    catch (ClassNotFoundException e)
    {
      e.printStackTrace();
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
