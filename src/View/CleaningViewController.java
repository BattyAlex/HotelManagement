package View;

import ViewModel.CleaningViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

/**
 * Controller class for managing the cleaning view
 */

public class CleaningViewController
{
  @FXML private Label roomInfo;
  @FXML private Label roomNumber;
  @FXML private Label roomState;
  private Region root;
  private CleaningViewModel cleaningViewModel;
  private ViewHandler viewHandler;

  /**
   * Initializes the CleaningViewController with the given ViewHandler, CleaningViewModel and roo Region
   * @param viewHandler  the handler for view transitions
   * @param cleaningViewModel  the ViewModel for managing cleaning operations
   * @param root  the root region for this view
   */

  public void init(ViewHandler viewHandler, CleaningViewModel cleaningViewModel, Region root)
  {
    this.cleaningViewModel = cleaningViewModel;
    this.root = root;
    this.viewHandler = viewHandler;
    cleaningViewModel.bindRoomInfo(roomInfo.textProperty());
    cleaningViewModel.bindRoomNumber(roomNumber.textProperty());
    cleaningViewModel.bindRoomState(roomState.textProperty());
  }

  /**
   * Gets the root region for this view
   * @return  the root region
   */

  public Region getRoot()
  {
    return root;
  }

  /**
   * Handles the cancel action, transitioning to the ROOM view
   */

  @FXML public void onCancel()
  {
    viewHandler.openView(ViewFactory.ROOM);
  }

  /**
   * Handles the action when cleaning is completed
   */
  @FXML public void onCleaned()
  {
    cleaningViewModel.setCleaned();
    viewHandler.openView(ViewFactory.ROOM);
  }

  /**
   * Handles the action when the room is under cleaning
   */
  @FXML public void onUnderCleaning()
  {
    cleaningViewModel.setUndergoingCleaning();
    viewHandler.openView(ViewFactory.ROOM);
  }
}
