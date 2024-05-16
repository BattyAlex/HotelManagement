package ViewModel;

import Model.HotelModel;
import Model.Room;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.time.LocalDate;

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
  }

  @Override public void propertyChange(PropertyChangeEvent evt)
  {
    if (evt.getPropertyName().equals("Display Room Selected"))
    {
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
    property.bind(firstName);
  }
  public void bindLastName(StringProperty property)
  {
    property.bind(lastName);
  }
  public void bindCard(StringProperty property)
  {
    property.bind(cardInfo);
  }
  public void bindBreakfast(BooleanProperty property)
  {
    property.bind(breakfast);
  }
  public void bindLunch(BooleanProperty property)
  {
    property.bind(lunch);
  }
  public void bindDinner(BooleanProperty property)
  {
    property.bind(dinner);
  }
  public void bindAirportTrans(BooleanProperty property)
  {
    property.bind(airportTrans);
  }
  public void bindWellness(BooleanProperty property)
  {
    property.bind(wellness);
  }
  public void bindRoomService(BooleanProperty property)
  {
    property.bind(roomService);
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
}
