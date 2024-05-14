package View;

import ViewModel.ReservationViewModel;

import javax.swing.plaf.synth.Region;

public class ReservationViewController
{
  private Region root;
  private ReservationViewModel reservationViewModel;
  private ViewHandler viewHandler;

  public void init(ViewHandler viewHandler, ReservationViewModel reservationViewModel, Region root)
  {
    this.reservationViewModel = reservationViewModel;
    this.root = root;
    this.viewHandler = viewHandler;
  }

  public Region getRoot()
  {
    return root;
  }
}
