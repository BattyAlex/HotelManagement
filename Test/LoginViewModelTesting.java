import Model.HotelModel;
import Model.HotelModelManager;
import ViewModel.LoginViewModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

public class LoginViewModelTesting
{
  private HotelModel model;
  private LoginViewModel loginViewModel;
  private StringProperty username;
  private StringProperty password;
  private StringProperty error;
  @BeforeEach public void setUp()
  {
    model = Mockito.mock(HotelModelManager.class);
    loginViewModel = new LoginViewModel(model);
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
}
