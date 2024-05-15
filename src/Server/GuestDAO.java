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


  //This is NOT FINISHED, if the client is not in the database, we should insert them
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
          return new Guest(guest.getFirstName(), guest.getLastName(), paymentInfo);
        }
      }
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    return null;
  }
}
