package Server;

import Model.Room;

import java.sql.*;
import java.util.ArrayList;

/**
 * The RoomDAO class handles the data access operations related to the Room entity.
 * It extends the DatabaseHandlerFactory class and implements the Singleton pattern.
 */
public class RoomDAO extends DatabaseHandlerFactory
{
  private static RoomDAO instance;
  private AmenitiesDAO amenitiesDAO;

  /**
   * Private constructor to prevent instantiation.
   * Registers the PostgreSQL driver.
   * @throws SQLException If an error occurs during driver registration.
   */
  private RoomDAO() throws SQLException
  {
    DriverManager.registerDriver(new org.postgresql.Driver());
    amenitiesDAO = AmenitiesDAO.getInstance();
  }

  /**
   * Provides the singleton instance of RoomDAO.
   * @return The singleton instance of RoomDAO.
   */
  public static synchronized RoomDAO getInstance()
  {
    try
    {
      if(instance == null)
      {
        instance = new RoomDAO();
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
   * Retrieves all rooms from the database.
   * @return An arraylist of Room objects.
   */
  public ArrayList<Room> getAllRooms()
  {
    ArrayList<Room> temp = new ArrayList<>();
    try (Connection connection = super.establishConnection())
    {
      PreparedStatement statement = connection.prepareStatement(
          "SELECT Room.roomNumber, RoomType.typeOfRoom, price, stateOfRoom\n"
              + "FROM Room, RoomType\n"
              + "WHERE Room.typeOfRoom = RoomType.typeOfRoom;");
      ResultSet rs = statement.executeQuery();
      while (rs.next())
      {
        String typeOfRoom = rs.getString("typeOfRoom");
        double price = rs.getInt("price");
        int roomNumber = rs.getInt("roomNumber");
        String stateOfRoom = rs.getString("stateOfRoom");
        Room returning = new Room(typeOfRoom, price, roomNumber, stateOfRoom);
        ArrayList<String> amenities = amenitiesDAO
            .returnAmenitiesForRoom(roomNumber);
        for (int i = 0; i < amenities.size(); i++)
        {
          returning.addAmenities(amenities.get(i));
        }
        temp.add(returning);
      }
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    return temp;
  }

  /**
   * Retrieves all available rooms from the database for the given date range
   * @param startDate The start date of the availability period.
   * @param endDate The end date of the availability period
   * @return An arraylist of available Room objects.
   */

  public ArrayList<Room> getAllAvailableRooms(Date startDate, Date endDate)
  {
    ArrayList<Room> temp = new ArrayList<>();
    try (Connection connection = super.establishConnection())
    {
      PreparedStatement statement = connection.prepareStatement(
          "SELECT Room.roomNumber, RoomType.typeOfRoom, price, stateOfRoom\n"
              + "FROM Room, RoomType\n"
              + "WHERE Room.typeOfRoom = RoomType.typeOfRoom AND roomNumber NOT IN\n"
              + "(\n" + "SELECT Room.roomNumber\n" + "FROM Room, Reservation\n"
              + "WHERE Room.roomNumber = Reservation.roomNumber AND (Reservation.startDate < ? AND Reservation.endDate > ?)\n"
              + ");");
      statement.setDate(1, endDate);
      statement.setDate(2, startDate);
      ResultSet rs = statement.executeQuery();
      while (rs.next())
      {
        String typeOfRoom = rs.getString("typeOfRoom");
        double price = rs.getInt("price");
        int roomNumber = rs.getInt("roomNumber");
        String stateOfRoom = rs.getString("stateOfRoom");
        Room returning = new Room(typeOfRoom, price, roomNumber, stateOfRoom);
        ArrayList<String> amenities = amenitiesDAO
            .returnAmenitiesForRoom(roomNumber);
        for (int i = 0; i < amenities.size(); i++)
        {
          returning.addAmenities(amenities.get(i));
        }
        temp.add(returning);
      }
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    return temp;
  }

  /**
   * Retrieves a room by its room number from the database
   * @param roomNumber The room number of the desired room
   * @return The Room object if found, otherwise null.
   */
  public Room getRoomByRoomNumber(int roomNumber)
  {
    try(Connection connection = super.establishConnection())
    {
      PreparedStatement statement = connection.prepareStatement("SELECT Room.roomNumber, RoomType.typeOfRoom, price, stateOfRoom\n"
          + "FROM Room, RoomType\n"
          + "WHERE Room.typeOfRoom = RoomType.typeOfRoom AND roomNumber = ?;");
      statement.setInt(1, roomNumber);
      ResultSet rs = statement.executeQuery();
      while (rs.next())
      {
        String typeOfRoom = rs.getString("typeOfRoom");
        double price = rs.getInt("price");
        String stateOfRoom = rs.getString("stateOfRoom");
        Room returning = new Room(typeOfRoom, price, roomNumber, stateOfRoom);
        ArrayList<String> amenities = amenitiesDAO
            .returnAmenitiesForRoom(roomNumber);
        for (int i = 0; i < amenities.size(); i++)
        {
          returning.addAmenities(amenities.get(i));
        }
        return returning;
      }
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * Updates the state of a room in the database based on the given room
   * @param room The room that needs to be updated
   */
  public void updateStateOfRoom(Room room)
  {
    try(Connection connection = super.establishConnection())
    {
      PreparedStatement statement = connection.prepareStatement("UPDATE Room\n"
          + "SET stateOfRoom = ?\n"
          + "WHERE roomNumber = ?;");
      statement.setString(1, room.getState());
      statement.setInt(2, room.getRoomNumber());
      statement.executeUpdate();
    }
    catch (SQLException e)
    {
      System.out.println("Room could not be updated");
      e.printStackTrace();
    }
  }

  /**
   * Returns a list of the rooms in need of cleaning
   * @return The list of rooms
   */
  public ArrayList<Room> getRoomsInNeedOfCleaning()
  {
    ArrayList<Room> temp = new ArrayList<>();
    try(Connection connection = super.establishConnection())
    {
      PreparedStatement statement = connection.prepareStatement("SELECT Room.roomNumber, RoomType.typeOfRoom, price, stateOfRoom\n"
          + "FROM Room, RoomType\n"
          + "WHERE Room.typeOfRoom = RoomType.typeOfRoom AND (stateOfRoom IN ('undergoing cleaning', 'needs cleaning'));");
      ResultSet rs = statement.executeQuery();
      while (rs.next())
      {
        String typeOfRoom = rs.getString("typeOfRoom");
        double price = rs.getInt("price");
        int roomNumber = rs.getInt("roomNumber");
        String stateOfRoom = rs.getString("stateOfRoom");
        Room returning = new Room(typeOfRoom, price, roomNumber, stateOfRoom);
        ArrayList<String> amenities = amenitiesDAO
            .returnAmenitiesForRoom(roomNumber);
        for (int i = 0; i < amenities.size(); i++)
        {
          returning.addAmenities(amenities.get(i));
        }
        temp.add(returning);
      }
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    return temp;
  }
  
}
