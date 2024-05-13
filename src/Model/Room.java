package Model;

import java.util.ArrayList;

/**
 * This class represents a room in a hotel management system
 */
public class Room
{
  private int roomNumber;
  private double price;
  private ArrayList<String> amenities = new ArrayList<String>();
  private RoomType type;
  private String state;

  /**
   * Constructs a new Room with specified details.
   * @param type The type of the room.
   * @param price The price of the room.
   * @param roomNumber The room number.
   * @param state The current state of the room.
   */
  public Room(String type, double price, int roomNumber, String state)
  {
    this.type = RoomType.getInstance(type, price);
    this.price = price;
    this.roomNumber = roomNumber;
    this.state = state;
  }

  /**
   * Sets the price of the room
   * @param price the n ew price to set
   */
  public void setPrice(double price)
  {
    this.price = price;
  }

  /**
   * Gets the room number
   * @return the room number
   */
  public int getRoomNumber()
  {
    return roomNumber;
  }

  /**
   * Gets the state of the room
   * @return the state of the room
   */
  public String getState()
  {
    return state;
  }

  /**
   * Gets the type of the room
   * @return the type of the room
   */
  public String getType()
  {
    return type.getType();
  }

  /**
   * Adds an amenity to the room.
   * @param amenity The amenity to add to the room
   */
  public void addAmenities(String amenity)
  {
    amenities.add(amenity);
  }

  /**
   * Gets the price of the room
   * @return the price of the room
   */
  public double getPrice()
  {
    return price;
  }

  /**
   * Retrieves a list of all amenities available in the room
   * @return An arraylist containing the amenities.
   */
  public ArrayList<String> getAllAmenities()
  {
    return amenities;
  }

}
