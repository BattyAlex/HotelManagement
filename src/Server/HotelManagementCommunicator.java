package Server;

import Model.Room;
import Model.Staff;
import com.google.gson.Gson;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class HotelManagementCommunicator implements Runnable
{
  private final Socket socket;
  private final UDPBroadcaster broadcaster;
  private final Gson gson;
  private BufferedReader reader;
  private PrintWriter writer;

  public HotelManagementCommunicator(Socket socket, UDPBroadcaster broadcaster)
  {
    this.socket = socket;
    this.broadcaster = broadcaster;
    this.gson = new Gson();
  }

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
        else if (text.equals("Requesting All Rooms"))
        {
          ArrayList<Room> sendOver = RoomDAO.getInstance().getAllRooms();
          String json = gson.toJson(sendOver);
          writer.println(json);
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
