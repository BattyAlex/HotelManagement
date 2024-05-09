package ViewModel;

import Model.HotelModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class LoginViewModel
{
  private StringProperty username;
  private StringProperty password;
  private final HotelModel model;


  public LoginViewModel(HotelModel model)
  {
    username = new SimpleStringProperty();
    password = new SimpleStringProperty();
    this.model = model;
  }
}
