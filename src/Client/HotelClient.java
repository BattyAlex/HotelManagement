package Client;

import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.Date;

public interface  HotelClient
/**
 * The HotelClient interface defines the methods required for a client that interacts witha hotel management system
 */
{
  /**
   * Attempts to log in with the provided username and password
   * @param username The username of the user trying to log in
   * @param password The password of the user trying to llog in
   * @throws IOException if an IO error occurs during the login attempt
   */
  void tryLogin(String username, String password) throws IOException;
  /**
   * Retrieves all rooms available in the hotel
   * @throws IOException if an IO error occurs while retrieving the rooms
   */
  void getAllRooms() throws IOException;
  /**
   * Retrieves the rooms available within the specific date range
   * @param startDate the start date of the availability period
   * @param endDate the end date of the availability period
   * @throws IOException  if an IO error occurs while retrieving the available rooms
   */
  void getRoomsAvailable(Date startDate, Date endDate) throws IOException;
  /**
   * Adds a property change listener to the client
   * @param listener the listener to be added
   */
  void addPropertyChangeListener(PropertyChangeListener listener);
  /**
   * Removes a property change listener from the client
   * @param listener the listener to be removed
   */
  void removePropertyChangeListener(PropertyChangeListener listener);
  /**
   * Receives a broadcast message and processes it
   * @param message the message received in the broadcast
   */
  void receiveBroadcast(String message);
}
