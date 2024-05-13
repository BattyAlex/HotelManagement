package Client;

import Model.HotelModel;
import Model.Staff;
import com.google.gson.Gson;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.*;
import java.net.Socket;

public class HotelClientImplementation implements HotelClient
{
  private PropertyChangeSupport support;
  private final Socket socket;
  private final Gson json;
  private final PrintWriter output;
  private final BufferedReader input;
  private final MessageListener listener;

  public HotelClientImplementation(String host, int port) throws IOException
  {
    socket = new Socket(host, port);
    json = new Gson();
    OutputStream outputStream = socket.getOutputStream();
    output = new PrintWriter(outputStream);
    InputStream inputStream = socket.getInputStream();
    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
    input = new BufferedReader(inputStreamReader);
    support = new PropertyChangeSupport(this);
    listener = new MessageListener(this, "230.0.0.0", 8888);
    Thread thread = new Thread(listener);
    thread.start();
  }

  @Override public void tryLogin(String username, String password)
      throws IOException
  {
    output.println("Login attempt");
    output.flush();
    String request = (String) input.readLine();
    if(request.equals("Which staff?"))
    {
      String sendOver = json.toJson(new Staff(username, password));
      output.print(sendOver);
      output.flush();
    }
    request = (String) input.readLine();
    if(request.equals("Invalid username"))
    {
      support.firePropertyChange("Invalid username", username, null);
    }
    else if (request.equals("Approved"))
    {
      support.firePropertyChange("Login Approved", null, null);
    }
    else if (request.equals("Rejected"))
    {
      support.firePropertyChange("Login Rejected", username, null);
    }
  }

  @Override public void addPropertyChangeListener(
      PropertyChangeListener listener)
  {
    support.addPropertyChangeListener(listener);
  }

  @Override public void removePropertyChangeListener(
      PropertyChangeListener listener)
  {
    support.removePropertyChangeListener(listener);
  }

  @Override public void receiveBroadcast(String message)
  {
    support.firePropertyChange("message", null, message);
  }
}
