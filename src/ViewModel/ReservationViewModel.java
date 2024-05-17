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

public class ReservationViewModel implements PropertyChangeListener
{
  private final HotelModel model;
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

  private final PropertyChangeSupport support;
  

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

  }
  private void setAllServicesToFalse()
  {
    breakfast.set(false);
    lunch.set(false);
    dinner.set(false);
    airportTrans.set(false);
    wellness.set(false);
    roomService.set(false);
  }
  public void setError(String errorMessage)
  {
    error.set(errorMessage);
  }
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

  @Override public void propertyChange(PropertyChangeEvent evt)
  {
    if (evt.getPropertyName().equals("Display Room Selected"))
    {
      setAllServicesToFalse();
      Room temp = (Room) evt.getOldValue();
      setAmenities(temp.toString());
      support.firePropertyChange("Set Current Room", null, temp.getRoomNumber());
    }
    else if(evt.getPropertyName().equals("Display Dates for Selected Room"))
    {
      LocalDate end = (LocalDate) evt.getOldValue();
      LocalDate start = (LocalDate) evt.getNewValue();
      support.firePropertyChange("Display Dates", end, start);
    }
    else if (evt.getPropertyName().equals("Getting All Available Rooms"))
    {
      support.firePropertyChange("Display Available Rooms", null, evt.getNewValue());
    }
    else if (evt.getPropertyName().equals("Display Reservation Selected"))
    {
      Reservation selected = (Reservation) evt.getOldValue();
      support.firePropertyChange("Set Reservation", selected, null);
      firstName.set(selected.getClient().getFirstName());
      lastName.set(selected.getClient().getLastName());
      cardInfo.set(selected.getClient().getPaymentInfo());
      amenities.set(selected.getRoom().toString());
      ArrayList<Service> services = selected.getServices();
      setAllServicesToFalse();
      for (int i = 0; i < services.size(); i++)
      {
        setService(services.get(i).getName());
      }
    }
  }
  public void setAmenities(String value)
  {
    amenities.set(value);
  }
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
    else
    {
      model.getAvailableRooms(startDate, endDate);
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
  public void addPropertyChangeListener(PropertyChangeListener listener)
  {
    support.addPropertyChangeListener(listener);
  }

  public void removePropertyChangeListener(PropertyChangeListener listener)
  {
    support.removePropertyChangeListener(listener);
  }

  public void roomSelected(Room room, LocalDate startDate, LocalDate endDate)
  {
    error.set("");
    model.roomSelected(room, startDate, endDate);
  }

  public void roomSelected(int roomNumber, LocalDate startDate, LocalDate endDate)
  {
    error.set("");
      model.roomSelected(roomNumber, startDate, endDate);
  }
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
      reservation.setNumberOfGuests(numOfGuests);

      model.makeOrUpdateReservation(reservation);
    }
  }
}
