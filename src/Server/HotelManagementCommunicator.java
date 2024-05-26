package Server;

import Model.Reservation;
import Model.Room;
import Model.Staff;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
/**
 * The HotelManagementCommunicator class implements the Runnable interface to handle
 * communication with the hotel management system
 */


public class HotelManagementCommunicator implements Runnable
{
  private final Socket socket;
  private final UDPBroadcaster broadcaster;
  private ObjectInputStream reader;
  private ObjectOutputStream writer;
  private final UserDAO userDAO;
  private final RoomDAO roomDAO;
  private final ReservationDAO reservationDAO;

  /**
   * Constructs a new HotelManagementCommunicator with the specified socket and UDP broadcaster.
   * @param socket  the socket for communication
   * @param broadcaster  the UDP broadcaster for message broadcasting
   */

  public HotelManagementCommunicator(Socket socket, UDPBroadcaster broadcaster)
  {
    this.socket = socket;
    this.broadcaster = broadcaster;
    userDAO = UserDAO.getInstance();
    roomDAO = RoomDAO.getInstance();
    reservationDAO = ReservationDAO.getInstance();
  }

  /**
   * Handles communication over the socket. This method listens for incoming messages, processes them
   * and sends appropriate responses. It runs in a loop until the connection is closed or an error occurs.
   * @throws IOException if an I0 error occurs while communicating.
   */

  private void communicate() throws IOException
  {
    reader = new ObjectInputStream(socket.getInputStream());
    writer = new ObjectOutputStream(socket.getOutputStream());
    try
    {
      loop: while (true)
      {
        String text = "";
        try
        {
          text = (String) reader.readObject();
        }
        catch (EOFException e)
        {
          break loop;
        }
        if(text == null)
        {
          break loop;
        }
        else {
          switch (text)
          {
            case "Login attempt" -> tryLogin();
            case "Requesting All Rooms" ->
            {
              ArrayList<Room> sendOver = roomDAO.getAllRooms();
              writer.writeObject(sendOver);
              writer.flush();
            }
            case "Request Rooms of Specific Period" ->
              getRoomsForSpecificPeriod();

            case "Requesting All Reservations" ->
            {
              ArrayList<Reservation> sendOver = reservationDAO.getAllReservations();
              writer.writeObject(sendOver);
              writer.flush();
            }
            case "Requesting Reservations of Specific Period" ->
                getReservationsForSpecificPeriod();
            case "Get room with room number" ->
            {
              getRoomWithRoomNumber();
            }
            case "Making or Updating Reservation" ->
            {
              makeOrUpdateReservation();
            }
            case "Deleting reservation" ->
            {
              deleteReservation();
            }
            case "Updating State of Room" ->
            {
              writer.writeObject("Requesting room");
              writer.flush();
              Room toClean = (Room) reader.readObject();
              roomDAO.updateStateOfRoom(toClean);
            }
            case "Requesting Rooms Needing Cleaning" ->
            {
              ArrayList<Room> sendOver = roomDAO.getRoomsInNeedOfCleaning();
              writer.writeObject(sendOver);
              writer.flush();
            }
          }
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
   * The method for deleting a reservation from the database
   * @throws IOException if an I0 error occurs while communicating.
   * @throws ClassNotFoundException in case and input is cast to the wrong class
   */
  private void deleteReservation() throws IOException, ClassNotFoundException
  {
    writer.writeObject("Which Reservation?");
    writer.flush();
    Reservation received = (Reservation) reader.readObject();
    Reservation sendOver = reservationDAO.deleteReservation(received);
    writer.writeObject(sendOver);
    writer.flush();
  }

  /**
   * The method for making or updating an existing reservation from the database
   * @throws IOException if an I0 error occurs while communicating.
   * @throws ClassNotFoundException in case and input is cast to the wrong class
   */

  private void makeOrUpdateReservation() throws IOException, ClassNotFoundException
  {
    writer.writeObject("Reservation needed");
    writer.flush();
    Reservation received = (Reservation) reader.readObject();
    Reservation sendBack = reservationDAO.makeOrUpdateReservation(received);
    writer.writeObject(sendBack);
    writer.flush();
  }

  /**
   * The method for returning a room based on a room number from the database
   * @throws IOException if an I0 error occurs while communicating.
   * @throws ClassNotFoundException in case and input is cast to the wrong class
   */
  private void getRoomWithRoomNumber() throws IOException, ClassNotFoundException
  {
    writer.writeObject("Which room?");
    writer.flush();
    int roomNumber = (int) reader.readObject();
    Room sendOver = roomDAO.getRoomByRoomNumber(roomNumber);
    writer.writeObject(sendOver);
    writer.flush();
  }

  /**
   * The method for getting all the reservations for a specific time frame from the database
   * @throws IOException if an I0 error occurs while communicating.
   * @throws ClassNotFoundException in case and input is cast to the wrong class
   */
  private void getReservationsForSpecificPeriod() throws IOException, ClassNotFoundException
  {
    writer.writeObject("Start Date?");
    writer.flush();
    LocalDate startDate = (LocalDate) reader.readObject();
    writer.writeObject("End Date?");
    writer.flush();
    LocalDate endDate = (LocalDate) reader.readObject();
    ArrayList<Reservation> reservations = reservationDAO
        .getAllReservationsForPeriod(startDate, endDate);
    writer.writeObject(reservations);
    writer.flush();
  }

  /**
   * The method for getting all the rooms available between a specific time frame from the database
   * @throws IOException if an I0 error occurs while communicating.
   * @throws ClassNotFoundException in case and input is cast to the wrong class
   */
  private void getRoomsForSpecificPeriod() throws IOException, ClassNotFoundException
  {
    Date startDate;
    Date endDate;
    writer.writeObject("Start Date?");
    writer.flush();
    LocalDate receivedStart = (LocalDate) reader.readObject();
    startDate = new Date(receivedStart.getYear()-1900, receivedStart.getMonthValue()-1, receivedStart.getDayOfMonth());
    writer.writeObject("End Date?");
    writer.flush();
    LocalDate receivedEnd = (LocalDate) reader.readObject();
    endDate = new Date(receivedEnd.getYear()-1900, receivedEnd.getMonthValue()-1, receivedEnd.getDayOfMonth());
    ArrayList<Room> available = roomDAO.getAllAvailableRooms(startDate, endDate);
    writer.writeObject(available);
    writer.flush();
  }

  /**
   * The method for retrieving a user from the database to check if the user can log in
   * @throws IOException if an I0 error occurs while communicating.
   * @throws ClassNotFoundException in case and input is cast to the wrong class
   */
  private void tryLogin() throws IOException, ClassNotFoundException
  {
    writer.writeObject("Which staff?");
    writer.flush();
    Staff confirmation = (Staff) reader.readObject();
    Staff loginRequest = userDAO.getStaffBasedOnUsername(
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
