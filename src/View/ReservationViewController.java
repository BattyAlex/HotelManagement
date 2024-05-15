package View;

import ViewModel.ReservationViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import javafx.scene.layout.Region;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ReservationViewController implements PropertyChangeListener
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
  @FXML private CheckBox roomService;
  @FXML private CheckBox airportTransport;
  @FXML private CheckBox breakfast;
  @FXML private CheckBox lunch;
  @FXML private CheckBox dinner;
  @FXML private CheckBox wellness;
  @FXML private Label error;


  public void init(ViewHandler viewHandler, ReservationViewModel reservationViewModel, Region root)
  {
    this.reservationViewModel = reservationViewModel;
    this.root = root;
    this.viewHandler = viewHandler;
    reservationViewModel.bindError(error.textProperty());
    reservationViewModel.bindAmenities(amenities.textProperty());
    reservationViewModel.addPropertyChangeListener(this);
  }

  public Region getRoot()
  {
    return root;
  }

  @FXML public void onSearch()
  {
    reservationViewModel.loadAvailableRooms(startDate.getValue(), endDate.getValue());
  }

  @FXML public void onCancel()
  {
    viewHandler.openView(ViewFactory.ROOM);
  }

  @Override public void propertyChange(PropertyChangeEvent evt)
  {
    if(evt.getPropertyName().equals("Set Current Room"))
    {
      roomNumber.getItems().clear();
      roomNumber.getItems().add((Integer)evt.getNewValue());
      roomNumber.getSelectionModel().select(0);
    }
  }
}
