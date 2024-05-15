package View;

import ViewModel.ReservationViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;

import javax.swing.plaf.synth.Region;
import java.awt.*;

public class ReservationViewController
{
  private Region root;
  private ReservationViewModel reservationViewModel;
  private ViewHandler viewHandler;
  @FXML private TextField firstName;
  @FXML private TextField lastName;
  @FXML private TextField cardInfo;
  @FXML private Label amenities;
  @FXML private DatePicker startDate;
  @FXML private DatePicker endDate;
  @FXML private ChoiceBox roomNumber;
  @FXML private Checkbox roomService;
  @FXML private Checkbox airportTransport;
  @FXML private Checkbox breakfast;
  @FXML private Checkbox lunch;
  @FXML private Checkbox dinner;
  @FXML private Checkbox wellness;

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
