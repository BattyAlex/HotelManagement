package ViewModel;

import Model.*;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * The ReservationViewModel class is responsible for managing and updating reservation details.
 * It implements the PropertyChangeListener to handle property change events.
 */

public class ReservationViewModel implements PropertyChangeListener
{
  private StringProperty firstName;
  private StringProperty lastName;
  private StringProperty cardInfo;
  private StringProperty amenities;
  private StringProperty error;
  private BooleanProperty breakfast;
  private BooleanProperty lunch;
  private BooleanProperty dinner;
  private BooleanProperty airportTrans;
  private BooleanProperty wellness;
  private BooleanProperty roomService;
  private StringProperty noOfGuests;
  private StringProperty reservationId;
  private BooleanProperty canClick;
  private BooleanProperty delete;
  private BooleanProperty checkOut;
  private final PropertyChangeSupport support;

  private final HotelModel model;

  /**
   * Constructor that initializes the ReservationViewModel with a given model.
   *
   * @param model The hotel model
   */
  

  public ReservationViewModel(HotelModel model)
  {
    this.model = model;
    model.addPropertyChangeListener(this);
    firstName = new SimpleStringProperty("");
    lastName = new SimpleStringProperty("");
    cardInfo = new SimpleStringProperty("");
    amenities = new SimpleStringProperty("");
    error = new SimpleStringProperty("");
    support = new PropertyChangeSupport(this);
    breakfast = new SimpleBooleanProperty(false);
    lunch = new SimpleBooleanProperty(false);
    dinner = new SimpleBooleanProperty(false);
    airportTrans = new SimpleBooleanProperty(false);
    wellness = new SimpleBooleanProperty(false);
    roomService = new SimpleBooleanProperty(false);
    noOfGuests = new SimpleStringProperty("");
    reservationId = new SimpleStringProperty("");
    canClick = new SimpleBooleanProperty(true);
    delete = new SimpleBooleanProperty(false);
    checkOut = new SimpleBooleanProperty(false);
  }

  /**
   * Binds the given property to the error SimpleStringProperty
   * @param property The property that gets bound
   */
  public void bindError(StringProperty property)
  {
    property.bind(error);
  }

  /**
   * Binds the given property to the amenities SimpleStringProperty
   * @param property The property that gets bound
   */
  public void bindAmenities(StringProperty property)
  {
    property.bind(amenities);
  }

  /**
   * Binds the given property to the firstName SimpleStringProperty bidirectionally
   * @param property The property that gets bound
   */
  public void bindFirstName(StringProperty property)
  {
    property.bindBidirectional(firstName);
  }

  /**
   * Binds the given property to the lastName SimpleStringProperty bidirectionally
   * @param property The property that gets bound
   */
  public void bindLastName(StringProperty property)
  {
    property.bindBidirectional(lastName);
  }

  /**
   * Binds the given property to the cardInfo SimpleStringProperty bidirectionally
   * @param property The property that gets bound
   */
  public void bindCard(StringProperty property)
  {
    property.bindBidirectional(cardInfo);
  }

  /**
   * Binds the given property to the amenities SimpleBooleanProperty bidirectionally
   * @param property The property that gets bound
   */
  public void bindBreakfast(BooleanProperty property)
  {
    property.bindBidirectional(breakfast);
  }

  /**
   * Binds the given property to the lunch SimpleBooleanProperty bidirectionally
   * @param property The property that gets bound
   */
  public void bindLunch(BooleanProperty property)
  {
    property.bindBidirectional(lunch);
  }

  /**
   * Binds the given property to the dinner SimpleBooleanProperty
   * @param property The property that gets bound
   */
  public void bindDinner(BooleanProperty property)
  {
    property.bindBidirectional(dinner);
  }

  /**
   * Binds the given property to the airportTrans SimpleBooleanProperty
   * @param property The property that gets bound
   */
  public void bindAirportTrans(BooleanProperty property)
  {
    property.bindBidirectional(airportTrans);
  }

  /**
   * Binds the given property to the wellness SimpleBooleanProperty
   * @param property The property that gets bound
   */
  public void bindWellness(BooleanProperty property)
  {
    property.bindBidirectional(wellness);
  }

  /**
   * Binds the given property to the roomService SimpleBooleanProperty
   * @param property The property that gets bound
   */
  public void bindRoomService(BooleanProperty property)
  {
    property.bindBidirectional(roomService);
  }

  /**
   * Binds the given property to the noOfGuests SimpleStringProperty
   * @param property The property that gets bound
   */
  public void bindNoOfGuests(StringProperty property)
  {
    property.bindBidirectional(noOfGuests);
  }

