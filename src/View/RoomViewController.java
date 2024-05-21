package View;

import Model.Reservation;
import Model.Room;
import ViewModel.LoginViewModel;
import ViewModel.RoomViewModel;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Region;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

/**
 * The RoomViewController class acts as the controller for the root-related views.
 * It implements PropertyChangeListener to handle property change events and updates the view accordingly.
 */

public class RoomViewController implements PropertyChangeListener
{
  private Region root;
  private RoomViewModel roomViewModel;
  private ViewHandler viewHandler;
  @FXML private ToggleButton roomReservation;
  @FXML private ListView roomsAndReservations;
  @FXML private DatePicker dateStart;
  @FXML private DatePicker dateEnd;
  @FXML private Button search;
  @FXML private Label error;
  @FXML private ToggleButton cleaningToggle;
  private SimpleBooleanProperty canClick;

  /**
   * Initialozes the RoomViewController with the specified ViewHandler, RoomViewModel and root Region.
   *
   * @param viewHandler   the ViewHandler to be used by this controller.
   * @param roomViewModel the RoomViewModel to be used by this controller
   * @param root          the root Region of the view
   */

  public void init(ViewHandler viewHandler, RoomViewModel roomViewModel,
      Region root)
  {
    this.roomViewModel = roomViewModel;
    this.root = root;
    this.viewHandler = viewHandler;
    canClick = new SimpleBooleanProperty();
    roomViewModel.addPropertyChangeListener(this);
    roomViewModel.bindToggle(roomReservation.textProperty());
    roomViewModel.bindError(error.textProperty());
    roomViewModel.bindCanClick(canClick);
    roomViewModel.bindCleaningToggle(cleaningToggle.textProperty());
    loadAllRooms();
  }


  /**
   * Returns the root Region of the view
   *
   * @return the root Region of the view
   */
  public Region getRoot()
  {
    return root;
  }

  /**
   * Handles the toggle button action and toggles the room reservation state.
   */
  @FXML public void onToggle()
  {
    roomViewModel.onToggle();
  }

  /**
   * Handles the search button action and loads the available rooms for the specified date range.
   */
  @FXML public void onSearch()
  {
    if (roomViewModel.getToggle().equals("To reservations"))
      roomViewModel.loadAvailableRooms(dateStart.getValue(),
          dateEnd.getValue());
    else
      roomViewModel.loadReservationsInTimeframe(dateStart.getValue(),
          dateEnd.getValue());
  }

  @FXML public void onClick()
  {
    if(roomsAndReservations.getSelectionModel().getSelectedItem() != null)
    {
      if(roomsAndReservations.getSelectionModel().getSelectedItem() instanceof Reservation)
      {
        viewHandler.openView(ViewFactory.RESERVATION);
        Reservation selected = (Reservation) roomsAndReservations.getSelectionModel()
            .getSelectedItem();
        roomViewModel.reservationSelected(selected);
      }
      else if (canClick.getValue())
      {
        if(roomViewModel.areDatesCorrect(dateStart.getValue(), dateEnd.getValue()))
        {
          if(roomsAndReservations.getSelectionModel().getSelectedItem() instanceof Room)
          {
            Room selected = (Room) roomsAndReservations.getSelectionModel().getSelectedItem();
            viewHandler.openView(ViewFactory.RESERVATION);
            roomViewModel.roomSelected(selected, dateStart.getValue(), dateEnd.getValue());
          }
        }
        else
        {
          roomViewModel.setError("Please select valid dates");
        }
      }
      else
      {
        Room selected = (Room) roomsAndReservations.getSelectionModel().getSelectedItem();
        roomViewModel.selectRoomForCleaning(selected);
        viewHandler.openView(ViewFactory.CLEANING);
      }
    }
  }
  @FXML public void onCleaning()
  {
    roomViewModel.onCleaning();
  }

  private void loadRooms(ArrayList<Room> rooms)
  {
    roomsAndReservations.getItems().clear();
    for (int i = 0; i < rooms.size(); i++)
    {
      roomsAndReservations.getItems().add(rooms.get(i));
    }
  }

  private void loadReservations(ArrayList<Reservation> reservations)
  {
    roomsAndReservations.getItems().clear();
    for (int i = 0; i < reservations.size(); i++)
    {
      roomsAndReservations.getItems().add(reservations.get(i));
    }
  }

  /**
   * Handles property change events and updates the view accordingly
   *
   * @param evt A PropertyChangeEvent object describing the event source
   *            and the property that has changed.
   */

  @Override public void propertyChange(PropertyChangeEvent evt)
  {
    switch (evt.getPropertyName())
    {
      case "Load Room List":
        loadRooms((ArrayList<Room>) evt.getNewValue());
        break;
      case "Load Reservation List":
        loadReservations((ArrayList<Reservation>) evt.getNewValue());
        break;
      case "Load All Reservations":
        loadAllReservations();
        break;
    }
  }

  @FXML public void datesChanged()
  {
    roomViewModel.datesChanged();
  }

  /**
   * Loads all rooms using the RoomViewModel
   */
  public void loadAllRooms()
  {
    roomViewModel.loadAllRooms();
  }

  public void loadAllReservations()
  {
    roomViewModel.loadAllReservations();
  }
}
