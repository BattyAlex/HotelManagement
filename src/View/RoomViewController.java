package View;

import ViewModel.LoginViewModel;
import ViewModel.RoomViewModel;
import javafx.scene.layout.Region;

public class RoomViewController
{
  private Region root;
  private RoomViewModel roomViewModel;
  private ViewHandler viewHandler;

  public void init(ViewHandler viewHandler, RoomViewModel roomViewModel, Region root)
  {
    this.roomViewModel = roomViewModel;
    this.root = root;
  }
  public Region getRoot()
  {
    return root;
  }

}