  /**
   * Binds the given property to the reservationId SimpleStringProperty
   * @param property The property that gets bound
   */
  public void bindReservationId(StringProperty property)
  {
    property.bind(reservationId);
  }

  /**
   * Binds the given property to the canClick SimpleBooleanProperty
   * @param property The property that gets bound
   */
  public void bindCanClick(BooleanProperty property)
  {
    property.bind(canClick);
  }

  /**
   * Binds the given property to the delete SimpleBooleanProperty
   * @param property The property that gets bound
   */
  public void bindDelete(BooleanProperty property)
  {
    property.bind(delete);
  }

  /**
   * Binds the given property to the checkOut SimpleBooleanProperty
   * @param property The property that gets bound
   */
  public void bindCheckOut(BooleanProperty property)
  {
    property.bind(checkOut);
  }

  /**
   * Sets all service properties to false.
   */
  private void setAllServicesToFalse()
  {
    breakfast.set(false);
    lunch.set(false);
    dinner.set(false);
    airportTrans.set(false);
    wellness.set(false);
    roomService.set(false);
  }

  /**
   * Sets the error message
   *
   * @param errorMessage The error message to set
   */
  public void setError(String errorMessage)
  {
    error.set(errorMessage);
  }

  /**
   * Sets the service based on the given service ID
   *
   * @param id The service ID
   */
  private void setService(String id)
  {
    switch (id)
    {
      case Service.BREAKFAST -> breakfast.set(true);
      case Service.LUNCH -> lunch.set(true);
      case Service.DINNER -> dinner.set(true);
      case Service.AIRPORT_TRANSPORT -> airportTrans.set(true);
      case Service.WELLNESS -> wellness.set(true);
      case Service.ROOM_SERVICE -> roomService.set(true);
    }
  }

  /**
   * Sets the fields in accordance with the values of the given Reservation
   *
   * @param selected The Reservation that was selected and needs to be displayed
   */
  private void displayReservation(Reservation selected)
  {
    canClick.set(true);
    delete.set(false);
    checkOut.set(false);
    firstName.set(selected.getClient().getFirstName());
    lastName.set(selected.getClient().getLastName());
    cardInfo.set(selected.getClient().getPaymentInfo());
    amenities.set(selected.getRoom().toString());
    String numberSet = "";
    numberSet += selected.getNumberOfGuests();
    noOfGuests.set(numberSet);
    String reservationSet = "";
    reservationSet += selected.getReservationId();
    reservationId.set(reservationSet);
    ArrayList<Service> services = selected.getServices();
    setAllServicesToFalse();
    for (int i = 0; i < services.size(); i++)
    {
      setService(services.get(i).getName());
    }
  }

  /**
   * Sets the amenities property
   *
   * @param value The amenities to set
   */
  public void setAmenities(String value)
  {
    amenities.set(value);
  }

  /**
   * Loads available rooms based on the provided dates.
   *
   * @param startDate The start date
   * @param endDate The end date
   */
  public void loadAvailableRooms(LocalDate startDate, LocalDate endDate)
  {
    error.set("");
    if(startDate == null || endDate == null)
    {
      error.set("Date is empty, please choose a date.");
      canClick.set(false);
    }
    else if (endDate.isBefore(startDate))
    {
      error.set("The end date is earlier than the start date.");
      canClick.set(false);
    }
    else if (endDate.getYear() == startDate.getYear() && endDate.getMonthValue() == startDate.getMonthValue() && endDate.getDayOfMonth() == startDate.getDayOfMonth())
    {
      error.set("The same dates have been selected.");
      canClick.set(false);
    }
    else
    {
      model.getAvailableRooms(startDate, endDate);
      canClick.set(true);
    }
  }

  /**
   * Adds a PropertyChangeListener to listen for property changes
   * @param listener The listener to add
   */
  public void addPropertyChangeListener(PropertyChangeListener listener)
  {
    support.addPropertyChangeListener(listener);
  }

  /**
   * Removes a PropertyChangeListener
   * @param listener The listener to remove
   */
  public void removePropertyChangeListener(PropertyChangeListener listener)
  {
    support.removePropertyChangeListener(listener);
  }

  /**
   * Selects a room
   * @param room The room to select
   * @param startDate The start date
   * @param endDate The end date
   */

  public void roomSelected(Room room, LocalDate startDate, LocalDate endDate)
  {
    error.set("");
    model.roomSelected(room, startDate, endDate);
  }

  /**
   * Selects a room by room number
   *
   * @param roomNumber The room number
   * @param startDate  The start date
   * @param endDate    The end date
   */

