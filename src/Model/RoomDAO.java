package Model;

import java.sql.*;
import java.util.ArrayList;

public class RoomDAO extends DatabaseHandlerFactory
{

  public RoomDAO() throws SQLException
  {
    DriverManager.registerDriver(new org.postgresql.Driver());
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
}
