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
      PreparedStatement statement = connection.prepareStatement("INSERT INTO ReservationServices(reservationNumber, nameOfService) VALUES (?, ?);");
      statement.setString(2, nameOfService);
      statement.setInt(1, reservationId);
      statement.executeUpdate();
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
  }

  public void deleteServiceForRoom(int reservationId, String nameOfService)
  {
    try(Connection connection = super.establishConnection())
    {
      PreparedStatement statement = connection.prepareStatement("DELETE FROM ReservationServices\n"
          + "WHERE reservationNumber = ? AND nameOfService = ?;");
      statement.setInt(1, reservationId);
      statement.setString(2, nameOfService);
      statement.executeUpdate();
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
  }

  public void deleteAllServicesForReservation(int reservationId)
  {
    try(Connection connection = super.establishConnection())
    {
      PreparedStatement statement = connection.prepareStatement("DELETE FROM ReservationServices\n"
          + "WHERE reservationNumber = ?;");
      statement.setInt(1, reservationId);
      statement.executeUpdate();
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
  }

}
