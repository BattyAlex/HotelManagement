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

  public static RoomType getInstance(String key)
  {
    if (instances.isEmpty())
    {
      instances.put("Diamond", new RoomType("single", 70));
      instances.put("Gold Nugget", new RoomType("double", 140));
      instances.put("Sapphire", new RoomType("queen sized", 200));
      instances.put("Ruby", new RoomType("king sized", 220));
      instances.put("Wooden Coin", new RoomType("suite", 300));
    }
    if (!instances.containsKey(key))
    {
      instances.put(key, new RoomType(key));
    }
    return instances.get(key);
  }
}
