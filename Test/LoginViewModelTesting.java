import Model.HotelModel;
import Model.HotelModelManager;
import ViewModel.LoginViewModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class LoginViewModelTesting
{
  private HotelModel model;
  private LoginViewModel loginViewModel;
  private StringProperty username;
  private StringProperty password;
  private StringProperty error;
  private PropertyChangeListener listener;
  @BeforeEach public void setUp()
  {
    listener = Mockito.mock(PropertyChangeListener.class);
    model = Mockito.mock(HotelModelManager.class);
    loginViewModel = new LoginViewModel(model);
    loginViewModel.addPropertyChangeListener(listener);
    username = new SimpleStringProperty();
    password = new SimpleStringProperty();
    error = new SimpleStringProperty();
    loginViewModel.bindUsername(username);
    loginViewModel.bindPassword(password);
    loginViewModel.bindError(error);
  }

  @Test public void newly_created_LoginViewModel_is_not_null()
  {
    assertNotNull(loginViewModel);
  }

  @Test public void if_username_is_empty_error_is_set()
  {
    assertEquals("", error.get());
    password.set("hello");
    username.set("");
    loginViewModel.tryLogin();
    assertEquals("Please enter your username and password.", error.get());
  }

  @Test public void if_password_is_empty_error_is_set()
  {
    assertEquals("", error.get());
    username.set("hello");
    password.set("");
    loginViewModel.tryLogin();
    assertEquals("Please enter your username and password.", error.get());
  }

  @Test public void if_username_and_password_are_empty_error_is_set()
  {
    assertEquals("", error.get());
    username.set("");
    password.set("");
    loginViewModel.tryLogin();
    assertEquals("Please enter your username and password.", error.get());
  }

  @Test public void if_username_is_null_error_is_set()
  {
    assertNull(username.get());
    password.set("hi");
    loginViewModel.tryLogin();
    assertEquals("Please enter your username and password.", error.get());
  }

  @Test public void if_password_is_null_error_is_set()
  {
    assertNull(password.get());
    username.set("hi");
    loginViewModel.tryLogin();
    assertEquals("Please enter your username and password.", error.get());
  }

  @Test public void if_password_and_username_are_null_error_is_set()
  {
    assertNull(password.get());
    assertNull(username.get());
    loginViewModel.tryLogin();
    assertEquals("Please enter your username and password.", error.get());
  }

  @Test public void if_password_and_username_are_set_error_stays_empty()
  {
    username.set("username");
    password.set("password");
    loginViewModel.tryLogin();
    assertEquals("", error.get());
  }

  @Test public void property_change_LoginSuccessful_is_received_and_sent_fired()
  {
    PropertyChangeEvent loginSuccessful = new PropertyChangeEvent(new Object(), "Login Successful", null, null);
    ArrayList<PropertyChangeEvent> events = new ArrayList<>();
    loginViewModel.addPropertyChangeListener(evt -> {
      events.add(evt);
    });
    loginViewModel.propertyChange(loginSuccessful);
    assertEquals(1, events.size());
    assertEquals("Login Successful", events.get(0).getPropertyName());
  }

  @Test public void if_usernameInvalid_propertyChangeEvent_error_is_set()
  {
    PropertyChangeEvent usernameInvalid = new PropertyChangeEvent(new Object(), "Username invalid", "cat", null);
    loginViewModel.propertyChange(usernameInvalid);
    assertEquals("The username 'cat' is invalid.", error.get());
  }

  @Test public void if_password_is_incorrect_error_is_set()
  {
    PropertyChangeEvent loginFailed = new PropertyChangeEvent(new Object(), "Login failed", null, null);
    loginViewModel.propertyChange(loginFailed);
    assertEquals("Incorrect password", error.get());
  }

  @Test public void removing_listeners_works()
  {
    loginViewModel.removePropertyChangeListener(listener);
  }

  @Test public void property_change_Database_Connection_Offline_is_received_and_sent_fired()
  {
    PropertyChangeEvent loginSuccessful = new PropertyChangeEvent(new Object(), "Database Connection Offline", null, "");
    ArrayList<PropertyChangeEvent> events = new ArrayList<>();
    loginViewModel.addPropertyChangeListener(evt -> {
      events.add(evt);
    });
    loginViewModel.propertyChange(loginSuccessful);
    assertEquals(1, events.size());
    assertEquals("Database Failed", events.get(0).getPropertyName());
  }
}
