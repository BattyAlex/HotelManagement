package Server;

import Model.Service;

import java.sql.*;
import java.util.ArrayList;

/**
 * The ServicesDAO class provides data access methods for service-related operations.
 * This class follows the Singleton pattern and extends the DatabaseHandlerFactory class.
 */

public class ServicesDAO extends DatabaseHandlerFactory
{
  private static ServicesDAO instance;

  /**
   * Private constructor to prevent instantiation.
   * Registers the PostgreSQL driver.
   *
   * @throws SQLException if there is an error registering the driver
   */
  private ServicesDAO() throws SQLException
  {
    DriverManager.registerDriver(new org.postgresql.Driver());
  }

  /**
   * Returns the singleton instance of the ServiceDAO class.
   *
   * @return the singleton instance of ServicesDAO.
   */

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

  /**
   * Retrieves all services for a given reservation.
   *
   * @param reservationId the ID of the reservation.
   * @return a list of services for the given reservation.
   */

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

  /**
   * Inserts a service for a given reservation.
   *
   * @param reservationId the ID of the reservation.
   * @param nameOfService the name of the service to be inserted.
   */

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

  /**
   * Deletes a service for a given reservation.
   *
   * @param reservationId the ID of the reservation.
   * @param nameOfService the name of the service to be deleted.
   */

  public void deleteServiceForReservation(int reservationId, String nameOfService)
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

  /**
   * Deletes all services for a given reservation.
   *
   * @param reservationId the ID of the reservation.
   */

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
