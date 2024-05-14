package Server;

import Model.Guest;

import java.sql.*;

public class GuestDAO extends DatabaseHandlerFactory
{
  private static GuestDAO instance;
  private GuestDAO() throws SQLException
  {
    DriverManager.registerDriver(new org.postgresql.Driver());
  }

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

  public Guest getGuestBasedOnName(String firstName, String lastName)
  {
    String fullName = firstName + lastName;
    try(Connection connection = super.establishConnection())
    {
      PreparedStatement statement = connection.prepareStatement("SELECT clientId, name, paymentInformation\n"
          + "FROM Client\n" + "WHERE name = 'Karen Smith';");
      ResultSet rs = statement.executeQuery();
      while (rs.next())
      {
        int id = rs.getInt("clientId");
        String paymentInfo = rs.getString("paymentInformation");
        return new Guest(firstName, lastName, paymentInfo, id);
      }
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    return null;
  }
}
