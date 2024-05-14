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

public class RoomViewModel implements PropertyChangeListener
{
  private final HotelModel model;
  private StringProperty toggle;
  private PropertyChangeSupport support;
  private StringProperty error;


  public RoomViewModel(HotelModel model)
  {
    this.model = model;
    model.addPropertyChangeListener(this);
    toggle = new SimpleStringProperty("To reservations");
    support = new PropertyChangeSupport(this);
    error = new SimpleStringProperty();
  }
  public void bindToggle(StringProperty property)
  {
    property.bind(toggle);
  }
  public void bindError(StringProperty property)
  {
    property.bind(error);
  }
  public void onToggle()
  {
    if(toggle.get().equals("To reservations"))
      toggle.set("To rooms");
    else
      toggle.set("To reservations");
  }

  public void addPropertyChangeListener(
      PropertyChangeListener listener)
  {
    support.addPropertyChangeListener(listener);
  }

  public void removePropertyChangeListener(
      PropertyChangeListener listener)
  {
    support.removePropertyChangeListener(listener);
  }

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
  }
  public void loadAllRooms()
  {
    model.loadAllRooms();
  }
  public void loadAvailableRooms(LocalDate startDate, LocalDate endDate)
  {
    error.set("");
    if(startDate == null || endDate == null)
    {
      error.set("Date from or until is empty, please choose a date.");
    }
    else if (endDate.isBefore(startDate))
    {
      error.set("The start date is earlier than the end date.");
    }
    else
    {
      model.loadAvailableRooms(startDate,endDate);
    }
  }
}
