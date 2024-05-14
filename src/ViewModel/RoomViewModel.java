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

public class RoomViewModel implements PropertyChangeListener
{
  private final HotelModel model;
  private StringProperty toggle;
  private PropertyChangeSupport support;


  public RoomViewModel(HotelModel model)
  {
    this.model = model;
    model.addPropertyChangeListener(this);
    toggle = new SimpleStringProperty("To reservations");
    support = new PropertyChangeSupport(this);
  }
  public void bindToggle(StringProperty property)
  {
    property.bind(toggle);
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
      support.firePropertyChange("Update Room List", null, evt.getNewValue());
    }
  }
  public void loadAllRooms()
  {
    model.loadAllRooms();
  }
}
