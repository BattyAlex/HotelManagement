package Client;

import Model.HotelModel;

import Model.Room;
import Model.Staff;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class HotelClientImplementation implements HotelClient
{
  private PropertyChangeSupport support;
  private final Socket socket;
  private final ObjectOutputStream output;
  private final ObjectInputStream input;
  private final MessageListener listener;

  public HotelClientImplementation(String host, int port) throws IOException
  {
    socket = new Socket(host, port);
    output = new ObjectOutputStream(socket.getOutputStream());
    input = new ObjectInputStream(socket.getInputStream());
    support = new PropertyChangeSupport(this);
    listener = new MessageListener(this, "230.0.0.0", 8888);
    Thread thread = new Thread(listener);
    thread.start();
  }

  @Override public void tryLogin(String username, String password)
      throws IOException
  {
    try
    {
      output.writeObject("Login attempt");
      output.flush();
      String request = (String) input.readObject();
      if(request.equals("Which staff?"))
      {
        Staff sendOver = new Staff(username, password);
        output.writeObject(sendOver);
        output.flush();
      }
      request = (String) input.readObject();
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
    catch (NullPointerException e)
    {
      System.out.println("Database connection may be offline.");
    }
    catch (ClassNotFoundException e)
    {
      e.printStackTrace();
    }
  }

  @Override public void getAllRooms() throws IOException
  {
    try
    {
      output.writeObject("Requesting All Rooms");
      output.flush();
      ArrayList<Room> temp = (ArrayList<Room>) input.readObject();
      support.firePropertyChange("Sending All Rooms", null, temp);
    }
    catch (ClassNotFoundException e)
    {
      e.printStackTrace();
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
