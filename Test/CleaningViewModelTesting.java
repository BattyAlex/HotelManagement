import Model.HotelModel;
import Model.HotelModelManager;
import ViewModel.CleaningViewModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;

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
    model = Mockito.mock(HotelModelManager.class);
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
}