  public void roomSelected(int roomNumber, LocalDate startDate, LocalDate endDate)
  {
    error.set("");
      model.roomSelected(roomNumber, startDate, endDate);
  }

  /**
   * Confirms the reservation
   *
   * @param room The selected room
   * @param startDate The start date
   * @param endDate The end date
   */
  public void onConfirm(Room room, LocalDate startDate, LocalDate endDate)
  {
    if(firstName.get() == null || lastName.get() == null || cardInfo.get() == null || noOfGuests.get() == null || firstName.get().isEmpty() || lastName.get().isEmpty() || cardInfo.get().length() != 16 || noOfGuests.get().isEmpty())
    {
      support.firePropertyChange("invalid input", null, null);
    }
    else
    {
      int numOfGuests = 0;
      try
      {
        numOfGuests = Integer.parseInt(noOfGuests.get());
      }
      catch (NumberFormatException e)
      {
        support.firePropertyChange("invalid input", null, null);
      }
      Guest client = new Guest(firstName.get(), lastName.get(), cardInfo.get());
      Staff staff = new Staff("", "");
      Reservation reservation = new Reservation(startDate, endDate, client, room, staff);
      if(breakfast.get())
      {
        reservation.addService(Service.BREAKFAST, 0);
      }
      if(lunch.get())
      {
        reservation.addService(Service.LUNCH, 0);
      }
      if(dinner.get())
      {
        reservation.addService(Service.DINNER, 0);
      }
      if(airportTrans.get())
      {
        reservation.addService(Service.AIRPORT_TRANSPORT, 0);
      }
      if(wellness.get())
      {
        reservation.addService(Service.AIRPORT_TRANSPORT, 0);
      }
      if(roomService.get())
      {
        reservation.addService(Service.ROOM_SERVICE, 0);
      }
      if(!(reservationId.get() == null) && !reservationId.get().isEmpty())
      {
        try
        {
          reservation.setReservationId(Integer.parseInt(reservationId.get()));
        }
        catch (NumberFormatException e)
        {
          support.firePropertyChange("invalid input", null, null);
        }
      }
      reservation.setNumberOfGuests(numOfGuests);
      model.makeOrUpdateReservation(reservation);
      resetValues();
    }
  }

  /**
   * Cancels the current reservation by resetting all fields to their default values
   */

  public void onCancel()
  {
    resetValues();
  }

  /**
   * Sets all values to false, or empty Strings
   */
  private void resetValues()
  {
    setAllServicesToFalse();
    firstName.set("");
    lastName.set("");
    cardInfo.set("");
    noOfGuests.set("");
    reservationId.set("");
  }

  /**
   * Deletes the given reservation
   *
   * @param reservation The reservation to delete
   */

  public void onDelete(Reservation reservation)
  {
    model.onDelete(reservation);
  }

  /**
   * Checks out the guest from the given reservation. This sets the room state to "NEEDS_CLEANING",
   * deletes the reservation and performs any additional checkout operations.
   *
   * @param reservation The reservation to check out
   */

  public void checkOut(Reservation reservation)
  {
    reservation.getRoom().setState(Room.NEEDS_CLEANING);
    onDelete(reservation);
    model.checkOut(reservation.getRoom());
  }

  /**
   * Handles what happens when the dates are changed
   */
  public void datesChanged()
  {
    canClick.set(false);
    delete.set(true);
    checkOut.set(true);
  }

  /**
   * Handles property change events.
   *
   * @param evt A PropertyChangeEvent object describing the event source
   *          and the property that has changed.
   */

  @Override public void propertyChange(PropertyChangeEvent evt)
  {
    switch (evt.getPropertyName())
    {
      case "Display Room Selected" ->
      {
        Room temp = (Room) evt.getOldValue();
        setAmenities(temp.toString());
        support.firePropertyChange("Set Current Room", null,
            temp.getRoomNumber());
        delete.set(true);
        checkOut.set(true);
      }
      case "Display Dates for Selected Room" ->
      {
        LocalDate end = (LocalDate) evt.getOldValue();
        LocalDate start = (LocalDate) evt.getNewValue();
        support.firePropertyChange("Display Dates", end, start);
        canClick.set(true);
      }
      case "Getting All Available Rooms" ->
          support.firePropertyChange("Display Available Rooms", null,
              evt.getNewValue());
      case "Display Reservation Selected" ->
      {
        Reservation selected = (Reservation) evt.getOldValue();
        support.firePropertyChange("Set Reservation", selected, null);
        displayReservation(selected);
      }
      case "Update Reservations" ->
      {
        if (evt.getNewValue() != null)
        {
          support.firePropertyChange("Display Reservation Made Popup", null,
              evt.getNewValue());
        }
      }
    }
  }
}
