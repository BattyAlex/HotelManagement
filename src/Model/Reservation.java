package Model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class Reservation
{
  private Guest client;
  private Room room;
  private String paymentInformation;
  private LocalDate startDate;
  private LocalDate endDate;
  private int lengthOfStay;
  private int numberOfGuests;
  private ArrayList<Service> services;

  public Reservation(String payment, LocalDate startDate, LocalDate endDate, Guest client, Room room)
  {
    paymentInformation = payment;
    this.startDate = startDate;
    this.endDate = endDate;
    this.client = client;
    this.room = room;
    lengthOfStay = (int) ChronoUnit.DAYS.between(startDate,endDate);
    services = new ArrayList<Service>();
  }

  public String getPaymentInformation()
  {
    return paymentInformation;
  }

  public LocalDate getStartDate()
  {
    return startDate;
  }

  public LocalDate getEndDate()
  {
    return endDate;
  }

  public int getNumberOfGuests()
  {
    return numberOfGuests;
  }

  public Room getRoom()
  {
    return room;
  }

  public Guest getClient()
  {
    return client;
  }

  public void setPaymentInformation(String payment)
  {
    this.paymentInformation = payment;
  }

  public void setStartDate(LocalDate date)
  {
    this.startDate = date;
  }

  public void setEndDate(LocalDate date)
  {
    this.endDate = date;
  }

  public void setNumberOfGuests(int numberOfGuests)
  {
    this.numberOfGuests = numberOfGuests;
  }

  public void setClient(Guest client)
  {
    this.client = client;
  }

  public void setRoom(Room room)
  {
    this.room = room;
  }

  public int getLengthOfStay()
  {
    return lengthOfStay;
  }
  public void addService(String name, double price)
  {
    services.add(new Service(name, price));
  }
  public void removeService(String name)
  {
    for(int i = 0; i<services.size(); i++)
    {
      if(services.get(i).getName().equals(name))
        services.remove(i);
    }
  }
  public double getServicesTotal()
  {
    int total = 0;
    for(int i = 0; i<services.size();i++)
    {
      total += services.get(i).getPrice();
    }
    return total;
  }
}
