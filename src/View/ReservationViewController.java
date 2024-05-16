package View;

import Model.Room;
import ViewModel.ReservationViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import javafx.scene.layout.Region;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDate;
import java.util.ArrayList;

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
  @FXML private ComboBox roomNumber;
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
    reservationViewModel.bindCard(cardInfo.textProperty());
    reservationViewModel.bindFirstName(firstName.textProperty());
    reservationViewModel.bindLastName(lastName.textProperty());
    reservationViewModel.bindAirportTrans(airportTransport.selectedProperty());
    reservationViewModel.bindBreakfast(breakfast.selectedProperty());
    reservationViewModel.bindLunch(lunch.selectedProperty());
    reservationViewModel.bindDinner(dinner.selectedProperty());
    reservationViewModel.bindRoomService(roomService.selectedProperty());
    reservationViewModel.bindWellness(wellness.selectedProperty());
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
    Alert alert = new Alert(Alert.AlertType.WARNING, "Are you sure you want to discard your changes?", ButtonType.YES, ButtonType.NO);
    alert.setTitle("Cancel");
    alert.showAndWait();
    if(alert.getResult() == ButtonType.YES)
    {
      viewHandler.openView(ViewFactory.ROOM);
    }

  }

  @Override public void propertyChange(PropertyChangeEvent evt)
  {
    if(evt.getPropertyName().equals("Set Current Room"))
    {
      if(roomNumber.getItems().isEmpty())
      {
        roomNumber.getItems().add((Integer)evt.getNewValue());
        roomNumber.getSelectionModel().select(0);
      }
    }
    else if(evt.getPropertyName().equals("Display Dates"))
    {
      startDate.setValue((LocalDate) evt.getNewValue());
      endDate.setValue((LocalDate) evt.getOldValue());
    }
    else if (evt.getPropertyName().equals("Display Available Rooms"))
    {
      roomNumber.getItems().clear();
      ArrayList<Room> rooms = (ArrayList<Room>) evt.getNewValue();
      for (int i = 0; i < rooms.size(); i++)
      {
        roomNumber.getItems().add((Integer)rooms.get(i).getRoomNumber());
      }
      if(!roomNumber.getItems().isEmpty())
      {
        roomNumber.getSelectionModel().select(0);
        reservationViewModel.roomSelected(rooms.get(0), startDate.getValue(), endDate.getValue());
      }
      else
      {
        reservationViewModel.setAmenities("");
      }
    }
  }

  @FXML public void onSelectNewRoom()
  {
    if(roomNumber.getSelectionModel().getSelectedItem() != null)
    {
      reservationViewModel.roomSelected((Integer) roomNumber.getSelectionModel().getSelectedItem(), startDate.getValue(), endDate.getValue());
    }
  }
  @FXML public void onConfirm()
  {

  }
}
