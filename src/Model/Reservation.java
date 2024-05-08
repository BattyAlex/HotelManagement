package Model;

import java.time.LocalDate;

public class Reservation
{
  private Guest client;
  private Room room;
  private String paymentInformation;
  private LocalDate startDate;
  private LocalDate endDate;
  private int numberOfGuests;

  public Reservation(String payment, LocalDate startDate, LocalDate endDate, Guest client, Room room)
  {
    paymentInformation = payment;
    this.startDate = startDate;
    this.endDate = endDate;
    this.client = client;
    this.room = room;
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
}
