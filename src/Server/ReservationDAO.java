package Server;

import Model.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * The ReservationDAO class is responsible for handling the database operations related to reservations.
 * It extends the DatabaseHandlerFactory and implements the Singleton design pattern.
 */

public class ReservationDAO extends DatabaseHandlerFactory
{
  private static ReservationDAO instance;

  /**
   * Private constructor to prevent instantiation from other classes.
   * It registers the PostgreSQL
   *
   * @throws SQLException if a database access error occurs.
   */
  private ReservationDAO() throws SQLException
  {
    DriverManager.registerDriver(new org.postgresql.Driver());
  }

  /**
   * Provides the global point of access to the ReservationDAO instance.
   * This method ensures that only one instance of the class is created.
   *
   * @return the single instance of ReservationDAO.
   */

  public static synchronized ReservationDAO getInstance()
  {
    try
    {
      if (instance == null)
      {
        instance = new ReservationDAO();
      }
      return instance;
    }
    catch (SQLException e)
    {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * Creates a new reservation in the database.
   *
   * @param reservation the Reservation object containing the reservation details.
   * @return the created Reservation object with updated information from the database.
   */

  public Reservation makeReservation(Reservation reservation)
  {
    try(Connection connection = super.establishConnection())
    {
      PreparedStatement statement = connection.prepareStatement("INSERT INTO Reservation(startDate, endDate, numberOfGuests, responsibleStaff, roomNumber, clientId) VALUES (?, ?, ?, ?, ?, ?);");
      Date startDate = new Date(reservation.getStartDate().getYear()-1900, reservation.getStartDate().getMonthValue() - 1, reservation.getStartDate().getDayOfMonth());
      Date endDate = new Date(reservation.getEndDate().getYear() - 1900, reservation.getEndDate().getMonthValue() - 1, reservation.getEndDate().getDayOfMonth());
      statement.setDate(1, startDate);
      statement.setDate(2, endDate);
      statement.setInt(3, reservation.getNumberOfGuests());
      statement.setString(4, reservation.getStaff().getUsername());
      Room room = RoomDAO.getInstance().getRoomByRoomNumber(reservation.getRoom().getRoomNumber());
      reservation.setRoom(room);
      statement.setInt(5, room.getRoomNumber());
      Guest guest = GuestDAO.getInstance().getGuestBasedOnName(reservation.getClient());
      reservation.setClient(guest);
      statement.setInt(6, guest.getId());
      statement.executeUpdate();
      ArrayList<Service> services = reservation.getServices();
      Reservation currentReservation = getReservationByTimeAndRoom(reservation);
      for (int i = 0; i < services.size(); i++)
      {
        ServicesDAO.getInstance().insertServiceForReservation(
            currentReservation.getReservationId(), services.get(i).getName());
      }
      return currentReservation;
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * Retrives all reservations within a specified period.
   *
   * @param startDate the start date of the period.
   * @param endDate the end date of the period.
   * @return an ArrayList of reservations within the specified period.
   */

  public ArrayList<Reservation> getAllReservationsForPeriod(LocalDate startDate, LocalDate endDate)
  {

    ArrayList<Reservation> reservations = new ArrayList<>();
    Date start = new Date(startDate.getYear()-1900, startDate.getMonthValue() - 1, startDate.getDayOfMonth());
    Date end = new Date(endDate.getYear() - 1900, endDate.getMonthValue() - 1, endDate.getDayOfMonth());
    try(Connection connection = super.establishConnection())
    {
      PreparedStatement statement = connection.prepareStatement("SELECT reservationNumber, startDate, endDate, numberOfGuests, responsibleStaff, roomNumber, clientId\n"
          + "FROM Reservation\n"
          + "WHERE startDate < ? AND endDate > ?;");
      statement.setDate(1, end);
      statement.setDate(2, start);
      ResultSet rs = statement.executeQuery();
      while (rs.next())
      {
        int reservation = rs.getInt("reservationNumber");
        int numberOfGuests = rs.getInt("numberOfGuests");
        Staff staff = new Staff(rs.getString("responsibleStaff"), "");
        Room room = RoomDAO.getInstance().getRoomByRoomNumber(rs.getInt("roomNumber"));
        Guest guest = GuestDAO.getInstance().getGuestBasedOnId(rs.getInt("clientId"));
        Reservation element = new Reservation(rs.getDate("startDate").toLocalDate(), rs.getDate("endDate").toLocalDate(), guest, room, staff);
        element.setNumberOfGuests(numberOfGuests);
        element.setReservationId(reservation);
        ArrayList<Service> services = ServicesDAO.getInstance()
            .getAllServicesForReservation(reservation);
        for (int i = 0; i < services.size(); i++)
        {
          element.addService(services.get(i).getName(), services.get(i).getPrice());
        }
        reservations.add(element);
      }
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    return reservations;
  }

  /**
   * Retrieves all reservations from the database.
   *
   * @return an ArrayList of all reservations.
   */

  public ArrayList<Reservation> getAllReservations()
  {

    ArrayList<Reservation> reservations = new ArrayList<>();
    try(Connection connection = super.establishConnection())
    {
      PreparedStatement statement = connection.prepareStatement("SELECT reservationNumber, startDate, endDate, numberOfGuests, responsibleStaff, roomNumber, clientId\n"
          + "FROM Reservation;");
      ResultSet rs = statement.executeQuery();
      while (rs.next())
      {
        int reservation = rs.getInt("reservationNumber");
        int numberOfGuests = rs.getInt("numberOfGuests");
        Staff staff = new Staff(rs.getString("responsibleStaff"), "");
        Room room = RoomDAO.getInstance().getRoomByRoomNumber(rs.getInt("roomNumber"));
        Guest guest = GuestDAO.getInstance().getGuestBasedOnId(rs.getInt("clientId"));
        Reservation element = new Reservation(rs.getDate("startDate").toLocalDate(), rs.getDate("endDate").toLocalDate(), guest, room, staff);
        element.setNumberOfGuests(numberOfGuests);
        element.setReservationId(reservation);
        ArrayList<Service> services = ServicesDAO.getInstance()
            .getAllServicesForReservation(reservation);
        for (int i = 0; i < services.size(); i++)
        {
          element.addService(services.get(i).getName(), services.get(i).getPrice());
        }
        reservations.add(element);
      }
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    return reservations;
  }

  /**
   * Retrievd a reservation by its ID.
   *
   * @param reservationId the ID of the reservation
   * @return  the Reservation object if found, null otherwise.
   */

  public Reservation getReservationById(int reservationId)
  {
    try(Connection connection = super.establishConnection())
    {
      PreparedStatement statement = connection.prepareStatement("SELECT reservationNumber, startDate, endDate, numberOfGuests, responsibleStaff, roomNumber, clientId\n"
          + "FROM Reservation\n" + "WHERE reservationNumber = ?;");
      statement.setInt(1, reservationId);
      ResultSet rs = statement.executeQuery();
      if(rs.next())
      {
        int reservationNumber = rs.getInt("reservationNumber");
        LocalDate startDate = rs.getDate("startDate").toLocalDate();
        LocalDate endDate = rs.getDate("endDate").toLocalDate();
        int numberOfGuests = rs.getInt("numberOfGuests");
        Staff staff = new Staff(rs.getString("responsibleStaff"), "");
        Room room = RoomDAO.getInstance().getRoomByRoomNumber(rs.getInt("roomNumber"));
        Guest guest = GuestDAO.getInstance().getGuestBasedOnId(rs.getInt("clientId"));
        Reservation reservation = new Reservation(startDate, endDate, guest, room, staff);
        reservation.setReservationId(reservationNumber);
        reservation.setNumberOfGuests(numberOfGuests);
        ArrayList<Service> services = ServicesDAO.getInstance()
            .getAllServicesForReservation(reservationNumber);
        for (int i = 0; i < services.size(); i++)
        {
          reservation.addService(services.get(i).getName(), services.get(i).getPrice());
        }
        return reservation;
      }
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * Retrieves a reservation by the reservation's start date, end date and room number.
   *
   * @param reservation the Reservation object containing the start date, end date and room number.
   * @return the found Reservation object with complete details, otherwise null.
   */

  public Reservation getReservationByTimeAndRoom(Reservation reservation)
  {
    try(Connection connection = super.establishConnection())
    {
      Date start = new Date(reservation.getStartDate().getYear()-1900, reservation.getStartDate().getMonthValue() - 1, reservation.getStartDate().getDayOfMonth());
      Date end = new Date(reservation.getEndDate().getYear() - 1900, reservation.getEndDate().getMonthValue() - 1, reservation.getEndDate().getDayOfMonth());
      PreparedStatement statement = connection.prepareStatement("SELECT reservationNumber, startDate, endDate, numberOfGuests, responsibleStaff, roomNumber, clientId\n"
          + "FROM Reservation\n"
          + "WHERE startDate = ? AND endDate = ? AND roomNumber = ?;");
      statement.setDate(1, start);
      statement.setDate(2, end);
      statement.setInt(3, reservation.getRoom().getRoomNumber());
      ResultSet rs = statement.executeQuery();
      if(rs.next())
      {
        int reservationNumber = rs.getInt("reservationNumber");
        LocalDate startDate = rs.getDate("startDate").toLocalDate();
        LocalDate endDate = rs.getDate("endDate").toLocalDate();
        int numberOfGuests = rs.getInt("numberOfGuests");
        Staff staff = new Staff(rs.getString("responsibleStaff"), "");
        Room room = RoomDAO.getInstance().getRoomByRoomNumber(rs.getInt("roomNumber"));
        Guest guest = GuestDAO.getInstance().getGuestBasedOnId(rs.getInt("clientId"));
        Reservation returning = new Reservation(startDate, endDate, guest,
            room, staff);
        returning.setReservationId(reservationNumber);
        returning.setNumberOfGuests(numberOfGuests);
        ArrayList<Service> services = ServicesDAO.getInstance()
            .getAllServicesForReservation(reservationNumber);
        for (int i = 0; i < services.size(); i++)
        {
          returning.addService(services.get(i).getName(),
              services.get(i).getPrice());
        }
        return returning;
      }
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * Updates the start date of a reservation.
   *
   * @param startDate the new start date
   * @param reservationId the ID of the reservation to be updated
   */

  public void updateStartDate(LocalDate startDate, int reservationId)
  {
    try(Connection connection = super.establishConnection())
    {
      PreparedStatement statement = connection.prepareStatement("UPDATE Reservation\n"
          + "SET startDate = ?\n" + "WHERE reservationNumber = ?;");
      Date start = new Date(startDate.getYear()-1900, startDate.getMonthValue() - 1, startDate.getDayOfMonth());
      statement.setDate(1, start);
      statement.setInt(2, reservationId);
      statement.executeUpdate();
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
  }

  /**
   * Updates the end date of the reservation.
   *
   * @param endDate the new end date.
   * @param reservationId the ID of the reservation to be updated
   */

  public void updateEndDate(LocalDate endDate, int reservationId)
  {
    try(Connection connection = super.establishConnection())
    {
      PreparedStatement statement = connection.prepareStatement("UPDATE Reservation\n"
          + "SET endDate = ?\n" + "WHERE reservationNumber = ?;");
      Date end = new Date(endDate.getYear()-1900, endDate.getMonthValue() - 1, endDate.getDayOfMonth());
      statement.setDate(1, end);
      statement.setInt(2, reservationId);
      statement.executeUpdate();
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
  }

  /**
   * Updates the number of guests for a reservation.
   *
   * @param numOfGuests the new number of guests.
   * @param reservationId the ID of the reservation to be updated.
   */

  public void updateNumberOfGuests(int numOfGuests, int reservationId)
  {
    try(Connection connection = super.establishConnection())
    {
      PreparedStatement statement = connection.prepareStatement("UPDATE Reservation\n"
          + "SET numberOfGuests = ?\n" + "WHERE reservationNumber = ?;");
      statement.setInt(1, numOfGuests);
      statement.setInt(2, reservationId);
      statement.executeUpdate();
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
  }

  /**
   * Update the room number for a reservation.
   *
   * @param roomNumber the new room number.
   * @param reservationId the ID of the reservation to be updated.
   */

  public void updateRoomNumber(int roomNumber, int reservationId)
  {
    try(Connection connection = super.establishConnection())
    {
      PreparedStatement statement = connection.prepareStatement("UPDATE Reservation\n"
          + "SET roomNumber = ?\n" + "WHERE reservationNumber = ?;");
      statement.setInt(1, roomNumber);
      statement.setInt(2, reservationId);
      statement.executeUpdate();
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
  }
  /**
   * Updates the responsible staff member for a specific reservation in the database.
   *
   * @param username The username of the staff member to be assigned to the reservation.
   * @param reservationId The unique identifier of the reservation to be updated.
   */

  public void updateResponsibleStaff(String username, int reservationId)
  {
    try(Connection connection = super.establishConnection())
    {
      PreparedStatement statement = connection.prepareStatement("UPDATE Reservation\n"
          + "SET responsibleStaff = ?\n" + "WHERE reservationNumber = ?;");
      statement.setString(1, username);
      statement.setInt(2, reservationId);
      statement.executeUpdate();
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
  }

  /**
   *  Creates a new reservation or updates an existing reservation in the database.
   *
   * @param reservation The reservation object counting the details to be inserted or updated.
   * @return The updated or newly created reservation.
   */

  public Reservation makeOrUpdateReservation(Reservation reservation)
  {
    try(Connection connection = super.establishConnection())
    {
      Date start = new Date(reservation.getStartDate().getYear()-1900, reservation.getStartDate().getMonthValue() - 1, reservation.getStartDate().getDayOfMonth());
      Date end = new Date(reservation.getEndDate().getYear()-1900, reservation.getEndDate().getMonthValue() - 1, reservation.getEndDate().getDayOfMonth());
      Reservation exists;
      if(reservation.getReservationId() == -1)
      {
        exists = getReservationByTimeAndRoom(reservation);
      }
      else
      {
        exists = getReservationById(reservation.getReservationId());
      }
      if(exists == null)
      {
        makeReservation(reservation);
      }
      else
      {
        reservation.setReservationId(exists.getReservationId());
        Room room = RoomDAO.getInstance().getRoomByRoomNumber(reservation.getRoom().getRoomNumber());
        reservation.setRoom(room);
        ArrayList<Service> reservationServices = reservation.getServices();
        ArrayList<Service> toUpdate = ServicesDAO.getInstance()
            .getAllServicesForReservation(reservation.getReservationId());
        for (int i = 0; i < reservationServices.size(); i++)
        {
          boolean contains = false;
          for (int j = 0; j < toUpdate.size(); j++)
          {
            if(reservationServices.get(i).getName().equals(toUpdate.get(j).getName()))
            {
              contains = true;
            }

          }
          if(!contains)
          {
            ServicesDAO.getInstance().insertServiceForReservation(reservation.getReservationId(), reservationServices.get(i).getName());
          }
        }
        toUpdate = ServicesDAO.getInstance()
            .getAllServicesForReservation(reservation.getReservationId());
        for (int i = 0; i < toUpdate.size(); i++)
        {
          boolean contains = false;
          for (int j = 0; j < reservationServices.size(); j++)
          {
            if(toUpdate.get(i).getName().equals(reservationServices.get(j).getName()))
            {
              contains = true;
            }
          }
          if(!contains)
          {
            ServicesDAO.getInstance().deleteServiceForRoom(reservation.getReservationId(), toUpdate.get(i).getName());
          }
        }
        if (exists.getStartDate() != reservation.getStartDate())
        {
          updateStartDate(reservation.getStartDate(),
              reservation.getReservationId());
        }
        if (exists.getEndDate() != reservation.getEndDate())
        {
          updateEndDate(reservation.getEndDate(), reservation.getReservationId());
        }
        if (exists.getNumberOfGuests() != reservation.getNumberOfGuests())
        {
          updateNumberOfGuests(reservation.getNumberOfGuests(),
              reservation.getReservationId());
        }
        if (exists.getRoom().getRoomNumber() != reservation.getRoom().getRoomNumber())
        {
          updateRoomNumber(reservation.getRoom().getRoomNumber(),
              reservation.getReservationId());
        }
        if(!exists.getStaff().getUsername().equals(reservation.getStaff().getUsername()))
        {
          updateResponsibleStaff(reservation.getStaff().getUsername(), reservation.getReservationId());
        }
      }
      reservation.setServices(ServicesDAO.getInstance().getAllServicesForReservation(reservation.getReservationId()));
      reservation = getReservationByTimeAndRoom(reservation);
      return reservation;
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    return null;
  }


  /**
   * Deletes a reservation from the database.
   *
   * @param reservation The reservation object containing the details of the reservation to be deleted.
   * @return The deleted reservation.
   */
  public Reservation deleteReservation(Reservation reservation)
  {
    try(Connection connection = super.establishConnection())
    {
      Reservation toDelete = getReservationByTimeAndRoom(reservation);
      ServicesDAO.getInstance().deleteAllServicesForReservation(
          toDelete.getReservationId());
      PreparedStatement statement = connection.prepareStatement("DELETE FROM Reservation\n"
          + "WHERE reservationNumber = ?;");
      statement.setInt(1, toDelete.getReservationId());
      statement.executeUpdate();
      return toDelete;
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    return null;
  }
}
