import Model.HotelModel;
import Model.HotelModelManager;
import Model.Reservation;
import Model.Room;
import View.RoomViewController;
import ViewModel.RoomViewModel;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class RoomViewModelTesting
{
  private HotelModel model;
  private RoomViewModel roomViewModel;
  private StringProperty toggle;
  private StringProperty error;
  private SimpleBooleanProperty canClick;
  private StringProperty cleaningToggle;
  private PropertyChangeListener listener;
  @BeforeEach public void setUp()
  {
    listener = Mockito.mock(PropertyChangeListener.class);
    model = Mockito.mock(HotelModelManager.class);
    roomViewModel = new RoomViewModel(model);
    toggle = new SimpleStringProperty();
    error = new SimpleStringProperty();
    canClick = new SimpleBooleanProperty();
    cleaningToggle = new SimpleStringProperty();
    roomViewModel.bindToggle(toggle);
    roomViewModel.bindError(error);
    roomViewModel.bindCanClick(canClick);
    roomViewModel.bindCleaningToggle(cleaningToggle);
  }

  @Test public void newly_created_RoomViewModel_is_not_null()
  {
    assertNotNull(roomViewModel);
  }

  @Test public void initial_values_of_properties_are_set()
  {
    assertEquals("To reservations", toggle.get());
    assertEquals("", error.get());
    assertFalse(canClick.get());
    assertEquals("Needs cleaning", cleaningToggle.get());
  }

  @Test public void onToggle_changes_toggle_value_to_Torooms_initially_clicked()
  {
    roomViewModel.onToggle();
    assertEquals("To rooms", toggle.get());
  }

  @Test public void onToggle_value_stays_same_if_clicked_twice()
  {
    roomViewModel.onToggle();
    roomViewModel.onToggle();
    assertEquals("To reservations", toggle.get());
  }

  @Test public void onToggle_changes_canClick_to_false()
  {
    canClick.set(true);
    assertTrue(canClick.get());
    roomViewModel.onToggle();
    assertFalse(canClick.get());
  }

  @Test public void adding_and_removing_propertyChangeListeners_does_not_cause_problems()
  {
    roomViewModel.addPropertyChangeListener(listener);
    roomViewModel.removePropertyChangeListener(listener);
  }
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

  @Test public void if_dates_are_changed_canClick_is_set_to_false()
  {
    canClick.set(true);
    assertTrue(canClick.get());
    roomViewModel.datesChanged();
    assertFalse(canClick.get());
  }

  @Test public void if_startDate_is_before_endDate_error_is_set_to_nothing()
  {
    LocalDate startDate = LocalDate.of(2024, 1,20);
    LocalDate endDate = LocalDate.of(2024, 1, 21);
    roomViewModel.loadAvailableRooms(startDate, endDate);
    assertEquals("", error.get());
  }

  @Test public void if_startDate_is_before_endDate_canClick_is_set_to_true()
  {
    LocalDate startDate = LocalDate.of(2024, 1,20);
    LocalDate endDate = LocalDate.of(2024, 1, 21);
    roomViewModel.loadAvailableRooms(startDate, endDate);
    assertTrue(canClick.get());
  }

  @Test public void if_startDate_and_endDate_on_same_day_error_is_set()
  {
    LocalDate startDate = LocalDate.of(2024, 1,20);
    LocalDate endDate = LocalDate.of(2024, 1, 20);
    roomViewModel.loadAvailableRooms(startDate, endDate);
    assertEquals("The same dates have been selected.", error.get());
  }

  @Test public void if_startDate_and_endDate_on_same_day_canClick_stays_false()
  {
    LocalDate startDate = LocalDate.of(2024, 1,20);
    LocalDate endDate = LocalDate.of(2024, 1, 20);
    roomViewModel.loadAvailableRooms(startDate, endDate);
    assertFalse(canClick.get());
  }

  @Test public void if_startDate_and_endDate_are_null_error_is_set()
  {
    LocalDate startDate = null;
    LocalDate endDate = null;
    roomViewModel.loadAvailableRooms(startDate, endDate);
    assertEquals("Date from or until is empty, please choose a date.", error.get());
  }

  @Test public void if_startDate_and_endDate_are_null_canClick_is_false()
  {
    LocalDate startDate = null;
    LocalDate endDate = null;
    roomViewModel.loadAvailableRooms(startDate, endDate);
    assertFalse(canClick.get());
  }

  @Test public void if_endDate_is_before_startDate_error_is_set()
  {
    LocalDate startDate = LocalDate.of(2024, 1,20);
    LocalDate endDate = LocalDate.of(2024, 1, 19);
    roomViewModel.loadAvailableRooms(startDate, endDate);
    assertEquals("The end date is earlier than the start date.", error.get());
  }

  @Test public void if_endDate_is_before_startDate_canClick_is_false()
  {
    LocalDate startDate = LocalDate.of(2024, 1,20);
    LocalDate endDate = LocalDate.of(2024, 1, 19);
    roomViewModel.loadAvailableRooms(startDate, endDate);
    assertFalse(canClick.get());
  }

  @Test public void toggle_returns_To_reservations_initially()
  {
    assertEquals("To reservations", roomViewModel.getToggle());
  }

  @Test public void toggle_returns_To_rooms_after_onToggle()
  {
    roomViewModel.onToggle();
    assertEquals("To rooms", roomViewModel.getToggle());
  }

  @Test public void if_startDate_is_before_endDate_error_is_set_to_nothing_in_loadReservationsInTimeframe()
  {
    LocalDate startDate = LocalDate.of(2024, 1,20);
    LocalDate endDate = LocalDate.of(2024, 1, 21);
    roomViewModel.loadReservationsInTimeframe(startDate, endDate);
    assertEquals("", error.get());
  }

  @Test public void if_startDate_is_before_endDate_canClick_is_set_to_true_in_loadReservationsInTimeframe()
  {
    LocalDate startDate = LocalDate.of(2024, 1,20);
    LocalDate endDate = LocalDate.of(2024, 1, 21);
    roomViewModel.loadReservationsInTimeframe(startDate, endDate);
    assertTrue(canClick.get());
  }

  @Test public void if_startDate_and_endDate_are_null_error_is_set_in_loadReservationsInTimeframe()
  {
    LocalDate startDate = null;
    LocalDate endDate = null;
    roomViewModel.loadReservationsInTimeframe(startDate, endDate);
    assertEquals("Date from or until is empty, please choose a date.", error.get());
  }

  @Test public void if_startDate_and_endDate_are_null_canClick_is_false_in_loadReservationsInTimeframe()
  {
    LocalDate startDate = null;
    LocalDate endDate = null;
    roomViewModel.loadReservationsInTimeframe(startDate, endDate);
    assertFalse(canClick.get());
  }

  @Test public void if_endDate_is_before_startDate_error_is_set_in_loadReservationsInTimeframe()
  {
    LocalDate startDate = LocalDate.of(2024, 1,20);
    LocalDate endDate = LocalDate.of(2024, 1, 19);
    roomViewModel.loadReservationsInTimeframe(startDate, endDate);
    assertEquals("The end date is earlier than the start date.", error.get());
  }

  @Test public void if_endDate_is_before_startDate_canClick_is_false_in_loadReservationsInTimeframe()
  {
    LocalDate startDate = LocalDate.of(2024, 1,20);
    LocalDate endDate = LocalDate.of(2024, 1, 19);
    roomViewModel.loadReservationsInTimeframe(startDate, endDate);
    assertFalse(canClick.get());
  }

  @Test public void error_is_set_to_cat()
  {
    roomViewModel.setError("cat");
    assertEquals("cat", error.get());
  }

  @Test public void if_startDate_is_before_endDate_error_is_set_to_nothing_in_roomSelected()
  {
    LocalDate startDate = LocalDate.of(2024, 1,20);
    LocalDate endDate = LocalDate.of(2024, 1, 21);
    roomViewModel.roomSelected(new Room(1), startDate, endDate);
    assertEquals("", error.get());
  }

  @Test public void if_startDate_and_endDate_are_null_error_is_set_in_roomSelected()
  {
    LocalDate startDate = null;
    LocalDate endDate = null;
    roomViewModel.roomSelected(new Room(1), startDate, endDate);
    assertEquals("Date from or until is empty, please choose a date.", error.get());
  }

  @Test public void if_endDate_is_before_startDate_error_is_set_in_roomSelected()
  {
    LocalDate startDate = LocalDate.of(2024, 1,20);
    LocalDate endDate = LocalDate.of(2024, 1, 19);
    roomViewModel.roomSelected(new Room(1), startDate, endDate);
    assertEquals("The end date is earlier than the start date.", error.get());
  }

  @Test public void cleaningToggle_is_set_to_To_rooms_when_onCleaning_is_used_once()
  {
    assertEquals("Needs cleaning", cleaningToggle.get());
    roomViewModel.onCleaning();
    assertEquals("To rooms", cleaningToggle.get());
  }

  @Test public void cleaningToggle_is_set_to_Needs_cleaning_when_onCleaning_is_used_twice()
  {
    roomViewModel.onCleaning();
    roomViewModel.onCleaning();
    assertEquals("Needs cleaning", cleaningToggle.get());
  }

  @Test public void getCleaningToggle_returns_needs_cleaning_initially()
  {

    assertEquals("Needs cleaning", roomViewModel.getCleaningToggle());
  }

  @Test public void on_AllRooms_event_cleaningToggle_is_set_to_NeedsCleaning()
  {
    roomViewModel.onCleaning();
    assertEquals("To rooms", cleaningToggle.get());
    PropertyChangeEvent event = new PropertyChangeEvent(new Object(),"All Rooms", null, new ArrayList<Room>());

    roomViewModel.propertyChange(event);
    assertEquals("Needs cleaning", cleaningToggle.get());
  }

  @Test public void on_AllRooms_event_propertyChangeSupportEvent_is_fired()
  {
    ArrayList<PropertyChangeEvent> events = new ArrayList<>();
    roomViewModel.addPropertyChangeListener(evt -> {
      events.add(evt);
    });
    PropertyChangeEvent event = new PropertyChangeEvent(new Object(),"All Rooms", null, new ArrayList<Room>());
    roomViewModel.propertyChange(event);
    assertEquals(1, events.size());
    assertEquals(event.getNewValue(), events.get(0).getNewValue());
  }

  @Test public void on_AvailableRooms_propertyChangeEvent_is_fired()
  {
    ArrayList<PropertyChangeEvent> events = new ArrayList<>();
    roomViewModel.addPropertyChangeListener(evt -> {
      events.add(evt);
    });
    PropertyChangeEvent availableRoomsEvent = new PropertyChangeEvent(new Object(),"Available Rooms", null, new ArrayList<Room>());
    roomViewModel.propertyChange(availableRoomsEvent);
    assertEquals(1, events.size());
    assertEquals(availableRoomsEvent.getNewValue(), events.get(0).getNewValue());
  }

  @Test public void on_AllReservations_event_propertyChangeEvent_is_fired()
  {
    ArrayList<PropertyChangeEvent> events = new ArrayList<>();
    roomViewModel.addPropertyChangeListener(evt -> {
      events.add(evt);
    });
    PropertyChangeEvent availableRoomsEvent = new PropertyChangeEvent(new Object(),"All Reservations", null, new ArrayList<Reservation>());
    roomViewModel.propertyChange(availableRoomsEvent);
    assertEquals(1, events.size());
    assertEquals(availableRoomsEvent.getNewValue(), events.get(0).getNewValue());
  }

  @Test public void on_ReservationsForTimePeriod_event_propertyChangeEvent_is_fired()
  {
    ArrayList<PropertyChangeEvent> events = new ArrayList<>();
    roomViewModel.addPropertyChangeListener(evt -> {
      events.add(evt);
    });
    PropertyChangeEvent availableRoomsEvent = new PropertyChangeEvent(new Object(),"Reservations for Time Period", null, new ArrayList<Reservation>());
    roomViewModel.propertyChange(availableRoomsEvent);
    assertEquals(1, events.size());
    assertEquals(availableRoomsEvent.getNewValue(), events.get(0).getNewValue());
  }

  @Test public void on_UpdateReservations_event_if_toggle_equals_ToReservations_no_propertyChangeEvent_is_fired()
  {
    assertEquals("To reservations", toggle.get());
    ArrayList<PropertyChangeEvent> events = new ArrayList<>();
    roomViewModel.addPropertyChangeListener(evt -> {
      events.add(evt);
    });
    PropertyChangeEvent availableRoomsEvent = new PropertyChangeEvent(new Object(),"Update Reservations", null, new ArrayList<Reservation>());
    roomViewModel.propertyChange(availableRoomsEvent);
    assertEquals(0, events.size());
  }

  @Test public void on_UpdateReservations_event_if_toggle_equals_ToRooms_propertyChangeEvent_is_fired()
  {
    roomViewModel.onToggle();
    assertEquals("To rooms", toggle.get());
    ArrayList<PropertyChangeEvent> events = new ArrayList<>();
    roomViewModel.addPropertyChangeListener(evt -> {
      events.add(evt);
    });
    PropertyChangeEvent availableRoomsEvent = new PropertyChangeEvent(new Object(),"Update Reservations", null, new ArrayList<Reservation>());
    roomViewModel.propertyChange(availableRoomsEvent);
    assertEquals(1, events.size());
  }

  @Test public void on_DisplayRoomsForCleaning_event_if_cleaningToggle_equals_ToReservations_no_propertyChangeEvent_is_fired()
  {
    assertEquals("Needs cleaning", cleaningToggle.get());
    ArrayList<PropertyChangeEvent> events = new ArrayList<>();
    roomViewModel.addPropertyChangeListener(evt -> {
      events.add(evt);
    });
    PropertyChangeEvent availableRoomsEvent = new PropertyChangeEvent(new Object(),"Display Rooms For Cleaning", null, new ArrayList<Room>());
    roomViewModel.propertyChange(availableRoomsEvent);
    assertEquals(0, events.size());
  }

  @Test public void on_DisplayRoomsForCleaning_event_if_cleaningToggle_equals_ToRooms_propertyChangeEvent_is_fired()
  {
    roomViewModel.onCleaning();
    assertEquals("To rooms", cleaningToggle.get());
    ArrayList<PropertyChangeEvent> events = new ArrayList<>();
    roomViewModel.addPropertyChangeListener(evt -> {
      events.add(evt);
    });
    PropertyChangeEvent availableRoomsEvent = new PropertyChangeEvent(new Object(),"Display Rooms For Cleaning", null, new ArrayList<Room>());
    roomViewModel.propertyChange(availableRoomsEvent);
    assertEquals(1, events.size());
  }
}
