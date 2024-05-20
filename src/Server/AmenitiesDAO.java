package Server;

import java.sql.*;
import java.util.ArrayList;

/**
 * The AmenitiesDAO class is responsible for handling the database operations related to amenities.
 * It extends the DatabaseHandlerFactory and implements the Singleton design pattern.
 */

public class AmenitiesDAO extends DatabaseHandlerFactory
{
  private static AmenitiesDAO instance;

  /**
   * Private constructor to prevent instantiation from other classes.
   * It registers the PostgreSQL driver.
   * @throws SQLException if a database access error occurs.
   */
  private AmenitiesDAO() throws SQLException
  {
    DriverManager.registerDriver(new org.postgresql.Driver());
  }

  /**
   * Provides the global point access to the AmenitiesDAO instance.
   * THis method ensures that only one instance of the class is created.
   *
   * @return the single instance of AmenitiesDAO.
   */

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

  /**
   * Returns a list of amenities for a specific room.
   *
   * @param roomNumber the number of the room for which amenities are to be fetched.
   * @return an ArrayList of amenities for the specified room.
   */

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
