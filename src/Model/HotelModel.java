package Model;

import java.beans.PropertyChangeListener;
import java.time.LocalDate;
import java.util.Date;

/**
 * This interface defines the functionality for a hotel management system model
 */
public interface HotelModel
{
  /**
   * Attempts to log in a user with the provided username and password
   * @param username The username of the user attempting to log in
   * @param password The password of the user attempting to log in
   */
  void tryLogin(String username, String password);
  /**
   * Exits the client session and performs any necessary clean up operations
   */
  void exitClient();
  /**
   * Adds the property change listener to the model
   * Listeners are notified of changes to properties within the hotel model
   * @param listener The property change listener to add
   */
  void addPropertyChangeListener(PropertyChangeListener listener);
  /**
   * Removes the property change listener from the model
   * @param listener The property change listener to remove.
   */
  void removePropertyChangeListener(PropertyChangeListener listener);
  void loadAllRooms();
  void loadAvailableRooms(LocalDate startDate, LocalDate endDate);
  void loadAllReservations();
  void loadReservationsInTimeframe(LocalDate startDate, LocalDate endDate);
  void roomSelected(Room room, LocalDate startDate, LocalDate endDate);
  void roomSelected(int roomNumber, LocalDate startDate, LocalDate endDate);
  void getAvailableRooms(LocalDate startDate, LocalDate endDate);
  void makeOrUpdateReservation(Reservation reservation);
  void reservationSelected(Reservation selected);
  void getRoomsForCleaning();
  void onDelete(Reservation reservation);
  void checkOut(Room room);
}
