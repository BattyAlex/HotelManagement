package View;

import Model.Room;
import ViewModel.ViewModelFactory;

import java.security.PublicKey;

public class ViewFactory
{
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

}
