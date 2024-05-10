package Model;

import java.sql.*;

public class UserDAO extends DatabaseHandlerFactory
{
  private static UserDAO instance;
  private UserDAO() throws SQLException
  {
    DriverManager.registerDriver(new org.postgresql.Driver());
  }

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
