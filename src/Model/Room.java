package Model;

import java.util.ArrayList;

public class Room
{
  private int roomNumber;
  private double price;
  private ArrayList<String> amenities = new ArrayList<String>();
  private RoomType type;
  private String state;

  public Room(String type, double price, int roomNumber, String state)
  {
    this.type = RoomType.getInstance(type, price);
    this.price = price;
    this.roomNumber = roomNumber;
    this.state = state;
  }


  public void setPrice(double price)
  {
    this.price = price;
  }

  public int getRoomNumber()
  {
    return roomNumber;
  }

  public String getState()
  {
    return state;
  }

  public String getType()
  {
    return type.getType();
  }

  public void addAmenities(String amenity)
  {
    amenities.add(amenity);
  }

  public double getPrice()
  {
    return price;
  }


  public ArrayList<String> getAllAmenities()
  {
    return amenities;
  }

}
