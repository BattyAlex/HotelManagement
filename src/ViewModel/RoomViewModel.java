package ViewModel;

import Model.HotelModel;
import Model.Reservation;
import Model.Room;
import javafx.beans.property.*;
import javafx.collections.ObservableList;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.time.LocalDate;
import java.util.Date;

/**
 * RoomViewModel handles the binding of properties and listens for property changes
 */
public class RoomViewModel implements PropertyChangeListener
{
  private final HotelModel model;
  private StringProperty toggle;
  private PropertyChangeSupport support;
  private StringProperty error;
  private BooleanProperty canClick;
  private StringProperty cleaningToggle;

  /**
   * Constructor to initialize RoomViewModel with the given HotelModel
   * @param model the HotelModel to be used in this ViewModel
   */
  public RoomViewModel(HotelModel model)
  {
    this.model = model;
    model.addPropertyChangeListener(this);
    toggle = new SimpleStringProperty("To reservations");
    support = new PropertyChangeSupport(this);
    error = new SimpleStringProperty("");
    canClick = new SimpleBooleanProperty(false);
    cleaningToggle = new SimpleStringProperty("Needs cleaning");
  }

  /**
   * Binds the provided StringProperty to the toggle property
   * @param property the property to bind to the toggle property.
   */
  public void bindToggle(StringProperty property)
  {
    property.bind(toggle);
  }

  /**
   * Binds the provided StringProperty to the error property
   * @param property the property to bind to the error property
   */
  public void bindError(StringProperty property)
  {
    property.bind(error);
  }

  /**
   * Binds the provided SimpleBooleanProperty to canClick
   * @param property the property to bind to canClick
   */
  public void bindCanClick(SimpleBooleanProperty property)
  {
    property.bindBidirectional(canClick);
  }

  /**
   * Binds the provided StringProperty to cleaningToggle
   * @param property the property to bind to cleaningToggle
   */
  public void bindCleaningToggle(StringProperty property)
  {
    property.bind(cleaningToggle);
  }

  /**
   * Toggles the value of the toggle property between "to reservations" and "to rooms"
   */
  public void onToggle()
  {
    error.set("");
    if(toggle.get().equals("To reservations"))
    {
      toggle.set("To rooms");
      loadAllReservations();
    }
    else
    {
      toggle.set("To reservations");
      loadAllRooms ();
    }
    canClick.set(false);
  }

  /**
   * Adds a propertyChangeListener to the support object
   * @param listener the propertyChangeListener to add
   */
  public void addPropertyChangeListener(
      PropertyChangeListener listener)
  {
    support.addPropertyChangeListener(listener);
  }

  /**
   * Removes a propertyChangeListener from the support object
   * @param listener the propertyChangeListener to remove
   */
  public void removePropertyChangeListener(
      PropertyChangeListener listener)
  {
    support.removePropertyChangeListener(listener);
  }

  /**
   * Loads all rooms by calling the corresponding method on the model
   */
  public void loadAllRooms()
  {
    model.loadAllRooms();
  }

  /**
   * Sets canClick to false
   */
  public void datesChanged()
  {
    canClick.set(false);
  }


  /**
   * Loads available rooms based on the provided start and end dates
   * Sets an error message if the dates are invalid
   * @param startDate the start date for the room availability
   * @param endDate the end date for the room availability
   */
  public void loadAvailableRooms(LocalDate startDate, LocalDate endDate)
  {
    error.set("");
    if(startDate == null || endDate == null)
    {
      error.set("Date from or until is empty, please choose a date.");
    }
    else if (endDate.isBefore(startDate))
    {
      error.set("The end date is earlier than the start date.");
    }
    else if (endDate.getYear() == startDate.getYear() && endDate.getMonthValue() == startDate.getMonthValue() && endDate.getDayOfMonth() == startDate.getDayOfMonth())
    {
     error.set("The same dates have been selected.");
    }
    else
    {
      model.loadAvailableRooms(startDate,endDate);
      canClick.set(true);
    }
  }

  /**
   * This method gets the current value of the toggle property
   * @return the current value of the toggle property
   */
  public String getToggle()
  {
    return toggle.get();
  }

  /**
   * Loads all reservations by calling HotelModel's method
   */
  public void loadAllReservations()
  {
    model.loadAllReservations();
  }

