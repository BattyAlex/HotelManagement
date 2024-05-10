package View;

import Model.Room;
import ViewModel.ViewModelFactory;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Region;

import java.io.IOException;
import java.security.PublicKey;

public class ViewFactory
{
  public static final String LOGIN = "login";
  private final ViewHandler viewHandler;
  private final ViewModelFactory viewModelFactory;
  private LoginViewController loginViewController;
  private ReservationViewController reservationViewController;
  private RoomViewController roomViewController;

  public ViewFactory(ViewHandler viewHandler, ViewModelFactory viewModelFactory)
  {
    this.viewHandler = viewHandler;
    this.viewModelFactory = viewModelFactory;
    loginViewController = null;
    roomViewController = null;
    reservationViewController = null;
  }

  public Region loadLoginView()
  {
    if(loginViewController == null)
    {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(getClass().getResource("LoginView.fxml"));
      try
      {
        Region root = loader.load();
        loginViewController = loader.getController();
        loginViewController.init(viewHandler, viewModelFactory.getLoginViewModel(), root);
      }
      catch (IOException e)
      {
        System.out.println("Login View failed to load");
        e.printStackTrace();
      }
    }
    return loginViewController.getRoot();
  }

  public Region loadReservationView()
  {

  }

  public Region load(String id)
  {
    Region root = switch (id)
    {
      case LOGIN -> loadLoginView();
      default -> throw new IllegalArgumentException("Unknown view: " + id);
    };
    return root;
  }
}
