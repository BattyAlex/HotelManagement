package ViewModel;

import Model.HotelModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.awt.*;

public class CleaningViewModel
{
  private final HotelModel model;
  private StringProperty roomInfo;

  public CleaningViewModel(HotelModel model)
  {
    this.model = model;
    roomInfo = new SimpleStringProperty();
  }
}
