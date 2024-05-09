package ViewModel;

import Model.HotelModel;

public class ViewModelFactory
{
  private LoginViewModel loginViewModel;
  private ReservationViewModel reservationViewModel;
  private RoomViewModel roomViewModel;

  public ViewModelFactory(HotelModel model)
  {
    loginViewModel = new LoginViewModel(model);
    reservationViewModel = new ReservationViewModel(model);
    roomViewModel = new RoomViewModel(model);
  }

  public LoginViewModel getLoginViewModel()
  {
    return loginViewModel;
  }

  public ReservationViewModel getReservationViewModel()
  {
    return reservationViewModel;
  }

  public RoomViewModel getRoomViewModel()
  {
    return roomViewModel;
  }
}
