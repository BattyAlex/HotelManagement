package ViewModel;

import Model.HotelModel;
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
  private LocalDate startDate;
  private LocalDate endDate;
  

  public ReservationViewModel(HotelModel model)
  {
    this.model = model;
    model.addPropertyChangeListener(this);
  }

  @Override public void propertyChange(PropertyChangeEvent evt)
  {

  }
}
