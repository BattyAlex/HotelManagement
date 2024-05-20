package Client;

import Model.HotelModel;

import Model.Reservation;
import Model.Room;
import Model.Staff;
import Server.RoomDAO;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.*;
import java.net.Socket;
import java.sql.SQLException;
import java.time.LocalDate;
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
        support.firePropertyChange("Login Approved", null, username);
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
  @Override public ArrayList<Room> getRoomsAvailable(LocalDate startDate, LocalDate endDate) throws IOException
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
      return result;
    }
    catch (ClassNotFoundException e)
    {
      e.printStackTrace();
    }
    return null;
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

  @Override public void getAllReservations() throws IOException
  {
    try
    {
      output.writeObject("Requesting All Reservations");
      output.flush();
      ArrayList<Reservation> reservations = (ArrayList<Reservation>) input.readObject();
      support.firePropertyChange("Sending All Reservations", null, reservations);
    }
    catch (ClassNotFoundException e)
    {
      e.printStackTrace();
    }
  }

  @Override public void getReservationsInTimeframe(LocalDate startDate,
      LocalDate endDate) throws IOException
  {
    try
    {
      output.writeObject("Requesting Reservations of Specific Period");
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
      ArrayList<Reservation> reservations = (ArrayList<Reservation>) input.readObject();
      support.firePropertyChange("Sending All Reservations For Period", null, reservations);
    }
    catch (ClassNotFoundException e)
    {
      e.printStackTrace();
    }
  }

  @Override public Room getRoomByRoomNumber(int roomNumber) throws IOException
  {
    try
    {
      output.writeObject("Get room with room number");
      output.flush();
      String request = (String) input.readObject();
      if(request.equals("Which room?"))
      {
        output.writeObject(roomNumber);
        output.flush();
      }
      Room received = (Room) input.readObject();
      return received;
    }
    catch (ClassNotFoundException e)
    {
      e.printStackTrace();
    }
    return null;
  }

  @Override public void makeOrUpdateReservation(Reservation reservation) throws IOException
  {
    try
    {
      output.writeObject("Making or Updating Reservation");
      output.flush();
      String request = (String) input.readObject();
      if(request.equals("Reservation needed"))
      {
        output.writeObject(reservation);
        output.flush();
      }
      Reservation made = (Reservation) input.readObject();
      support.firePropertyChange("Reservation Made", null, made);
    }
    catch (ClassNotFoundException e)
    {
      e.printStackTrace();
    }
  }

  @Override public void close()
  {
    try
    {
      socket.close();
      listener.close();
    }
    catch (IOException e)
    {
      System.out.println("Unable to close client.");
    }
  }

  @Override public void onDelete(Reservation reservation) throws IOException
  {
    try
    {
      output.writeObject("Deleting reservation");
      output.flush();
      String request = (String) input.readObject();
      if(request.equals("Which Reservation?"))
      {
        output.writeObject(reservation);
        output.flush();
      }
    }
    catch (ClassNotFoundException e)
    {
      e.printStackTrace();
    }
  }

  @Override public void updateStateOfRoom(Room room) throws IOException
  {
    try
    {
      output.writeObject("Updating State of Room");
      output.flush();
      String request = (String) input.readObject();
      if(request.equals("Requesting room"))
      {
        output.writeObject(room);
        output.flush();
      }
    }
    catch (ClassNotFoundException e)
    {
      e.printStackTrace();
    }
  }

  @Override public void getAllRoomsNeedingCleaning() throws IOException
  {
    try
    {
      output.writeObject("Requesting Rooms In Need of Cleaning");
      output.flush();
      ArrayList<Room> received = (ArrayList<Room>) input.readObject();
    }
    catch (ClassNotFoundException e)
    {
      e.printStackTrace();
    }
  }

}
