package View;

import Model.Room;
import ViewModel.LoginViewModel;
import ViewModel.RoomViewModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.ToggleButton;
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


  public void init(ViewHandler viewHandler, RoomViewModel roomViewModel, Region root)
  {
    this.roomViewModel = roomViewModel;
    this.root = root;
    roomViewModel.addPropertyChangeListener(this);
    roomViewModel.bindToggle(roomReservation.textProperty());
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
    if(dateStart != null && dateEnd != null)
    {
      Date startDate = new Date(dateStart.getValue().getYear(), dateStart.getValue().getMonthValue(), dateStart.getValue().getDayOfMonth());
      Date endDate = new Date(dateEnd.getValue().getYear(), dateEnd.getValue().getMonthValue(), dateEnd.getValue().getDayOfMonth());
      roomViewModel.loadAvailableRooms(startDate, endDate);
    }
  }

  @Override public void propertyChange(PropertyChangeEvent evt)
  {
    if(evt.getPropertyName().equals("Update Room List"))
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
}
