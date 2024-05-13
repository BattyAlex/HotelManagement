package View;

import ViewModel.LoginViewModel;
import ViewModel.RoomViewModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.Region;

public class RoomViewController
{
  private Region root;
  private RoomViewModel roomViewModel;
  private ViewHandler viewHandler;
  @FXML private ToggleButton roomReservation;
  @FXML private ListView roomsAndReservations;


  public void init(ViewHandler viewHandler, RoomViewModel roomViewModel, Region root)
  {
    this.roomViewModel = roomViewModel;
    this.root = root;
    roomViewModel.bindToggle(roomReservation.textProperty());
  }
  public Region getRoot()
  {
    return root;
  }
  @FXML public void onToggle()
  {
    roomViewModel.onToggle();
  }

}
