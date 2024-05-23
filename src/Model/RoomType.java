package Model;

import java.io.Serializable;
import java.util.HashMap;

/**
 * This class represents a room type in a hotel management system.
 */
public class RoomType implements Serializable
{
  private String key;
  private double price;
  private static HashMap<String, RoomType> instances = new HashMap<>();

  /**
   * Constructs a RoomType instance with a specified key and price.
   * @param key A unique string key identifying the room type.
   * @param price The price associated with this room type
   */
  private RoomType(String key, double price)
  {
    this.key = key;
    this.price = price;
  }
  private RoomType(String key)
  {
    this.key = key;
    price = 0;
  }

  /**
   * Provides a global access point to get a RoomType instance by key and price.
   * If the instance does not exist, it is created and returned. If it exists, the price is updated.
   * @param key The unique for the room type
   * @param price The price to set or update for the room type
   * @return The RoomType instance corresponding to the provided key
   */
  public  static RoomType getInstance(String key, double price)
  {
    if (!instances.containsKey(key))
    {
      instances.put(key, new RoomType(key));
      instances.get(key).setPrice(price);
    }
    instances.get(key).setPrice(price);
    return instances.get(key);
  }

  /**
   * Sets the price of the room
   * @param price the new price to set
   */
  public void setPrice(double price)
  {
    this.price = price;
  }

  /**
   * Gets the type of key
   * @return the key
   */
  public String getType()
  {
    return key;
  }
}
