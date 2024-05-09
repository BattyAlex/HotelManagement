package Model;

import java.util.HashMap;

public class RoomType
{
  private String key;
  private double price;
  private static HashMap<String, RoomType> instances = new HashMap<>();


  public RoomType(String key, double price)
  {
    this.key = key;
    this.price = price;
  }
  public RoomType(String key)
  {
    this.key = key;
    price = 0;
  }

  public static RoomType getInstance(String key, double price)
  {
    if (!instances.containsKey(key))
    {
      instances.put(key, new RoomType(key));
      instances.get(key).setPrice(price);
    }
    instances.get(key).setPrice(price);
    return instances.get(key);
  }

  public void setPrice(double price)
  {
    this.price = price;
  }
}
