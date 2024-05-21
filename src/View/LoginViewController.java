package View;

import ViewModel.LoginViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Region;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * The LoginViewController class handles the user interactions for the login view
 * It listens to property changes and updates the view accordingly
 */
public class LoginViewController implements PropertyChangeListener
{
  @FXML public TextField username;
  @FXML public PasswordField password;
  @FXML public Label error;
  private Region root;
  private LoginViewModel loginViewModel;
  private ViewHandler viewHandler;

  /**
   * Initializes the controller with the specified ViewHandler, LoginViewModel and root
   * @param viewHandler The handler for managing views
   * @param loginViewModel The view model for handling login logic
   * @param root The root region of the view
   */
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

  /**
   * Returns the root region of the view
   * @return The root region of the view
   */
  public Region getRoot()
  {
    return root;
  }

  /**
   * Handles the login action triggered by the user
   */
  @FXML public void onLogin()
  {
    loginViewModel.tryLogin();
  }

  /**
   * Handles property chnages and updates the view accordingly
   * @param evt A PropertyChangeEvent object describing the event source
   *          and the property that has changed.
   */
  @Override public void propertyChange(PropertyChangeEvent evt)
  {
    switch (evt.getPropertyName())
    {
      case "Login Successful":
        viewHandler.openView(ViewFactory.ROOM);
        break;
      case "Database Failed":
        Alert alert = new Alert(Alert.AlertType.WARNING, (String) evt.getNewValue(), ButtonType.OK);
        alert.showAndWait();
    }
  }
}
