import Model.HotelModel;
import Model.HotelModelManager;
import ViewModel.RoomViewModel;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;

import java.beans.PropertyChangeSupport;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class RoomViewModelTesting
{
  private HotelModel model;
  private RoomViewModel roomViewModel;
  private StringProperty toggle;
  private StringProperty error;
  private BooleanProperty canClick;
  private StringProperty cleaningToggle;
  @BeforeEach public void setUp()
  {
    model = Mockito.mock(HotelModelManager.class);
    roomViewModel = new RoomViewModel(model);
    toggle = new SimpleStringProperty("To reservations");
  }

  //RoomViewModel

  @Test public void if_startDate_is_before_endDate_return_true()
  {
    LocalDate startDate = LocalDate.of(2024, 1,20);
    LocalDate endDate = LocalDate.of(2024, 1, 21);
    assertTrue(roomViewModel.areDatesCorrect(startDate, endDate));
  }

  @Test public void if_startDate_and_endDate_on_same_day_return_false()
  {
    LocalDate startDate = LocalDate.of(2024, 1,20);
    LocalDate endDate = LocalDate.of(2024, 1, 20);
    assertFalse(roomViewModel.areDatesCorrect(startDate, endDate));
  }

  @Test public void if_startDate_and_endDate_are_null_return_false()
  {
    LocalDate startDate = null;
    LocalDate endDate = null;
    assertFalse(roomViewModel.areDatesCorrect(startDate, endDate));
  }

  @Test public void if_endDate_is_before_startDate_return_false()
  {
    LocalDate startDate = LocalDate.of(2024, 1,20);
    LocalDate endDate = LocalDate.of(2024, 1, 19);
    assertFalse(roomViewModel.areDatesCorrect(startDate, endDate));
  }


}
