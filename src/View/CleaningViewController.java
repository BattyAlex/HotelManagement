package View;

import ViewModel.CleaningViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

public class CleaningViewController
{
  @FXML private Label roomInfo;
  private Region root;
  private CleaningViewModel cleaningViewModel;
  private ViewHandler viewHandler;

  public void init(ViewHandler viewHandler, CleaningViewModel cleaningViewModel, Region root)
  {
    this.cleaningViewModel = cleaningViewModel;
    this.root = root;
    this.viewHandler = viewHandler;

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

  }
  @FXML public void onUnderCleaning()
  {

  }
}
