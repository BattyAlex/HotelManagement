package View;

import Model.Room;
import ViewModel.LoginViewModel;
import ViewModel.RoomViewModel;
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


  public void init(ViewHandler viewHandler, RoomViewModel roomViewModel, Region root)
  {
    this.roomViewModel = roomViewModel;
    this.root = root;
    this.viewHandler = viewHandler;
    roomViewModel.addPropertyChangeListener(this);
    roomViewModel.bindToggle(roomReservation.textProperty());
    roomViewModel.bindError(error.textProperty());
    loadAllRooms();
  }
  public Region getRoot()
  {
    return root;
  }
  @FXML public void onToggle()
  {
    roomViewModel.onToggle();
  }
  @FXML public void onSearch()
  {
    if(roomViewModel.getToggle().equals("To reservations"))
      roomViewModel.loadAvailableRooms(dateStart.getValue(), dateEnd.getValue());
    else
      roomViewModel.loadReservationsInTimeframe(dateStart.getValue(), dateEnd.getValue());
  }

  @Override public void propertyChange(PropertyChangeEvent evt)
  {
    if(evt.getPropertyName().equals("Load Room List"))
    {
      roomsAndReservations.getItems().clear();
      ArrayList<Room> rooms = (ArrayList<Room>) evt.getNewValue();
      for (int i = 0; i < rooms.size(); i++)
      {
        roomsAndReservations.getItems().add(rooms.get(i));
      }
    }
    else if (evt.getPropertyName().equals("Update Room List"))
    {
      roomsAndReservations.getItems().clear();
      ArrayList<Room> rooms = (ArrayList<Room>) evt.getNewValue();
      for (int i = 0; i < rooms.size(); i++)
      {
        roomsAndReservations.getItems().add(rooms.get(i));
      }
    }
  }
  public void loadAllRooms()
  {
    roomViewModel.loadAllRooms();
  }
  public void loadAllReservations()
  {
    roomViewModel.loadAllReservations();
  }
}
