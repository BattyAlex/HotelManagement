package ViewModel;

import Model.HotelModel;

/**
 * The ViewModelFactory class is responsible for creating and providing instances
 * of view models used in the application.
 */

public class ViewModelFactory
{
  private LoginViewModel loginViewModel;
  private ReservationViewModel reservationViewModel;
  private RoomViewModel roomViewModel;
  private CleaningViewModel cleaningViewModel;

  /**
   * Constructs a ViewModelFactory with the specified HotelModel
   *
   * @param model The hotel model used by the view models.
   */

  public ViewModelFactory(HotelModel model)
  {
    loginViewModel = new LoginViewModel(model);
    reservationViewModel = new ReservationViewModel(model);
    roomViewModel = new RoomViewModel(model);
    cleaningViewModel = new CleaningViewModel(model);
  }

  /**
   * Returns the instance of LoginViewModel
   *
   * @return The instance of LoginViewModel.
   */
  public LoginViewModel getLoginViewModel()
  {
    return loginViewModel;
  }

  /**
   * Returns the instance of ReservationViewModel.
   *
   * @return The instance of ReservationViewModel.
   */
  public ReservationViewModel getReservationViewModel()
  {
    return reservationViewModel;
  }

  /**
   * Returns the instance of RoomViewModel.
   *
   * @return The instance of RoomViewModel.
   */

  public RoomViewModel getRoomViewModel()
  {
    return roomViewModel;
  }

  /**
   * Returns the instance of CleaningViewModel
   * @return The instance of CleaningViewModel
   */
  public CleaningViewModel getCleaningViewModel()
  {
    return cleaningViewModel;
  }
}
