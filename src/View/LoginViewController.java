package View;

import ViewModel.LoginViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class LoginViewController implements PropertyChangeListener
{
  @FXML public TextField username;

  @FXML public PasswordField password;
  @FXML public Label error;
  private Region root;
  private LoginViewModel loginViewModel;
  private ViewHandler viewHandler;

  public void init(ViewHandler viewHandler, LoginViewModel loginViewModel, Region root)
  {
    this.loginViewModel = loginViewModel;
    this.root = root;
    this.viewHandler = viewHandler;
    loginViewModel.bindError(error.textProperty());
    loginViewModel.bindPassword(password.textProperty());
    loginViewModel.bindUsername(username.textProperty());
    loginViewModel.addPropertyChangeListener(this);
  }

  public Region getRoot()
  {
    return root;
  }

  @FXML public void onLogin()
  {
    loginViewModel.tryLogin();
  }

  @Override public void propertyChange(PropertyChangeEvent evt)
  {
    if(evt.getPropertyName().equals("Login Successful"))
    {
      viewHandler.openView(ViewFactory.ROOM);
    }
  }
}
