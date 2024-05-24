import Model.*;
import ViewModel.ReservationViewModel;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class ReservationViewModelTesting
{
  private HotelModel model;
  private ReservationViewModel reservationViewModel;
  private PropertyChangeListener listener;
  private StringProperty firstName;
  private StringProperty lastName;
  private StringProperty cardInfo;
  private StringProperty amenities;
  private StringProperty error;
  private BooleanProperty breakfast;
  private BooleanProperty lunch;
  private BooleanProperty dinner;
  private BooleanProperty airportTrans;
  private BooleanProperty wellness;
  private BooleanProperty roomService;
  private StringProperty noOfGuests;
  private StringProperty reservationId;
  private BooleanProperty canClick;
  private BooleanProperty delete;
  private BooleanProperty checkOut;
  @BeforeEach public void setUp()
  {
    model = Mockito.mock(HotelModel.class);
    reservationViewModel = new ReservationViewModel(model);
    listener = Mockito.mock(PropertyChangeListener.class);
    reservationViewModel.addPropertyChangeListener(listener);
    reservationViewModel.removePropertyChangeListener(listener);
    reservationViewModel.addPropertyChangeListener(listener);

    firstName = new SimpleStringProperty();
    lastName = new SimpleStringProperty();
    cardInfo = new SimpleStringProperty();
    amenities = new SimpleStringProperty();
    error = new SimpleStringProperty();
    breakfast = new SimpleBooleanProperty();
    lunch = new SimpleBooleanProperty();
    dinner = new SimpleBooleanProperty();
    airportTrans  = new SimpleBooleanProperty();
    wellness  = new SimpleBooleanProperty();
    roomService = new SimpleBooleanProperty();
    noOfGuests  = new SimpleStringProperty();
    reservationId  = new SimpleStringProperty();
    canClick = new SimpleBooleanProperty();
    delete  = new SimpleBooleanProperty();
    checkOut  = new SimpleBooleanProperty();

    reservationViewModel.bindFirstName(firstName);
    reservationViewModel.bindLastName(lastName);
    reservationViewModel.bindCard(cardInfo);
    reservationViewModel.bindAmenities(amenities);
    reservationViewModel.bindError(error);
    reservationViewModel.bindBreakfast(breakfast);
    reservationViewModel.bindLunch(lunch);
    reservationViewModel.bindDinner(dinner);
    reservationViewModel.bindAirportTrans(airportTrans);
    reservationViewModel.bindWellness(wellness);
    reservationViewModel.bindRoomService(roomService);
    reservationViewModel.bindNoOfGuests(noOfGuests);
    reservationViewModel.bindReservationId(reservationId);
    reservationViewModel.bindCanClick(canClick);
    reservationViewModel.bindDelete(delete);
    reservationViewModel.bindCheckOut(checkOut);
  }

  @Test public void newly_created_reservationViewModel_is_not_null()
  {
    assertNotNull(reservationViewModel);
  }

  @Test public void initial_values_are_set_properly()
  {
    assertEquals("", firstName.get());
    assertEquals("", lastName.get());
    assertEquals("", cardInfo.get());
    assertEquals("", amenities.get());
    assertEquals("", error.get());
    assertEquals("", firstName.get());
    assertEquals("", noOfGuests.get());
    assertEquals("", reservationId.get());
    assertFalse(breakfast.get());
    assertFalse(lunch.get());
    assertFalse(dinner.get());
    assertFalse(airportTrans.get());
    assertFalse(wellness.get());
    assertFalse(roomService.get());
    assertFalse(delete.get());
    assertFalse(checkOut.get());
    assertTrue(canClick.get());
  }

  @Test public void set_error_gives_back_same_result()
  {
    assertEquals("", error.get());
    reservationViewModel.setError("hello there");
    assertEquals("hello there", error.get());
  }

  @Test public void setting_amenities_to_cat_changes_amenities()
  {
    assertEquals("", amenities.get());
    reservationViewModel.setAmenities("cat");
    assertEquals("cat", amenities.get());
  }

  @Test public void for_loadAvailableRooms_if_dates_are_null_sets_error()
  {
    assertEquals("", error.get());
    reservationViewModel.loadAvailableRooms(null, null);
    assertEquals("Date is empty, please choose a date.", error.get());
  }

  @Test public void for_loadAvailableRooms_if_endDate_before_startDate_sets_error()
  {
    assertEquals("", error.get());
    LocalDate startDate = LocalDate.of(2024, 1, 20);
    LocalDate endDate = LocalDate.of(2024, 1, 19);
    reservationViewModel.loadAvailableRooms(startDate, endDate);
    assertEquals("The end date is earlier than the start date.", error.get());
  }

  @Test public void for_loadAvailableRooms_if_startDate_and_endDate_on_same_day_sets_error()
  {
    assertEquals("", error.get());
    LocalDate startDate = LocalDate.of(2024, 1, 20);
    LocalDate endDate = LocalDate.of(2024, 1, 20);
    reservationViewModel.loadAvailableRooms(startDate, endDate);
    assertEquals("The same dates have been selected.", error.get());
  }

  @Test public void for_loadAvailableRooms_if_startDate_before_endDate_error_remains_empty()
  {
    assertEquals("", error.get());
    LocalDate startDate = LocalDate.of(2024, 1, 19);
    LocalDate endDate = LocalDate.of(2024, 1, 20);
    reservationViewModel.loadAvailableRooms(startDate, endDate);
    assertEquals("", error.get());
  }

  @Test public void for_loadAvailableRooms_if_dates_are_null_canClick_is_false()
  {
    reservationViewModel.loadAvailableRooms(null, null);
    assertFalse(canClick.get());
  }

  @Test public void for_loadAvailableRooms_if_endDate_before_startDate_canClick_is_false()
  {
    LocalDate startDate = LocalDate.of(2024, 1, 20);
    LocalDate endDate = LocalDate.of(2024, 1, 19);
    reservationViewModel.loadAvailableRooms(startDate, endDate);
    assertFalse(canClick.get());
  }

  @Test public void for_loadAvailableRooms_if_startDate_and_endDate_on_same_day_canClick_is_false()
  {
    LocalDate startDate = LocalDate.of(2024, 1, 20);
    LocalDate endDate = LocalDate.of(2024, 1, 20);
    reservationViewModel.loadAvailableRooms(startDate, endDate);
    assertFalse(canClick.get());
  }

  @Test public void for_loadAvailableRooms_if_startDate_before_endDate_canClick_is_false()
  {
    LocalDate startDate = LocalDate.of(2024, 1, 19);
    LocalDate endDate = LocalDate.of(2024, 1, 20);
    reservationViewModel.loadAvailableRooms(startDate, endDate);
    assertTrue(canClick.get());
  }

  @Test public void for_loadAvailableRooms_if_startDate_before_endDate_model_method_fired()
  {
    LocalDate startDate = LocalDate.of(2024, 1, 19);
    LocalDate endDate = LocalDate.of(2024, 1, 20);
    reservationViewModel.loadAvailableRooms(startDate, endDate);
    Mockito.verify(model).getAvailableRooms(startDate, endDate);
  }

  @Test public void when_room_selected_for_Room_error_is_set()
  {
    reservationViewModel.setError("cat");
    assertEquals("cat", error.get());
    Room room = new Room("single", 120, 1, "cleaned");
    LocalDate startDate = LocalDate.of(2024, 1, 19);
    LocalDate endDate = LocalDate.of(2024, 1, 20);

    reservationViewModel.roomSelected(room, startDate, endDate);
    assertEquals("", error.get());
  }

  @Test public void when_room_selected_for_roomNumber_error_is_set()
  {
    reservationViewModel.setError("cat");
    assertEquals("cat", error.get());
    int roomNumber = 1;
    LocalDate startDate = LocalDate.of(2024, 1, 19);
    LocalDate endDate = LocalDate.of(2024, 1, 20);

    reservationViewModel.roomSelected(1, startDate, endDate);
    assertEquals("", error.get());
  }

  @Test public void when_room_selected_for_Room_model_method_is_fired()
  {
    reservationViewModel.setError("cat");
    assertEquals("cat", error.get());
    Room room = new Room("single", 120, 1, "cleaned");
    LocalDate startDate = LocalDate.of(2024, 1, 19);
    LocalDate endDate = LocalDate.of(2024, 1, 20);

    reservationViewModel.roomSelected(room, startDate, endDate);
    Mockito.verify(model).roomSelected(room, startDate, endDate);
  }

  @Test public void when_room_selected_for_roomNumber_method_is_fired()
  {
    reservationViewModel.setError("cat");
    assertEquals("cat", error.get());
    int roomNumber = 1;
    LocalDate startDate = LocalDate.of(2024, 1, 19);
    LocalDate endDate = LocalDate.of(2024, 1, 20);

    reservationViewModel.roomSelected(roomNumber, startDate, endDate);
    Mockito.verify(model).roomSelected(roomNumber, startDate, endDate);
  }

  @Test public void if_firstName_is_empty_propertyChangeEvent_is_fired()
  {
    assertEquals("", firstName.get());
    lastName.set("hi");
    cardInfo.set("hello");
    ArrayList<PropertyChangeEvent> events = new ArrayList<>();
    reservationViewModel.addPropertyChangeListener(evt -> {
      events.add(evt);
    });
    Room room = new Room("single", 120, 1, "cleaned");
    LocalDate startDate = LocalDate.of(2024, 1, 19);
    LocalDate endDate = LocalDate.of(2024, 1, 20);

    reservationViewModel.onConfirm(room, startDate, endDate);
    assertEquals(1, events.size());
  }

  @Test public void if_lastName_is_empty_propertyChangeEvent_is_fired()
  {
    assertEquals("", lastName.get());
    firstName.set("hi");
    cardInfo.set("hello");
    ArrayList<PropertyChangeEvent> events = new ArrayList<>();
    reservationViewModel.addPropertyChangeListener(evt -> {
      events.add(evt);
    });
    Room room = new Room("single", 120, 1, "cleaned");
    LocalDate startDate = LocalDate.of(2024, 1, 19);
    LocalDate endDate = LocalDate.of(2024, 1, 20);

    reservationViewModel.onConfirm(room, startDate, endDate);
    assertEquals(1, events.size());
  }

  @Test public void if_cardInfo_is_empty_propertyChangeEvent_is_fired()
  {
    assertEquals("", cardInfo.get());
    lastName.set("hi");
    firstName.set("hello");
    ArrayList<PropertyChangeEvent> events = new ArrayList<>();
    reservationViewModel.addPropertyChangeListener(evt -> {
      events.add(evt);
    });
    Room room = new Room("single", 120, 1, "cleaned");
    LocalDate startDate = LocalDate.of(2024, 1, 19);
    LocalDate endDate = LocalDate.of(2024, 1, 20);

    reservationViewModel.onConfirm(room, startDate, endDate);
    assertEquals(1, events.size());
  }

  @Test public void if_numOfGuests_is_not_int_propertyChangeEvent_is_fired()
  {
    cardInfo.set("1234123412341234");
    lastName.set("hi");
    firstName.set("hello");
    noOfGuests.set("wrong");
    ArrayList<PropertyChangeEvent> events = new ArrayList<>();
    reservationViewModel.addPropertyChangeListener(evt -> {
      events.add(evt);
    });
    Room room = new Room("single", 120, 1, "cleaned");
    LocalDate startDate = LocalDate.of(2024, 1, 19);
    LocalDate endDate = LocalDate.of(2024, 1, 20);

    reservationViewModel.onConfirm(room, startDate, endDate);
    assertEquals(1, events.size());
  }

  @Test public void onCancel_resets_values_to_empty_strings()
  {
    cardInfo.set("1234123412341234");
    lastName.set("hi");
    firstName.set("hello");
    noOfGuests.set("3");
    assertEquals("1234123412341234", cardInfo.get());
    assertEquals("hi", lastName.get());
    assertEquals("hello", firstName.get());
    assertEquals("3", noOfGuests.get());
    reservationViewModel.onCancel();
    assertEquals("", cardInfo.get());
    assertEquals("", lastName.get());
    assertEquals("", firstName.get());
    assertEquals("", noOfGuests.get());
  }

  @Test public void onDelete_calls_model_method_onDelete()
  {
    Room room = new Room("single", 120, 1, "cleaned");
    LocalDate startDate = LocalDate.of(2024, 1, 19);
    LocalDate endDate = LocalDate.of(2024, 1, 20);
    Reservation reservation = new Reservation(startDate, endDate, new Guest("hi", "cat", "1234123412341234"), room, new Staff("", ""));
    reservation.setReservationId(2);
    reservationViewModel.onDelete(reservation);
    Mockito.verify(model).onDelete(reservation);
  }

  @Test public void onCancel_calls_model_method_checkOut()
  {
    Room room = new Room("single", 120, 1, "cleaned");
    LocalDate startDate = LocalDate.of(2024, 1, 19);
    LocalDate endDate = LocalDate.of(2024, 1, 20);
    Reservation reservation = new Reservation(startDate, endDate, new Guest("hi", "cat", "1234123412341234"), room, new Staff("", ""));
    reservation.setReservationId(2);
    reservationViewModel.checkOut(reservation);
    Mockito.verify(model).checkOut(reservation.getRoom());
  }

  @Test public void on_datesChanged_canClick_set_to_false()
  {
    assertTrue(canClick.get());
    reservationViewModel.datesChanged();
    assertFalse(canClick.get());
  }

  @Test public void on_datesChanged_delete_and_checkOut_set_to_true()
  {
    assertFalse(delete.get());
    assertFalse(checkOut.get());
    reservationViewModel.datesChanged();
    assertTrue(delete.get());
    assertTrue(checkOut.get());
  }

  @Test public void on_DisplayRoomSelected_fields_are_set()
  {
    Room room = new Room("single", 120, 1, "cleaned");
    PropertyChangeEvent event = new PropertyChangeEvent(new Object(), "Display Room Selected", room, null);
    reservationViewModel.propertyChange(event);
    assertEquals(room.toString(), amenities.get());
    assertTrue(delete.get());
    assertTrue(checkOut.get());
  }

  @Test public void on_DisplayRoomSelected_propertyChangeEvent_is_fired()
  {
    ArrayList<PropertyChangeEvent> events = new ArrayList<>();
    reservationViewModel.addPropertyChangeListener(evt -> {
      events.add(evt);
    });
    Room room = new Room("single", 120, 1, "cleaned");
    PropertyChangeEvent event = new PropertyChangeEvent(new Object(), "Display Room Selected", room, null);
    reservationViewModel.propertyChange(event);

    assertEquals(1, events.size());
  }

  @Test public void on_DisplayDatesForSelectedRoom_canClick_is_set_true()
  {
    LocalDate startDate = LocalDate.of(2024, 1, 19);
    LocalDate endDate = LocalDate.of(2024, 1, 20);
    PropertyChangeEvent event = new PropertyChangeEvent(new Object(), "Display Dates for Selected Room", startDate, endDate);
    reservationViewModel.datesChanged();
    assertFalse(canClick.get());
    reservationViewModel.propertyChange(event);
    assertTrue(canClick.get());
  }

  @Test public void on_DisplayDatesForSelectedRoom_propertyChangeEvent_is_fired()
  {
    LocalDate startDate = LocalDate.of(2024, 1, 19);
    LocalDate endDate = LocalDate.of(2024, 1, 20);
    PropertyChangeEvent event = new PropertyChangeEvent(new Object(), "Display Dates for Selected Room", startDate, endDate);
    ArrayList<PropertyChangeEvent> events = new ArrayList<>();
    reservationViewModel.addPropertyChangeListener(evt -> {
      events.add(evt);
    });
    reservationViewModel.propertyChange(event);
    assertEquals(1, events.size());
  }

  @Test public void on_GettingAllAvailableRooms_propertyChangeEvent_is_fired()
  {
    ArrayList<PropertyChangeEvent> events = new ArrayList<>();
    reservationViewModel.addPropertyChangeListener(evt -> {
      events.add(evt);
    });
    PropertyChangeEvent event = new PropertyChangeEvent(new Object(), "Getting All Available Rooms", null, new ArrayList<Room>());
    reservationViewModel.propertyChange(event);

    assertEquals(1, events.size());
  }

  @Test public void on_DisplayReservationSelected_propertyChangeEvent_is_fired()
  {
    ArrayList<PropertyChangeEvent> events = new ArrayList<>();
    reservationViewModel.addPropertyChangeListener(evt -> {
      events.add(evt);
    });
    LocalDate startDate = LocalDate.of(2024, 1, 19);
    LocalDate endDate = LocalDate.of(2024, 1, 20);
    Room room = new Room("single", 120, 1, "cleaned");
    Reservation reservation = new Reservation(startDate, endDate, new Guest("hi", "cat", "1234123412341234"), room, new Staff("", ""));
    PropertyChangeEvent event = new PropertyChangeEvent(new Object(), "Display Reservation Selected", reservation, null);
    reservationViewModel.propertyChange(event);

    assertEquals(1, events.size());
  }

  @Test public void on_UpdateReservations_propertyChangeEvent_is_fired()
  {
    ArrayList<PropertyChangeEvent> events = new ArrayList<>();
    reservationViewModel.addPropertyChangeListener(evt -> {
      events.add(evt);
    });
    LocalDate startDate = LocalDate.of(2024, 1, 19);
    LocalDate endDate = LocalDate.of(2024, 1, 20);
    Room room = new Room("single", 120, 1, "cleaned");
    Reservation reservation = new Reservation(startDate, endDate, new Guest("hi", "cat", "1234123412341234"), room, new Staff("", ""));
    PropertyChangeEvent event = new PropertyChangeEvent(new Object(), "Update Reservations", null, reservation);

    reservationViewModel.propertyChange(event);
    assertEquals(1, events.size());
  }
}
