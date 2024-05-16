package Server;

import Model.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class ReservationDAO extends DatabaseHandlerFactory
{
  private static ReservationDAO instance;
  private ReservationDAO() throws SQLException
  {
    DriverManager.registerDriver(new org.postgresql.Driver());
  }

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
      statement.setInt(5, reservation.getRoom().getRoomNumber());
      Guest guest = GuestDAO.getInstance().getGuestBasedOnName(reservation.getClient());
      reservation.setClient(guest);
      statement.setInt(6, guest.getId());
      statement.executeUpdate();
      ArrayList<Service> services = reservation.getServices();
      for (int i = 0; i < services.size(); i++)
      {
        ServicesDAO.getInstance().insertServiceForReservation(
            reservation.getReservationId(), services.get(i).getName());
      }
      return reservation;
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    return null;
  }

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

  public Reservation getReservationById(int reservationId)
  {
    try(Connection connection = super.establishConnection())
    {
      PreparedStatement statement = connection.prepareStatement("SELECT reservationNumber, startDate, endDate, numberOfGuests, responsibleStaff, roomNumber, clientId\n"
          + "FROM Reservation\n" + "WHERE reservationNumber = ?;");
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

  public Reservation makeOrUpdateReservation(Reservation reservation)
  {
    try(Connection connection = super.establishConnection())
    {
      Date start = new Date(reservation.getStartDate().getYear()-1900, reservation.getStartDate().getMonthValue() - 1, reservation.getStartDate().getDayOfMonth());
      Date end = new Date(reservation.getEndDate().getYear()-1900, reservation.getEndDate().getMonthValue() - 1, reservation.getEndDate().getDayOfMonth());
      Reservation exists = getReservationByTimeAndRoom(reservation);
      if(exists == null)
      {
        makeReservation(reservation);
      }
      else
      {
        reservation.setReservationId(exists.getReservationId());
        ArrayList<Service> reservationServices = reservation.getServices();
        ArrayList<Service> toUpdate = ServicesDAO.getInstance()
            .getAllServicesForReservation(reservation.getReservationId());
        for (int i = 0; i < reservationServices.size(); i++)
        {
          if (!toUpdate.contains(reservationServices.get(i)))
          {
            ServicesDAO.getInstance().insertServiceForReservation(reservation.getReservationId(), reservationServices.get(i).getName());
          }
        }
        toUpdate = ServicesDAO.getInstance()
            .getAllServicesForReservation(reservation.getReservationId());
        for (int i = 0; i < toUpdate.size(); i++)
        {
          if(!reservationServices.contains(toUpdate.get(i)))
          {
            ServicesDAO.getInstance().deleteServiceForRoom(
                reservation.getReservationId(), toUpdate.get(i).getName());
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
      return reservation;
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    return null;
  }
}
