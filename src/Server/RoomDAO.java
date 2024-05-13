package Server;

import Model.Room;

import java.sql.*;
import java.util.ArrayList;

public class RoomDAO extends DatabaseHandlerFactory
{
  private static RoomDAO instance;

  private RoomDAO() throws SQLException
  {
    DriverManager.registerDriver(new org.postgresql.Driver());
  }

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
  public ArrayList<Room> getAllRooms()
  {
    ArrayList<Room> temp = new ArrayList<>();
    int currentRoomNumber = -1;
    int incrementor = 0;
    try (Connection connection = super.establishConnection())
    {
      PreparedStatement statement = connection.prepareStatement(
          "SELECT Room.roomNumber, amenityName, RoomType.typeOfRoom, price, Room.roomNumber, stateOfRoom\n"
              + "FROM Room LEFT OUTER JOIN Amenities ON (Room.roomNumber = Amenities.roomNumber), RoomType\n"
              + "WHERE Room.typeOfRoom = RoomType.typeOfRoom\n"
              + "ORDER BY Room.roomNumber;");
      ResultSet rs = statement.executeQuery();
      while (rs.next())
      {
        String amenity = rs.getString("amenityName");
        String typeOfRoom = rs.getString("typeOfRoom");
        double price = rs.getInt("price");
        int roomNumber = rs.getInt("roomNumber");
        String stateOfRoom = rs.getString("stateOfRoom");
        if(roomNumber != currentRoomNumber)
        {
          if (!(currentRoomNumber == -1))
          {
            incrementor ++;
          }
          currentRoomNumber = roomNumber;
          temp.add(new Room(typeOfRoom, price, roomNumber, stateOfRoom));
          if(amenity != null)
          {
            temp.get(incrementor).addAmenities(amenity);
          }
        }
        else
        {
          if(amenity != null)
          {
            temp.get(incrementor).addAmenities(amenity);
          }
        }
      }
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    return temp;
  }

  public ArrayList<Room> getAllAvailableRooms(Date startDate, Date endDate)
  {
    ArrayList<Room> temp = new ArrayList<>();
    int currentRoomNumber = -1;
    int incrementor = 0;
    try (Connection connection = super.establishConnection())
    {
      PreparedStatement statement = connection.prepareStatement(
          "SELECT Room.roomNumber, amenityName, RoomType.typeOfRoom, price, Room.roomNumber, stateOfRoom\n"
              + "FROM Room LEFT OUTER JOIN Amenities ON (Room.roomNumber = Amenities.roomNumber), RoomType, Reservation\n"
              + "WHERE Room.typeOfRoom = RoomType.typeOfRoom AND Room.roomNumber NOT IN (SELECT Room.roomNumber\n"
              + "FROM Room, Reservation\n"
              + "WHERE Room.roomNumber = Reservation.roomNumber AND (Reservation.startDate < ? OR Reservation.endDate > ?))\n"
              + "ORDER BY Room.roomNumber;");
      statement.setDate(1, endDate);
      statement.setDate(2, startDate);
      ResultSet rs = statement.executeQuery();
      while (rs.next())
      {
        String amenity = rs.getString("amenityName");
        String typeOfRoom = rs.getString("typeOfRoom");
        double price = rs.getInt("price");
        int roomNumber = rs.getInt("roomNumber");
        String stateOfRoom = rs.getString("stateOfRoom");
        if(roomNumber != currentRoomNumber)
        {
          if (!(currentRoomNumber == -1))
          {
            incrementor ++;
          }
          currentRoomNumber = roomNumber;
          temp.add(new Room(typeOfRoom, price, roomNumber, stateOfRoom));
          if(amenity != null)
          {
            temp.get(incrementor).addAmenities(amenity);
          }
        }
        else
        {
          if(amenity != null)
          {
            temp.get(incrementor).addAmenities(amenity);
          }
        }
      }
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    return temp;
  }

  public

  
}
