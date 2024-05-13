package Server;

import com.google.gson.Gson;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

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
