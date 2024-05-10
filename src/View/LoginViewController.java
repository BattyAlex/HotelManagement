package View;

import ViewModel.LoginViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;

public class LoginViewController
{
  @FXML public TextField username;
  @FXML public TextField password;
  @FXML public Label error;
  private Region root;
  private LoginViewModel loginViewModel;
  private ViewHandler viewHandler;

  public void init(ViewHandler viewHandler, LoginViewModel loginViewModel, Region root)
  {
    this.loginViewModel = loginViewModel;
    this.root = root;
    loginViewModel.bindError(error.textProperty());
    loginViewModel.bindPassword(password.textProperty());
    loginViewModel.bindUsername(username.textProperty());
  }

  public Region getRoot()
  {
    return root;
  }

  @FXML public void onLogin()
  {

  }
}