  /**
   * Loads reservations within the specified timeframe
   * Validates the start and end dates and sets an error message if invalid
   * @param startDate the start date for the reservations search
   * @param endDate the end date for the reservation search
   */
  public void loadReservationsInTimeframe(LocalDate startDate, LocalDate endDate)
  {
    error.set("");
    if(startDate == null || endDate == null)
    {
      error.set("Date from or until is empty, please choose a date.");
    }
    else if (endDate.isBefore(startDate))
    {
      error.set("The end date is earlier than the start date.");
    }
    else
    {
      model.loadReservationsInTimeframe(startDate, endDate);
      canClick.set(true);
    }
  }

  /**
   * Sets the error to the given String message
   * @param message the message to be displayed
   */
  public void setError(String message)
  {
    error.set(message);
  }

  /**
   * Sends the selected room forward to the HotelModel to be displayed
   * Sets the error message given the startDate or the endDate are incorrect
   * @param room The room that was selected
   * @param startDate The start date to be displayed
   * @param endDate The end date to be displayed
   */
  public void roomSelected(Room room, LocalDate startDate, LocalDate endDate)
  {
    error.set("");
    if(startDate == null || endDate == null)
    {
      setError("Date from or until is empty, please choose a date.");
    }
    else if (endDate.isBefore(startDate))
    {
      setError("The end date is earlier than the start date.");
    }
    else
    {
      model.roomSelected(room, startDate, endDate);
    }
  }

  /**
   * Checks whether the given startDate and endDate are correct
   * @param startDate The start date to be checked
   * @param endDate The end date to be checked
   * @return Returns a boolean based on whether the given dates are not null, and the start date is before the end date
   */
  public boolean areDatesCorrect(LocalDate startDate, LocalDate endDate)
  {
    if(startDate == null || endDate == null)
    {
      return false;
    }
    else if (endDate.isBefore(startDate) || (endDate.getYear() == startDate.getYear() && endDate.getMonthValue() == startDate.getMonthValue() && endDate.getDayOfMonth() == startDate.getDayOfMonth()))
    {
      return false;
    }
    return true;
  }

  /**
   * Calls the HotelModel's methods based on the value of cleaningToggle
   */
  public void onCleaning()
  {
    if(cleaningToggle.get().equals("Needs cleaning"))
    {
      cleaningToggle.set("To rooms");
      model.getRoomsForCleaning();
    }
    else
    {
      cleaningToggle.set("Needs cleaning");
      loadAllRooms();
    }
  }

  /**
   * Forwards the selected reservation to the HotelModel
   * @param selected the selected reservation
   */
  public void reservationSelected(Reservation selected)
  {
    model.reservationSelected(selected);
  }

  /**
   * Returns the String value of the cleaningToggle
   * @return the value of the cleaningToggle
   */
  public String getCleaningToggle()
  {
    return cleaningToggle.get();
  }

  /**
   * Send the selected room for cleaning to the HotelModel
   * @param room The room selected for cleaning
   */
  public void selectRoomForCleaning(Room room)
  {
    model.selectRoomToClean(room);
  }

  /**
   * Called when a property change occurs. This method fires appropriate property changes based on the event
   * @param evt A PropertyChangeEvent object describing the event source
   *          and the property that has changed.
   */
  @Override public void propertyChange(PropertyChangeEvent evt)
  {
    switch (evt.getPropertyName())
    {
      case "All Rooms" ->
      {
        cleaningToggle.set("Needs cleaning");
        support.firePropertyChange("Load Room List", null, evt.getNewValue());
      }
      case "Available Rooms" ->
          support.firePropertyChange("Load Room List", null, evt.getNewValue());
      case "All Reservations" ->
          support.firePropertyChange("Load Reservation List", null,
              evt.getNewValue());
      case "Reservations for Time Period" ->
          support.firePropertyChange("Load All Reservations", null,
              evt.getNewValue());
      case "Update Reservations" ->
      {
        if (toggle.get().equals("To rooms"))
        {
          support.firePropertyChange("Load All Reservations", null,
              evt.getNewValue());
        }
      }
      case "Display Rooms For Cleaning" ->
      {
        if (cleaningToggle.get().equals("To rooms"))
        {
          support.firePropertyChange("Load Room List", null, evt.getNewValue());
        }
      }
    }
  }
}
