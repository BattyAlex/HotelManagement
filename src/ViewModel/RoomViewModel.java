package ViewModel;

import Model.HotelModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.beans.PropertyChangeSupport;

public class RoomViewModel
{
  private final HotelModel model;
  private StringProperty toggle;


  public RoomViewModel(HotelModel model)
  {
    this.model = model;
    toggle = new SimpleStringProperty("To reservations");

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
}
