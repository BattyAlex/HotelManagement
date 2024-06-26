package View;

import Model.Room;
import ViewModel.ViewModelFactory;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Region;

import java.io.IOException;
import java.security.PublicKey;

/**
 * The ViewFactory class is responsible for loading different views in the application.
 * It manages the controllers and their initialization for the views.
 */

public class ViewFactory
{
  public static final String LOGIN = "login";
  public static final String ROOM = "room";
  public static final String RESERVATION = "reservation";
  public static final String CLEANING = "cleaning";
  private final ViewHandler viewHandler;
  private final ViewModelFactory viewModelFactory;
  private LoginViewController loginViewController;
  private RoomViewController roomViewController;
  private ReservationViewController reservationViewController;
  private CleaningViewController cleaningViewController;

  /**
   * Constructs a ViewFactory with the specified ViewHandler and ViewModel.
   *
   * @param viewHandler  The handler for managing views
   * @param viewModelFactory The factory for creating view models.
   */

  public ViewFactory(ViewHandler viewHandler, ViewModelFactory viewModelFactory)
  {
    this.viewHandler = viewHandler;
    this.viewModelFactory = viewModelFactory;
    loginViewController = null;
    roomViewController = null;
    reservationViewController = null;
    cleaningViewController = null;
  }

  /**
   * Loads and returns the login view
   *
   * @return The root region of the login view.
   */

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

  /**
   * Loads and returns the room view
   *
   * @return The root region of the room view
   */
  public Region loadRoomView()
  {
    if(roomViewController == null)
    {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(getClass().getResource("RoomView.fxml"));
      try
      {
        Region root = loader.load();
        roomViewController = loader.getController();
        roomViewController.init(viewHandler, viewModelFactory.getRoomViewModel(), root);
      }
      catch (IOException e)
      {
        System.out.println("Room View failed to load");
        e.printStackTrace();
      }
    }
    return roomViewController.getRoot();
  }

  /**
   * Loads and returns the reservation view
   *
   * @return The root region of the reservation view
   */
  public Region loadReservationView()
  {
    if(reservationViewController == null)
    {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(getClass().getResource("ReservationView.fxml"));
      try
      {
        Region root = loader.load();
        reservationViewController = loader.getController();
        reservationViewController.init(viewHandler, viewModelFactory.getReservationViewModel(), root);
      }
      catch (IOException e)
      {
        System.out.println("Reservation view failed to load");
        e.printStackTrace();
      }
    }
    return reservationViewController.getRoot();
  }

  /**
   * Loads and returns the cleaning view
   *
   * @return The root region of the cleaning view
   */
  public Region loadCleaningView()
  {
    if(cleaningViewController == null)
    {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(getClass().getResource("CleaningView.fxml"));
      try
      {
        Region root = loader.load();
        cleaningViewController = loader.getController();
        cleaningViewController.init(viewHandler, viewModelFactory.getCleaningViewModel(), root);
      }
      catch (IOException e)
      {
        System.out.println("Cleaning View failed to load");
        e.printStackTrace();
      }
    }
    return cleaningViewController.getRoot();
  }

  /**
   * Loads and returns the view specified by the ID
   *
   * @param id The ID of the view to load
   * @return The root region of the specified view
   */


  public Region load(String id)
  {
    Region root = switch (id)
    {
      case LOGIN -> loadLoginView();
      case ROOM -> loadRoomView();
      case RESERVATION -> loadReservationView();
      case CLEANING -> loadCleaningView();
      default -> throw new IllegalArgumentException("Unknown view: " + id);
    };
    return root;
  }
}
