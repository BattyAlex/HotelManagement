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
  /**
   * Gets all the rooms and sends it forward
   */
  void loadAllRooms();
  /**
   * Gets all rooms between a specific timeframe and sends it forward
   * @param startDate The start date from which the room should be available
   * @param endDate The end date till which the room should be available
   */
  void loadAvailableRooms(LocalDate startDate, LocalDate endDate);
  /**
   * Gets all reservations and sends it forward
   */
  void loadAllReservations();
  /**
   * Gets all reservations between a specific time frame
   * @param startDate The start date between which the reservation should be made for
   * @param endDate The end date between which the reservation should be made for
   */
  void loadReservationsInTimeframe(LocalDate startDate, LocalDate endDate);
  /**
   * Sends forward the selected room with all information which is available for a certain period
   * @param room The room which was selected
   * @param startDate The start date from which the room should be available
   * @param endDate The end date till which the room should be available
   */
  void roomSelected(Room room, LocalDate startDate, LocalDate endDate);
  /**
   * Sends forward the selected room based on the room number which is available for a certain period
   * @param roomNumber The room number of the room which was selected
   * @param startDate The start date from which the room should be available
   * @param endDate The end date till which the room should be available
   */
  void roomSelected(int roomNumber, LocalDate startDate, LocalDate endDate);
  /**
   * Gets available rooms for a certain time period and sends it forward
   * @param startDate The start date from which the rooms are available
   * @param endDate The end date till which the rooms should be available
   */
  void getAvailableRooms(LocalDate startDate, LocalDate endDate);
  /**
   * Makes or updates a reservation based on whether it already exists or not
   * @param reservation The reservation that needs to be made or updated
   */
  void makeOrUpdateReservation(Reservation reservation);
  /**
   * Sends forward the selected reservation
   * @param selected The reservation selected
   */
  void reservationSelected(Reservation selected);
  /**
   * Gets all the rooms needing cleaning and sends them forward
   */
  void getRoomsForCleaning();
  /**
   * Deletes the reservation
   * @param reservation The reservation that needs to be deleted
   */
  void onDelete(Reservation reservation);
  /**
   * Changes the state for the room from which the guest is checking out
   * @param room The room from which the guest checks out
   */
  void checkOut(Room room);
  /**
   * Gets the room that needs cleaning and sends it forward
   * @param room The room that was selected
   */
  void selectRoomToClean(Room room);
}
