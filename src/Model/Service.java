package Model;

import java.io.Serializable;

/**
 * Represents a service that can be offered as part of a hotel reservation
 */
public class Service implements Serializable
{
  public static final String ROOM_SERVICE = "room service";
  public static final String AIRPORT_TRANSPORT = "airport transport";
  public static final String BREAKFAST =  "breakfast";
  public static final String LUNCH = "lunch";
  public static final String DINNER = "dinner";
  public static final String WELLNESS = "wellness";
  private String name;
  private double price;

  /**
   * Constructs a new service with the specific name and price
   * @param name The name of the service
   * @param price The price of the service
   */
  public Service(String name, double price)
  {
    this.name = name;
    this.price = price;
  }

  /**
   * Retrieves the name of the service
   * @return The name of the service
   */
  public String getName()
  {
    return name;
  }

  /**
   * Sets the name of the service
   * @param name The new name of the service
   */
  public void setName(String name)
  {
    this.name = name;
  }

  /**
   * Retrieves the price of the service
   * @return  The price of the service
   */
  public double getPrice()
  {
    return price;
  }

  /**
   * Sets the price of the service
   * @param price The new name of the service
   */
  public void setPrice(double price)
  {
    this.price = price;
  }


}
