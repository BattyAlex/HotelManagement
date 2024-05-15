package ViewModel;

import Model.HotelModel;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
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
    error = new SimpleStringProperty();
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
   * Toggles the value of the toggle property between "to reservations" and "to rooms"
   */
  public void onToggle()
  {
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
   * Called when a property change occurs. This method fires appropriate property changes based on the event
   * @param evt A PropertyChangeEvent object describing the event source
   *          and the property that has changed.
   */
  @Override public void propertyChange(PropertyChangeEvent evt)
  {
    if(evt.getPropertyName().equals("All Rooms"))
    {
      support.firePropertyChange("Load Room List", null, evt.getNewValue());
    }
    else if(evt.getPropertyName().equals("Available Rooms"))
    {
      support.firePropertyChange("Update Room List", null, evt.getNewValue());
    }
    else if (evt.getPropertyName().equals("All Reservations"))
    {
      support.firePropertyChange("Load Reservation List", null, evt.getNewValue());
    }
    else if (evt.getPropertyName().equals("Reservations for Time Period"))
    {
      support.firePropertyChange("Load Reservations for Period", null, evt.getNewValue());
    }
  }

  /**
   * Loads all rooms by calling the corresponding method on the model
   */
  public void loadAllRooms()
  {
    model.loadAllRooms();
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
    else
    {
      model.loadAvailableRooms(startDate,endDate);
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
   * Loads reservations within the specified timeframe
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
    }
  }
}
