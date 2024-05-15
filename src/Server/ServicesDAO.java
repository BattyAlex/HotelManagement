package Server;

import Model.Service;

import java.sql.*;
import java.util.ArrayList;

public class ServicesDAO extends DatabaseHandlerFactory
{
  private static ServicesDAO instance;
  private ServicesDAO() throws SQLException
  {
    DriverManager.registerDriver(new org.postgresql.Driver());
  }

  public static synchronized ServicesDAO getInstance()
  {
    try
    {
      if (instance == null)
      {
        instance = new ServicesDAO();
      }
      return instance;
    }
    catch (SQLException e)
    {
      e.printStackTrace();
      return null;
    }
  }

  public ArrayList<Service> getAllServicesForReservation(int reservationId)
  {
    ArrayList<Service> services = new ArrayList<>();
    try(Connection connection = super.establishConnection())
    {
      PreparedStatement statement = connection.prepareStatement("SELECT price, Service.nameOfService\n"
          + "FROM Service, ReservationServices\n"
          + "WHERE Service.nameOfService = ReservationServices.nameOfService AND ReservationServices.reservationNumber = ?;");
      statement.setInt(1, reservationId);
      ResultSet rs = statement.executeQuery();
      while (rs.next())
      {
        String nameOfService = rs.getString("nameOfService");
        double price = rs.getInt("price");
        services.add(new Service(nameOfService, price));
      }
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    return services;
  }

  public void insertServiceForReservation(int reservationId, String nameOfService)
  {
    try(Connection connection = super.establishConnection())
    {
      PreparedStatement statement = connection.prepareStatement("INSERT INTO Amenities(amenityName, roomNumber) VALUES (?, ?);");
      statement.setString(1, nameOfService);
      statement.setInt(2, reservationId);
      statement.executeUpdate();
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
  }
}