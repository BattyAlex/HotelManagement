package View;

import ViewModel.CleaningViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

public class CleaningViewController
{
  @FXML private Label roomInfo;
  @FXML private Label roomNumber;
  @FXML private Label roomState;
  private Region root;
  private CleaningViewModel cleaningViewModel;
  private ViewHandler viewHandler;

  public void init(ViewHandler viewHandler, CleaningViewModel cleaningViewModel, Region root)
  {
    this.cleaningViewModel = cleaningViewModel;
    this.root = root;
    this.viewHandler = viewHandler;
    cleaningViewModel.bindRoomInfo(roomInfo.textProperty());
    cleaningViewModel.bindRoomNumber(roomNumber.textProperty());
    cleaningViewModel.bindRoomState(roomState.textProperty());
  }

  public Region getRoot()
  {
    return root;
  }

  @FXML public void onCancel()
  {
    viewHandler.openView(ViewFactory.ROOM);
  }
  @FXML public void onCleaned()
  {
    cleaningViewModel.setCleaned();
    viewHandler.openView(ViewFactory.ROOM);
  }
  @FXML public void onUnderCleaning()
  {
    cleaningViewModel.setUndergoingCleaning();
    viewHandler.openView(ViewFactory.ROOM);
  }
}
