package Server;

import Model.Guest;
import Model.Reservation;
import Model.Room;
import Model.Staff;

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

  //This does not insert the services yet. Not good!
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
        Guest guest;
      }
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    return reservations;
  }
}
