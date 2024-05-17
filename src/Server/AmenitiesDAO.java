package Server;

import java.sql.*;
import java.util.ArrayList;

public class AmenitiesDAO extends DatabaseHandlerFactory
{
  private static AmenitiesDAO instance;
  private AmenitiesDAO() throws SQLException
  {
    DriverManager.registerDriver(new org.postgresql.Driver());
  }

  public static synchronized AmenitiesDAO getInstance()
  {
    try
    {
      if (instance == null)
      {
        instance = new AmenitiesDAO();
      }
      return instance;
    }
    catch (SQLException e)
    {
      e.printStackTrace();
      return null;
    }
  }

  public ArrayList<String> returnAmenitiesForRoom(int roomNumber)
  {
    ArrayList<String> amenities = new ArrayList<>();
    try(Connection connection = super.establishConnection())
    {
      PreparedStatement statement = connection.prepareStatement("SELECT roomNumber, amenityName\n"
          + "FROM Amenities\n" + "WHERE roomNumber = ?;");
      statement.setInt(1, roomNumber);
      ResultSet rs = statement.executeQuery();
      while (rs.next())
      {
        String amenity = rs.getString("amenityName");
        amenities.add(amenity);
      }
      return amenities;
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    return amenities;
  }
}
