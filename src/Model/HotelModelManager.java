package Model;

import Client.HotelClient;
import javafx.application.Platform;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class HotelModelManager implements HotelModel, PropertyChangeListener
{
  private Staff staff;
  private PropertyChangeSupport support;
  private HotelClient client;

  /**
   * Manages the model login for the hotel and handles operations such as user login and notifications for changes in property value
   */
  public HotelModelManager(HotelClient client)
  {
    staff = new Staff("", "");
    this.client = client;
    this.support = new PropertyChangeSupport(this);
    client.addPropertyChangeListener(this);
  }

  /**
   * Attempts to log in a user with the specified username and password.
   * Notifies listeners of the result of the login attempt.
   * @param username the username of the staff member trying to log in.
   * @param password the password of the staff member trying to log in.
   */

  @Override public void tryLogin(String username, String password)
  {
    try
    {
      client.tryLogin(username, password);
    }
    catch (IOException e)
    {
      System.out.println("Login request rejected");
      support.firePropertyChange("Login failed", username, null);
    }
  }

  /**
   * Exits the client session and performs any necessary clean up operations
   */
  @Override public void exitClient()
  {
    client.close();
  }

  /**
   * Adds a PropertyChangeListener to the list of listeners to be notified of property changes
   * @param listener the PropertyChangeListener to be added.
   */

  @Override public void addPropertyChangeListener(
      PropertyChangeListener listener)
  {
    support.addPropertyChangeListener(listener);
  }

  /**
   * Removes a PropertyChangeListener to the list of listeners.
   * @param listener the PropertyChangeListener to be removed.
   */

  @Override public void removePropertyChangeListener(
      PropertyChangeListener listener)
  {
    support.removePropertyChangeListener(listener);
  }

  /**
   * Gets all the rooms and sends it forward
   */
  @Override public void loadAllRooms()
  {
    try
    {
      support.firePropertyChange("All Rooms", null, client.getAllRooms());
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }

  /**
   * Gets all rooms between a specific timeframe and sends it forward
   * @param startDate The start date from which the room should be available
   * @param endDate The end date till which the room should be available
   */
  @Override public void loadAvailableRooms(LocalDate startDate, LocalDate endDate)
  {
    try
    {
      support.firePropertyChange("Available Rooms", null, client.getRoomsAvailable(startDate,endDate));
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }

  /**
   * Gets all reservations and sends it forward
   */
  @Override public void loadAllReservations()
  {
    try
    {
      client.getAllReservations();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }

  /**
   * Gets all reservations between a specific time frame
   * @param startDate The start date between which the reservation should be made for
   * @param endDate The end date between which the reservation should be made for
   */
  @Override public void loadReservationsInTimeframe(LocalDate startDate,
      LocalDate endDate)
  {
    try
    {
      client.getReservationsInTimeframe(startDate, endDate);
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }

  /**
   * Sends forward the selected room with all information which is available for a certain period
   * @param room The room which was selected
   * @param startDate The start date from which the room should be available
   * @param endDate The end date till which the room should be available
   */
  @Override public void roomSelected(Room room, LocalDate startDate, LocalDate endDate)
  {
    support.firePropertyChange("Display Room Selected", room, null);
    support.firePropertyChange("Display Dates for Selected Room", endDate, startDate);
  }

  /**
   * Sends forward the selected room based on the room number which is available for a certain period
   * @param roomNumber The room number of the room which was selected
   * @param startDate The start date from which the room should be available
   * @param endDate The end date till which the room should be available
   */
  @Override public void roomSelected(int roomNumber, LocalDate startDate, LocalDate endDate)
  {
    try
    {
      support.firePropertyChange("Display Room Selected", client.getRoomByRoomNumber(roomNumber), null);
      support.firePropertyChange("Display Dates for Selected Room", endDate, startDate);
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }

  /**
   * Gets available rooms for a certain time period and sends it forward
   * @param startDate The start date from which the rooms are available
   * @param endDate The end date till which the rooms should be available
   */
  @Override public void getAvailableRooms(LocalDate startDate,
      LocalDate endDate)
  {
    try
    {
      support.firePropertyChange("Getting All Available Rooms", null, client.getRoomsAvailable(startDate, endDate));
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }

  /**
   * Makes or updates a reservation based on whether it already exists or not
   * @param reservation The reservation that needs to be made or updated
   */
  @Override public void makeOrUpdateReservation(Reservation reservation)
  {
    try
    {
      reservation.getStaff().setUsername(this.staff.getUsername());
      client.makeOrUpdateReservation(reservation);
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }

  /**
   * Sends forward the selected reservation
   * @param selected The reservation selected
   */
  @Override public void reservationSelected(Reservation selected)
  {
    support.firePropertyChange("Display Reservation Selected", selected, null);
  }

  /**
   * Gets all the rooms needing cleaning and sends them forward
   */
  @Override public void getRoomsForCleaning()
  {
    try
    {
      ArrayList<Room> temp = client.getAllRoomsForCleaning();
      support.firePropertyChange("Display Rooms For Cleaning", null, temp);
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }

  /**
   * Deletes the reservation
   * @param reservation The reservation that needs to be deleted
   */
  @Override public void onDelete(Reservation reservation)
  {
    try
    {
      Reservation deleted = client.onDelete(reservation);
      support.firePropertyChange("Update Reservations", null, deleted);
    }
    catch (IOException e)
    {
      System.out.println("Reservation could not be deleted.");
      e.printStackTrace();
    }
  }

  /**
   * Changes the state for the room from which the guest is checking out
   * @param room The room from which the guest checks out
   */
  @Override public void checkOut(Room room)
  {
    try
    {
      client.updateStateOfRoom(room);
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }

  /**
   * Gets the room that needs cleaning and sends it forward
   * @param room The room that was selected
   */
  @Override public void selectRoomToClean(Room room)
  {
    support.firePropertyChange("Room Selected For Cleaning", room, null);
  }

  /**
   * Method is called when a PropertyChangeEvent is about to get fired.
   * @param evt the PropertyChangeEvent object containing the event details.
   */
  public void propertyChange(PropertyChangeEvent evt)
  {
    Platform.runLater(()-> {
      switch (evt.getPropertyName())
      {
        case "Invalid username" ->
            support.firePropertyChange("Username invalid", evt.getOldValue(),
                null);
        case "Login Approved" ->
        {
          staff.setUsername((String) evt.getNewValue());
          support.firePropertyChange("Login Successful", null, null);
        }
        case "Login Rejected" ->
            support.firePropertyChange("Login failed", evt.getOldValue(), null);
        case "Sending All Reservations" ->
            support.firePropertyChange("All Reservations", null, evt.getNewValue());
        case "Sending All Reservations For Period" ->
            support.firePropertyChange("Reservations for Time Period", null, evt.getNewValue());
        case "Reservation Made" ->
            support.firePropertyChange("Update Reservations", null, evt.getNewValue());
        case "Database Connection Problems" ->
            support.firePropertyChange("Database Connection Offline", null, evt.getNewValue());
      }
    });
  }
}
