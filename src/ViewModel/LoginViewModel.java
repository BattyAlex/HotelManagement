package ViewModel;

import Model.HotelModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * The LoginViewModel class handles the login logic and state for the login view
 * It implements PropertyChangeListener to listen for property changes in the model.
 */
public class LoginViewModel implements PropertyChangeListener
{
  private StringProperty username;
  private StringProperty password;
  private StringProperty error;
  private final HotelModel model;

  private PropertyChangeSupport support;

  /**
   * Constructs a LoginViewModel with the specific HotelModel
   * @param model The hotel model used for login operations
   */
  public LoginViewModel(HotelModel model)
  {
    this.model = model;
    username = new SimpleStringProperty();
    password = new SimpleStringProperty();
    error = new SimpleStringProperty("");
    model.addPropertyChangeListener(this);
    support = new PropertyChangeSupport(this);
  }

  /**
   * Attempts to log in using the provided username and password
   * Sets an error message if the username or password is null
   */
  public void tryLogin()
  {
    error.set("");
    if(username.get() == null|| password.get() == null || username.get().isEmpty() || password.get().isEmpty())
    {
      error.set("Please enter your username and password.");
    }
    else
    {
      model.tryLogin(username.get(), password.get());
    }
  }

  /**
   * Binds the username property bidirectionally to the provided property
   * @param property The property to bind to
   */
  public void bindUsername(StringProperty property)
  {
    property.bindBidirectional(username);
  }

  /**
   * Binds the password property bidirectionally to the provided property
   * @param property The property to bind to
   */
  public void bindPassword(StringProperty property)
  {
    property.bindBidirectional(password);
  }

  public void bindError(StringProperty property)
  {
    property.bind(error);
  }

  /**
   * Adds a PropertyChangeListener to listen for property changes
   * @param listener The listener to add
   */
  public void addPropertyChangeListener(PropertyChangeListener listener)
  {
    support.addPropertyChangeListener(listener);
  }

  /**
   * Removes a PropertyChangeListener
   * @param listener The listener to remove
   */
  public void removePropertyChangeListener(PropertyChangeListener listener)
  {
    support.removePropertyChangeListener(listener);
  }

  /**
   * Handles property changes from the model and updates the view model accordingly
   * @param evt A PropertyChangeEvent object describing the event source
   *          and the property that has changed.
   */
  @Override public void propertyChange(PropertyChangeEvent evt)
  {
    switch (evt.getPropertyName())
    {
      case "Username invalid" ->
          error.set("The username '" + evt.getOldValue() + "' is invalid.");
      case "Login Successful" ->
          support.firePropertyChange("Login Successful", null, null);
      case "Login failed" -> error.set("Incorrect password");
      case "Database Connection Offline" ->
          support.firePropertyChange("Database Failed", null,
              evt.getNewValue());
    }
  }
}
