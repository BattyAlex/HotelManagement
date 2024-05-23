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
  /**
   * The model representing the hotel
   */
  private final HotelModel model;
  /**
   * The first name of the guest.
   */
  private StringProperty firstName;
  /**
   * The last name of the guest.
   */
  private StringProperty lastName;
  /**
   * The card information of the guest.
   */
  private StringProperty cardInfo;
  /**
   * The amenities selected for the reservation.
   */
  private StringProperty amenities;
  /**
   * The error message property.
   */
  private StringProperty error;
  /**
   * Indicates whether the breakfast is included or not in the reservation.
   */
  private BooleanProperty breakfast;
  /**
   * Indicates if lunch is included in the reservation.
   */
  private BooleanProperty lunch;
  /**
   * Indicates if dinner is included in the reservation.
   */
  private BooleanProperty dinner;
  /**
   * Indicated if airport transportation is included in the reservation.
   */
  private BooleanProperty airportTrans;
  /**
   * Indicates if wellness services are included in the reservation.
   */
  private BooleanProperty wellness;
  /**
   * Indicates if room service is included in the reservation
   */
  private BooleanProperty roomService;
  /**
   * The number of guests for the reservation
   */
  private StringProperty noOfGuests;
  /**
   * The reservation ID
   */
  private StringProperty reservationId;
  /**
   * Supports property change events.
   */

  private final PropertyChangeSupport support;
  private BooleanProperty canClick;
  private BooleanProperty delete;
  private BooleanProperty checkOut;

  /**
   * Constructor that initializes the ReservationViewModel with a given model.
   *
   * @param model The hotel model
   */
  

  public ReservationViewModel(HotelModel model)
  {
    this.model = model;
    model.addPropertyChangeListener(this);
    firstName = new SimpleStringProperty();
    lastName = new SimpleStringProperty();
    cardInfo = new SimpleStringProperty();
    amenities = new SimpleStringProperty();
    error = new SimpleStringProperty("");
    support = new PropertyChangeSupport(this);
    breakfast = new SimpleBooleanProperty();
    lunch = new SimpleBooleanProperty();
    dinner = new SimpleBooleanProperty();
    airportTrans = new SimpleBooleanProperty();
    wellness = new SimpleBooleanProperty();
    roomService = new SimpleBooleanProperty();
    noOfGuests = new SimpleStringProperty();
    reservationId = new SimpleStringProperty();
    canClick = new SimpleBooleanProperty(true);
    delete = new SimpleBooleanProperty(false);
    checkOut = new SimpleBooleanProperty(false);
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
   * Handles property change events.
   *
   * @param evt A PropertyChangeEvent object describing the event source
   *          and the property that has changed.
   */

  @Override public void propertyChange(PropertyChangeEvent evt)
  {
    switch (evt.getPropertyName())
    {
      case "Display Room Selected":
        Room temp = (Room) evt.getOldValue();
        setAmenities(temp.toString());
        support.firePropertyChange("Set Current Room", null, temp.getRoomNumber());
        delete.set(true);
        checkOut.set(true);
        break;
      case "Display Dates for Selected Room":
        LocalDate end = (LocalDate) evt.getOldValue();
        LocalDate start = (LocalDate) evt.getNewValue();
        support.firePropertyChange("Display Dates", end, start);
        canClick.set(true);
        break;
      case "Getting All Available Rooms":
        support.firePropertyChange("Display Available Rooms", null, evt.getNewValue());
        break;
      case "Display Reservation Selected":
        Reservation selected = (Reservation) evt.getOldValue();
        support.firePropertyChange("Set Reservation", selected, null);
        displayReservation(selected);
        break;
      case "Update Reservations":
        if(evt.getNewValue() != null)
        {
          support.firePropertyChange("Display Reservation Made Popup", null, evt.getNewValue());
        }
        break;
    }
  }

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
      model.getAvailableRooms(startDate, endDate);
      canClick.set(true);
    }
  }

  public void bindError(StringProperty property)
  {
    property.bind(error);
  }

  public void bindAmenities(StringProperty property)
  {
    property.bind(amenities);
  }
  public void bindFirstName(StringProperty property)
  {
    property.bindBidirectional(firstName);
  }
  public void bindLastName(StringProperty property)
  {
    property.bindBidirectional(lastName);
  }
  public void bindCard(StringProperty property)
  {
    property.bindBidirectional(cardInfo);
  }
  public void bindBreakfast(BooleanProperty property)
  {
    property.bindBidirectional(breakfast);
  }
  public void bindLunch(BooleanProperty property)
  {
    property.bindBidirectional(lunch);
  }
  public void bindDinner(BooleanProperty property)
  {
    property.bindBidirectional(dinner);
  }
  public void bindAirportTrans(BooleanProperty property)
  {
    property.bindBidirectional(airportTrans);
  }
  public void bindWellness(BooleanProperty property)
  {
    property.bindBidirectional(wellness);
  }
  public void bindRoomService(BooleanProperty property)
  {
    property.bindBidirectional(roomService);
  }
  public void bindNoOfGuests(StringProperty property)
  {
    property.bindBidirectional(noOfGuests);
  }

  public void bindReservationId(StringProperty property)
  {
    property.bind(reservationId);
  }

  public void bindCanClick(BooleanProperty property)
  {
    property.bind(canClick);
  }

  public void bindDelete(BooleanProperty property)
  {
    property.bind(delete);
  }

  public void bindCheckOut(BooleanProperty property)
  {
    property.bind(checkOut);
  }
  public void addPropertyChangeListener(PropertyChangeListener listener)
  {
    support.addPropertyChangeListener(listener);
  }

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

  public void datesChanged()
  {
    canClick.set(false);
    delete.set(true);
    checkOut.set(true);
  }
}
