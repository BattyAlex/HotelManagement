package ViewModel;

import Model.HotelModel;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ReservationViewModel implements PropertyChangeListener
{
  private final HotelModel model;

  public ReservationViewModel(HotelModel model)
  {
    this.model = model;
    model.addPropertyChangeListener(this);
  }

  @Override public void propertyChange(PropertyChangeEvent evt)
  {

  }
}
