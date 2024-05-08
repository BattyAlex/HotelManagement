package Model;

import java.util.ArrayList;

public abstract class Room
{
  private double price;
  private int numberOfGuests;
  private ArrayList<String> amenities;

  public Room()
  {
    amenities = new ArrayList<String>();
  }

  public void setPrice(double price)
  {
    this.price = price;
  }

  public void setNumberOfGuests(int numberOfGuests)
  {
    this.numberOfGuests = numberOfGuests;
  }

  public void addAmenities(String amenity)
  {
    amenities.add(amenity);
  }

  public double getPrice()
  {
    return price;
  }

  public int getNumberOfGuests()
  {
    return numberOfGuests;
  }

  public ArrayList<String> getAllAmenities()
  {
    return amenities;
  }

}
