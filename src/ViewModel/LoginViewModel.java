package ViewModel;

import Model.HotelModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class LoginViewModel implements PropertyChangeListener
{
  private StringProperty username;
  private StringProperty password;
  private StringProperty error;
  private final HotelModel model;

  private PropertyChangeSupport support;


  public LoginViewModel(HotelModel model)
  {
    this.model = model;
    username = new SimpleStringProperty();
    password = new SimpleStringProperty();
    error = new SimpleStringProperty();
    model.addPropertyChangeListener(this);
    support = new PropertyChangeSupport(this);
  }
  public void tryLogin()
  {
    error.set("");
    if(username.get() == null|| password.get() == null)
    {
      error.set("Please enter your username and password.");
    }
    else
    {
      model.tryLogin(username.get(), password.get());
    }
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

  public void addPropertyChangeListener(PropertyChangeListener listener)
  {
    support.addPropertyChangeListener(listener);
  }

  public void removePropertyChangeListener(PropertyChangeListener listener)
  {
    support.removePropertyChangeListener(listener);
  }

  @Override public void propertyChange(PropertyChangeEvent evt)
  {
    if(evt.getPropertyName().equals("Username invalid"))
    {
      error.set("The username '" + evt.getOldValue() + "' is invalid.");
    }
    else if (evt.getPropertyName().equals("Login Successful"))
    {
      support.firePropertyChange("Login Successful", null, null);
    }
    else if (evt.getPropertyName().equals("Login failed"))
    {
      error.set("Incorrect password");
    }
  }
}
