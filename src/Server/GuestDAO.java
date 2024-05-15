package Server;

import Model.Guest;

import java.sql.*;

/**
 * The GuestDAO class provides data access operations for the Guest entity
 * It extends the DatabaseHandlerFactory to assist with the handling of database connection
 * This class uses the Singleton design pattern.
 */
public class GuestDAO extends DatabaseHandlerFactory
{
  private static GuestDAO instance;

  /**
   * Private constructor to prevent instantiation from outside the class
   * It also registers the PostgreSQL driver.
   * @throws SQLException if a database access error occurs
   */
  private GuestDAO() throws SQLException
  {
    DriverManager.registerDriver(new org.postgresql.Driver());
  }

  /**
   * Returns the singleton instance of GuestDAO
   * @return the singleton instance of GuestDAO
   */
  public static synchronized GuestDAO getInstance()
  {
    try
    {
      if (instance == null)
      {
        instance = new GuestDAO();
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
   * Retrieves a Guest based on their full name
   * If the guest does not exist, inserts the guest into the database
   * @param guest the Guest to be retrieved or inserted
   * @return the retrieved or newly inserted Guest or null if an error occurs.
   */
  public Guest getGuestBasedOnName(Guest guest)
  {
    String fullName = guest.getFirstName() + guest.getLastName();
    try(Connection connection = super.establishConnection())
    {
      PreparedStatement statement = connection.prepareStatement("SELECT clientId, name, paymentInformation\n"
          + "FROM Client\n" + "WHERE name = ?;");
      statement.setString(1, fullName);
      ResultSet rs = statement.executeQuery();
      while (rs.next())
      {
        int id = rs.getInt("clientId");
        String paymentInfo = rs.getString("paymentInformation");
        if(paymentInfo.equals(guest.getPaymentInfo()))
        {
          guest.setId(id);
          return guest;
        }
        else
        {
          if(!rs.next())
          {
            return insertGuest(guest);
          }
        }
      }
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * Inserts a new Guest into the database
   * @param guest the Guest to be inserted
   * @return the inserted Guest with the generated client ID or null if an error occurs
   */
  public Guest insertGuest(Guest guest)
  {
    String fullName = guest.getFirstName() + guest.getLastName();
    try(Connection connection = super.establishConnection())
    {
      PreparedStatement statement = connection.prepareStatement("INSERT INTO Client(name, paymentInformation) VALUES (?, ?);");
      statement.setString(1, fullName);
      statement.setString(2, guest.getPaymentInfo());
      statement.executeUpdate();
      ResultSet generatedKeys = statement.getGeneratedKeys();
      if(generatedKeys.next())
      {
        int id = generatedKeys.getInt("clientId");
        guest.setId(id);
      }
      else
      {
        throw new SQLException("No generated keys");
      }
      return guest;
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    return null;
  }
}
