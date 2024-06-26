package Client;

import Model.Reservation;
import Model.Room;

import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

/**
 * The HotelClient interface defines the methods required for a client that interacts with a hotel management system
 */
public interface  HotelClient
{
  /**
   * Attempts to log in with the provided username and password
   * @param username The username of the user trying to log in
   * @param password The password of the user trying to log in
   * @throws IOException if an IO error occurs during the login attempt
   */
  void tryLogin(String username, String password) throws IOException;
  /**
   * Retrieves all rooms available in the hotel
   * @throws IOException if an IO error occurs while retrieving the rooms
   */
  ArrayList<Room> getAllRooms() throws IOException;
  /**
   * Retrieves the rooms available within the specific date range
   * @param startDate the start date of the availability period
   * @param endDate the end date of the availability period
   * @throws IOException  if an IO error occurs while retrieving the available rooms
   */
  ArrayList<Room> getRoomsAvailable(LocalDate startDate, LocalDate endDate) throws IOException;
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
  /**
   * Gets all reservations and forwards it
   * @throws IOException if an IO error occurs during communication with the server
   */
  void getAllReservations() throws IOException;
  /**
   * Gets all reservations for a specific time period and forwards them
   * @param startDate The start date between which the reservation was made
   * @param endDate The end date between which the reservation was made
   * @throws IOException if an IO error occurs during communication with the server
   */
  void getReservationsInTimeframe(LocalDate startDate, LocalDate endDate) throws IOException;
  /**
   * Returns the room based on the room number
   * @param roomNumber the room number of the room
   * @return the room based on the room number
   * @throws IOException if an IO error occurs during communication with the server
   */
  Room getRoomByRoomNumber(int roomNumber) throws IOException;
  /**
   * Makes or updates a reservation
   * @param reservation The reservation to be made or updated
   * @throws IOException if an IO error occurs during communication with the server
   */
  void makeOrUpdateReservation(Reservation reservation) throws IOException;
  /**
   * Closes the client and any connecting threads
   */
  void close();
  /**
   * Deletes and returns the reservation given
   * @param reservation The reservation to be deleted
   * @return The deleted reservation
   * @throws IOException if an IO error occurs during communication with the server
   */
  Reservation onDelete(Reservation reservation) throws IOException;
  /**
   * Updates the state of the given room
   * @param room The room which needs to be updated
   * @throws IOException if an IO error occurs during communication with the server
   */
  void updateStateOfRoom(Room room) throws IOException;
  /**
   * Gets all rooms which need cleaning
   * @return a list of rooms requiring cleaning
   * @throws IOException if an IO error occurs during communication with the server
   */
  ArrayList<Room> getAllRoomsForCleaning() throws IOException;
}
