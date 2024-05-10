package ViewModel;

import Model.HotelModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class LoginViewModel implements PropertyChangeListener
{
  private StringProperty username;
  private StringProperty password;
  private StringProperty error;
  private final HotelModel model;


  public LoginViewModel(HotelModel model)
  {
    this.model = model;
    username = new SimpleStringProperty();
    password = new SimpleStringProperty();
    error = new SimpleStringProperty();
    model.addPropertyChangeListener(this);
  }

  public void bindUsername(StringProperty property)
  {
    property.bindBidirectional(username);
  }

  public void bindPassword(StringProperty property)
  {
    property.bindBidirectional(password);
  }

  public void bindError(StringProperty property)
  {
    property.bind(error);
  }

  @Override public void propertyChange(PropertyChangeEvent evt)
  {

  }
}
