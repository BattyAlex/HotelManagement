package Client;

import Model.HotelModel;

import Model.Room;
import Model.Staff;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;

/**
 * The HotelClientImplementation class implements the HotelClient interface
 * It handles communication with the server and updates local data based on server responses
 */
public class HotelClientImplementation implements HotelClient
{
  private PropertyChangeSupport support;
  private final Socket socket;
  private final ObjectOutputStream output;
  private final ObjectInputStream input;
  private final MessageListener listener;

  /**
   * Constructs a HotelClientImplementation object and establishes a connection to tthe server
   * @param host The host address of the server
   * @param port The port number of the server
   * @throws IOException if an IO error occurs when creating the socket or streams
   */
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

  /**
   * Tries to log in with the specified username and password
   * @param username The username of the user trying to log in
   * @param password The password of the user tryying to log in
   * @throws IOException if an IO erroe occurs during communication with the server
   */
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
    catch (EOFException e)
    {
      System.out.println("Database connection may be offline.");
    }
    catch (ClassNotFoundException e)
    {
      e.printStackTrace();
    }
  }

  /**
   * Retrieves all rooms from the server
   * @throws IOException if an IO error occurs during communication with the server
   */
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

  /**
   * Retrieves available rooms for a specified period from the server
   * @param startDate the start date of the period
   * @param endDate the end date of the period
   * @throws IOException if an IO error occurs during communication with the server
   */
  @Override public void getRoomsAvailable(Date startDate, Date endDate) throws IOException
  {
    try
    {
      output.writeObject("Request Rooms of Specific Period");
      output.flush();
      String request = (String) input.readObject();
      if (request.equals("Start Date?"))
      {
        output.writeObject(startDate);
        output.flush();
      }
      request = (String) input.readObject();
      if(request.equals("End Date?"))
      {
        output.writeObject(endDate);
        output.flush();
      }
      ArrayList<Room> result = (ArrayList<Room>) input.readObject();
      support.firePropertyChange("Sending Available Rooms", null, result);
    }
    catch (ClassNotFoundException e)
    {
      e.printStackTrace();
    }
  }

  /**
   * Adds a PropertyChangeListener to this client
   * @param listener the listener to add
   */
  @Override public void addPropertyChangeListener(
      PropertyChangeListener listener)
  {
    support.addPropertyChangeListener(listener);
  }

  /**
   * Removes a property change listener from this client
   * @param listener the listener to remove
   */
  @Override public void removePropertyChangeListener(
      PropertyChangeListener listener)
  {
    support.removePropertyChangeListener(listener);
  }

  /**
   * Receives a broadcast message and fires a property change event
   * @param message the broadcast message
   */
  @Override public void receiveBroadcast(String message)
  {
    support.firePropertyChange("message", null, message);
  }
}
