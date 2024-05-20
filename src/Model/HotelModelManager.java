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
      e.printStackTrace();
    }
  }

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
   * Method is called when a PropertyChangeEvent is about to get fired.
   * @param evt the PropertyChangeEvent object containing the event details.
   */

  public void propertyChange(PropertyChangeEvent evt)
  {
    Platform.runLater(()-> {
      if (evt.getPropertyName().equals("Invalid username"))
      {
        support.firePropertyChange("Username invalid", evt.getOldValue(), null);
      }
      else if (evt.getPropertyName().equals("Login Approved"))
      {
        staff.setUsername((String) evt.getNewValue());
        support.firePropertyChange("Login Successful", null, null);
      }
      else if (evt.getPropertyName().equals("Login Rejected"))
      {
        support.firePropertyChange("Login failed", evt.getOldValue(), null);
      }
      else if (evt.getPropertyName().equals("Sending All Rooms"))
      {
        support.firePropertyChange("All Rooms", null, evt.getNewValue());
      }

      else if (evt.getPropertyName().equals("Sending All Reservations"))
      {
        support.firePropertyChange("All Reservations", null, evt.getNewValue());
      }
      else if (evt.getPropertyName().equals("Sending All Reservations For Period"))
      {
        support.firePropertyChange("Reservations for Time Period", null, evt.getNewValue());
      }
      else if (evt.getPropertyName().equals("Reservation Made"))
      {
        support.firePropertyChange("Update Reservations", null, evt.getNewValue());
      }
    });

  }
  public void loadAllRooms()
  {
    try
    {
      client.getAllRooms();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }

  }

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

  @Override public void roomSelected(Room room, LocalDate startDate, LocalDate endDate)
  {
    support.firePropertyChange("Display Room Selected", room, null);
    support.firePropertyChange("Display Dates for Selected Room", endDate, startDate);
  }

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

  @Override public void reservationSelected(Reservation selected)
  {
    support.firePropertyChange("Display Reservation Selected", selected, null);
  }
  @Override public void getRoomsForCleaning()
  {

  }

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
}
