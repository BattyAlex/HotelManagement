package ViewModel;

import Model.HotelModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDate;

public class ReservationViewModel implements PropertyChangeListener
{
  private final HotelModel model;
  private StringProperty firstName;
  private StringProperty lastName;
  private StringProperty cardInfo;
  private StringProperty amenities;
  private StringProperty error;
  

  public ReservationViewModel(HotelModel model)
  {
    this.model = model;
    model.addPropertyChangeListener(this);
    firstName = new SimpleStringProperty();
    lastName = new SimpleStringProperty();
    cardInfo = new SimpleStringProperty();
    amenities = new SimpleStringProperty();
    error = new SimpleStringProperty();
  }

  @Override public void propertyChange(PropertyChangeEvent evt)
  {

  }

}
