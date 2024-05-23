package View;

import Model.Guest;
import Model.Reservation;
import Model.Room;
import Model.Staff;
import ViewModel.ReservationViewModel;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import javafx.scene.layout.Region;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * The ReservationViewController class is responsible for handling the view logic for reservations.
 */

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
  @FXML private TextField noOfGuests;
  @FXML private Button delete;
  @FXML private Button checkOut;
  @FXML private Label reservationId;
  private SimpleBooleanProperty canClick;

  /**
   * Initialzes the controller with the necessary handlers and model
   *
   * @param viewHandler  the view handler
   * @param reservationViewModel the reservation view model
   * @param root the root region
   */


  public void init(ViewHandler viewHandler, ReservationViewModel reservationViewModel, Region root)
  {
    this.reservationViewModel = reservationViewModel;
    this.root = root;
    this.viewHandler = viewHandler;
    canClick = new SimpleBooleanProperty();
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
    reservationViewModel.bindNoOfGuests(noOfGuests.textProperty());
    reservationViewModel.bindReservationId(reservationId.textProperty());
    reservationViewModel.bindCanClick(canClick);
    reservationViewModel.bindDelete(delete.disableProperty());
    reservationViewModel.bindCheckOut(checkOut.disableProperty());
  }

  /**
   * Gets the root region
   * @return the root region
   */

  public Region getRoot()
  {
    return root;
  }

  /**
   * Handles the search action
   */

  @FXML public void onSearch()
  {
    reservationViewModel.loadAvailableRooms(startDate.getValue(), endDate.getValue());
  }

  /**
   * Handles the cancel action
   */

  @FXML public void onCancel()
  {
    Alert alert = new Alert(Alert.AlertType.WARNING, "Are you sure you want to discard your changes?", ButtonType.YES, ButtonType.NO);
    alert.setTitle("Cancel");
    alert.showAndWait();
    if(alert.getResult() == ButtonType.YES)
    {
      reservationViewModel.onCancel();
      viewHandler.openView(ViewFactory.ROOM);
    }
  }

  /**
   * Shows an alert with the given message
   */
  public void alert()
  {
    Alert alert = new Alert(Alert.AlertType.ERROR, "One or more input values are invalid. Check your information and try again", ButtonType.OK);
    alert.setTitle("Error");
    alert.showAndWait();
  }

  /**
   * Responds to property changes in the reservation view model
   * @param evt A PropertyChangeEvent object describing the event source
   *          and the property that has changed.
   */

  @Override public void propertyChange(PropertyChangeEvent evt)
  {
    switch (evt.getPropertyName())
    {
      case "Set Current Room":
        if(roomNumber.getItems().isEmpty())
        {
          roomNumber.getItems().add((Integer)evt.getNewValue());
          roomNumber.getSelectionModel().select(0);
        }
        break;
      case "Display Dates":
        startDate.setValue((LocalDate) evt.getNewValue());
        endDate.setValue((LocalDate) evt.getOldValue());
        break;
      case "invalid input":
        alert();
        break;
      case "Display Available Rooms":
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
        break;
      case "Set Reservation":
        Reservation selected = (Reservation) evt.getOldValue();
        startDate.setValue(selected.getStartDate());
        endDate.setValue(selected.getEndDate());
        roomNumber.getItems().clear();
        roomNumber.getItems().add(selected.getRoom().getRoomNumber());
        roomNumber.getSelectionModel().select(0);
        break;
      case "Display Reservation Made Popup":
        Reservation reservation = (Reservation) evt.getNewValue();
        String contentText = "Reservation " + reservation.getReservationId() + " successfully made / updated / ended.\nThe total is: " + reservation.getTotalForStay() + "$";
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, contentText, ButtonType.OK);
        alert.setTitle("Reservation Successfully Made");
        alert.showAndWait();
        if(alert.getResult() == ButtonType.OK)
        {
          viewHandler.openView(ViewFactory.ROOM);
        }
        break;
    }
  }

  /**
   * Handles the date change action
   */

  @FXML public void datesChanged()
  {
    reservationViewModel.datesChanged();
  }

  /**
   * Handles the selection of a new room
   */

  @FXML public void onSelectNewRoom()
  {
    if(roomNumber.getSelectionModel().getSelectedItem() != null)
    {
      reservationViewModel.roomSelected((Integer) roomNumber.getSelectionModel().getSelectedItem(), startDate.getValue(), endDate.getValue());
    }
  }

  /**
   * Handles the confirmation action
   */
  @FXML public void onConfirm()
  {
    if(canClick.get())
    {
      if(roomNumber.getItems().isEmpty())
      {
        alert();
      }
      else
      {
        Room room = new Room((int)roomNumber.getItems().get(0));
        reservationViewModel.onConfirm(room,startDate.getValue(), endDate.getValue());
      }
    }
    else
    {
      reservationViewModel.setError("Please press search to display valid rooms.");
    }
  }

  /**
   * Handles the delete action
   */
  @FXML public void onDelete()
  {
    Alert alert = new Alert(Alert.AlertType.WARNING, "Are you sure you want to delete this reservation from the system?", ButtonType.YES, ButtonType.NO);
    alert.setTitle("Delete");
    alert.showAndWait();
    if(alert.getResult() == ButtonType.YES)
    {
      Reservation reservation = new Reservation(startDate.getValue(), endDate.getValue(), new Guest("", "", ""), new Room((int)roomNumber.getSelectionModel().getSelectedItem()), new Staff("", ""));
      reservationViewModel.onDelete(reservation);
      viewHandler.openView(ViewFactory.ROOM);
    }
  }

  /**
   * Handles the check-out action
   */
  @FXML public void onCheckOut()
  {
    if(LocalDate.now().isBefore(startDate.getValue()))
    {
      Alert alert = new Alert(Alert.AlertType.ERROR, "You cannot check out before the start date", ButtonType.OK);
      alert.setTitle("Check out");
      alert.showAndWait();
    }
    else if(LocalDate.now().isBefore(endDate.getValue()))
    {
      Alert warning = new Alert(Alert.AlertType.WARNING, "Are you sure you want to check out early?", ButtonType.YES, ButtonType.NO);
      warning.setTitle("Check out");
      warning.showAndWait();

      if(warning.getResult() == ButtonType.YES)
      {
        Reservation reservation = new Reservation(startDate.getValue(), endDate.getValue(), new Guest("", "", ""), new Room((int)roomNumber.getSelectionModel().getSelectedItem()), new Staff("", ""));
        reservationViewModel.onDelete(reservation);
        viewHandler.openView(ViewFactory.ROOM);
      }
    }
    else
    {
      Reservation reservation = new Reservation(startDate.getValue(), endDate.getValue(), new Guest("", "", ""), new Room((int)roomNumber.getSelectionModel().getSelectedItem()), new Staff("", ""));
      reservationViewModel.checkOut(reservation);
      viewHandler.openView(ViewFactory.ROOM);
    }
  }
}
