package Client;

import Model.HotelModel;
import com.google.gson.Gson;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.*;
import java.net.Socket;

public class HotelClientImplementation implements HotelClient
{
  private PropertyChangeSupport support;
  private final Socket socket;
  private final Gson gson;
  private final PrintWriter output;
  private final BufferedReader input;
  private final MessageListener listener;

  public HotelClientImplementation(String host, int port) throws IOException
  {
    socket = new Socket(host, port);
    gson = new Gson();
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
