package Model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

/**
 *This class represents a reservation in a hotel management system.
 */
public class Reservation implements Serializable
{
  private Guest client;
  private Room room;
  private Staff staff;
  private LocalDate startDate;
  private LocalDate endDate;
  private int lengthOfStay;
  private int numberOfGuests;
  private int reservationId;
  private ArrayList<Service> services;

  /**
   * Constructs a new Reservation with specified details.
   * @param startDate The start date of the stay.
   * @param endDate The end date of the date.
   * @param client The guest making the reservation.
   * @param room The room associated with this reservation.
   * @param staff The staff responsible for making the reservation.
   */
  public Reservation(LocalDate startDate, LocalDate endDate, Guest client, Room room, Staff staff)
  {
    this.startDate = startDate;
    this.endDate = endDate;
    this.client = client;
    this.room = room;
    this.staff = staff;
    lengthOfStay = (int) ChronoUnit.DAYS.between(startDate,endDate);
    services = new ArrayList<Service>();
    reservationId = -1;
  }


  /**
   * Returns the startDate
   * @return the start date.
   */
  public LocalDate getStartDate()
  {
    return startDate;
  }

  /**
   * Returns the end date
   * @return Returns EndDate
   */
  public LocalDate getEndDate()
  {
    return endDate;
  }

  /**
   * Returns number of guests
   * @return the number of guests
   */
  public int getNumberOfGuests()
  {
    return numberOfGuests;
  }

  /**
   * Returns room
   * @return the room
   */
  public Room getRoom()
  {
    return room;
  }

  /**
   * Returns the client
   * @return client
   */
  public Guest getClient()
  {
    return client;
  }

  /**
   * Returns the staff
   * @return the staff to be returned
   */
  public Staff getStaff()
  {
    return staff;
  }

  /**
   * Returns the length of stay
   * @return the length of the stay
   */
  public int getLengthOfStay()
  {
    return lengthOfStay;
  }

  /**
   * Returns the reservation id
   * @return the id of the reservation
   */

  public int getReservationId()
  {
    return reservationId;
  }
  /**
   * Sets the start date
   * @param date the new date to set
   */
  public void setStartDate(LocalDate date)
  {
    this.startDate = date;
  }

  /**
   * Sets the end date
   * @param date the new date to set
   */
  public void setEndDate(LocalDate date)
  {
    this.endDate = date;
  }

  /**
   * Sets the number of guests
   * @param numberOfGuests the new numberOfGuests to set
   */
  public void setNumberOfGuests(int numberOfGuests)
  {
    this.numberOfGuests = numberOfGuests;
  }

  /**
   * Sets the client
   * @param client the new client to set
   */
  public void setClient(Guest client)
  {
    this.client = client;
  }

  /**
   * Sets the room
   * @param room the new room to set
   */
  public void setRoom(Room room)
  {
    this.room = room;
  }

  /**
   * Sets the reservation id
   * @param reservationId the id to be set
   */
  public void setReservationId(int reservationId)
  {
    this.reservationId = reservationId;
  }


  /**
   * Adds a service to the list of additional services.
   * @param name The name of the service.
   * @param price The price of the service.
   */

  public void addService(String name, double price)
  {
    services.add(new Service(name, price));
  }

  /**
   * Removes a service from the list of additional services based on the service name.
   * @param name The name of the service to remove.
   */
  public void removeService(String name)
  {
    for(int i = 0; i<services.size(); i++)
    {
      if(services.get(i).getName().equals(name))
        services.remove(i);
    }
  }

  /**
   * Calculates the total set of all additional services requested.
   * @return The total cost of the services.
   */
  public double getServicesTotal()
  {
    int total = 0;
    for(int i = 0; i<services.size();i++)
    {
      total += services.get(i).getPrice();
    }
    return total;
  }

  /**
   * Returns the list of services for the reservation
   * @return the list of services
   */
  public ArrayList<Service> getServices()
  {
    return services;
  }

  /**
   * Sets the services for the reservation
   * @param services the list of services that needs to be set
   */

  public void setServices(ArrayList<Service> services)
  {
    this.services.clear();
    for (int i = 0; i < services.size(); i++)
    {
      this.services.add(services.get(i));
    }
  }

  /**
   * Calculates the total based on the room's price and the length of stay
   * @return The total for the room
   */
  public double getTotalForRoom()
  {
    return getLengthOfStay() * getRoom().getPrice();
  }

  /**
   * Calculates the price for the whole reservation
   * @return the total for the whole reservation
   */
  public double getTotalForStay()
  {
    return getTotalForRoom() + getServicesTotal();
  }

  /**
   * Returns the reservation as a string without the services
   * @return the reservation as a String
   */
  public String toString()
  {
    return client.getFirstName() + " " + client.getLastName() + "( Reservation: " + reservationId + " For room: " + room.getRoomNumber() + " )\nFrom: " + startDate.getYear() + "/" + startDate.getMonthValue() + "/" + startDate.getDayOfMonth() + " Until: " + endDate.getYear() + "/" + endDate.getMonthValue() + "/" + endDate.getDayOfMonth();
  }
}
