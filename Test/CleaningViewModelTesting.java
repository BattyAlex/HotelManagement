import Model.HotelModel;
import Model.HotelModelManager;
import Model.Room;
import Model.RoomType;
import View.ViewFactory;
import ViewModel.CleaningViewModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class CleaningViewModelTesting
{
  private HotelModel model;
  private CleaningViewModel cleaningViewModel;
  private StringProperty roomInfo;
  private StringProperty roomNumber;
  private StringProperty roomState;

  @BeforeEach public void setUp()
  {
    model = Mockito.mock(HotelModel.class);
    cleaningViewModel = new CleaningViewModel(model);
    roomInfo = new SimpleStringProperty();
    roomNumber = new SimpleStringProperty();
    roomState = new SimpleStringProperty();

    cleaningViewModel.bindRoomInfo(roomInfo);
    cleaningViewModel.bindRoomNumber(roomNumber);
    cleaningViewModel.bindRoomState(roomState);
  }

  @Test public void newly_created_viewModel_is_not_null()
  {
    assertNotNull(cleaningViewModel);
  }

  @Test public void initial_values_match()
  {
    assertEquals("", roomInfo.get());
    assertEquals("", roomNumber.get());
    assertEquals("", roomState.get());
  }

  @Test public void on_propertyChange_set_values_change() {
    Room room = new Room("single", 120, 1, "cleaned");
    PropertyChangeEvent availableRoomsEvent = new PropertyChangeEvent(new Object(),"Room Selected For Cleaning", room, null);
    cleaningViewModel.propertyChange(availableRoomsEvent);
    assertEquals("1", roomNumber.get());
    assertEquals("cleaned", roomState.get());
    assertEquals(room.toString(), roomInfo.get());
  }

  @Test public void setCleaned_resets_values()
  {
    Room room = new Room("single", 120, 1, "cleaned");
    PropertyChangeEvent availableRoomsEvent = new PropertyChangeEvent(new Object(),"Room Selected For Cleaning", room, null);
    cleaningViewModel.propertyChange(availableRoomsEvent);
    cleaningViewModel.setCleaned();
    assertEquals("", roomNumber.get());
    assertEquals("", roomState.get());
    assertEquals("", roomInfo.get());
  }

  @Test public void setCleaned_calls_model_method_loadAllRooms()
  {
    Room room = new Room("single", 120, 1, "cleaned");
    PropertyChangeEvent availableRoomsEvent = new PropertyChangeEvent(new Object(),"Room Selected For Cleaning", room, null);
    cleaningViewModel.propertyChange(availableRoomsEvent);
    cleaningViewModel.setCleaned();
    Mockito.verify(model).loadAllRooms();
  }

  @Test public void setUndergoingCleaning_resets_values()
  {
    Room room = new Room("single", 120, 1, "cleaned");
    PropertyChangeEvent availableRoomsEvent = new PropertyChangeEvent(new Object(),"Room Selected For Cleaning", room, null);
    cleaningViewModel.propertyChange(availableRoomsEvent);
    cleaningViewModel.setUndergoingCleaning();
    assertEquals("", roomNumber.get());
    assertEquals("", roomState.get());
    assertEquals("", roomInfo.get());
  }

  @Test public void setUndergoingCleaning_calls_model_method_loadAllRooms()
  {
    Room room = new Room("single", 120, 1, "cleaned");
    PropertyChangeEvent availableRoomsEvent = new PropertyChangeEvent(new Object(),"Room Selected For Cleaning", room, null);
    cleaningViewModel.propertyChange(availableRoomsEvent);
    cleaningViewModel.setUndergoingCleaning();
    Mockito.verify(model).loadAllRooms();
  }

}
