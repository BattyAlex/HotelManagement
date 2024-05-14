package Server;

import Model.Staff;

import java.sql.*;

/**
 * The UserDAO class handles the data access operations related to the User entity
 * It extends the DatabaseHandlerFactory class and implements the Singleton pattern.
 */
public class UserDAO extends DatabaseHandlerFactory
{
  private static UserDAO instance;

  /**
   * Private constructor to prevent instantiation
   * Registers the postgresSQL driver
   * @throws SQLException If an error occurs during driver registration
   */
  private UserDAO() throws SQLException
  {
    DriverManager.registerDriver(new org.postgresql.Driver());
  }

  /**
   * Provides the singleton instance of UserDAO
   * @return The Singleton instance of UserDAO
   */
  public static UserDAO getInstance()
  {
    try
    {
      if(instance == null)
      {
        instance = new UserDAO();
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
   * Retrieves a staff member based on the provided username.
   * @param username The username of the Staff member to retrieve
   * @return A Staff object if a matching username is found, otherwise null
   */
  public Staff getStaffBasedOnUsername(String username)
  {
    try(Connection connection = super.establishConnection())
    {
      PreparedStatement statement = connection.prepareStatement("SELECT username, password\n"
          + "FROM Staff\n" + "WHERE username = ?;");
      statement.setString(1, username);
      ResultSet rs = statement.executeQuery();
      while (rs.next())
      {
        return new Staff(rs.getString("username"), rs.getString("password"));
      }
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    return null;
  }
}
