package Model;

import Client.HotelClient;
import Server.UserDAO;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class HotelModelManager implements HotelModel
{
  private Staff staff;
  private PropertyChangeSupport support;
  private HotelClient client;

  /**
   * Manages the model login for the hotel and handles operations such as user login and notifications for changes in property value
   */
  public HotelModelManager(HotelClient client)
  {
    this.client = client;
    this.support = new PropertyChangeSupport(this);
  }

  /**
   * Attempts to log in a user with the specified username and password.
   * Notifies listeners of the result of the login attempt.
   * @param username the username of the staff member trying to log in.
   * @param password the password of the staff member trying to log in.
   */

  @Override public void tryLogin(String username, String password)
  {
    staff = new Staff(username, password);
    Staff loginRequest = UserDAO.getInstance().getStaffBasedOnUsername(username);
    if(loginRequest == null)
    {
      support.firePropertyChange("Username invalid", username, null);
    }
    else if(staff.equals(loginRequest))
    {
      support.firePropertyChange("Login Successful", null, null);
    }
    else
    {
      support.firePropertyChange("Login failed", username, null);
    }
  }

  @Override public void exitClient()
  {
  }

  /**
   * Adds a PropertyChangeListener to the list of listeners to be notified of property changes
   * @param listener the PropertyChangeListener to be added.
   */

  @Override public void addPropertyChangeListener(
      PropertyChangeListener listener)
  {
    support.addPropertyChangeListener(listener);
  }

  /**
   * Removes a PropertyChangeListener to the list of listeners.
   * @param listener the PropertyChangeListener to be removed.
   */

  @Override public void removePropertyChangeListener(
      PropertyChangeListener listener)
  {
    support.removePropertyChangeListener(listener);
  }

  /**
   * Method is called when a PropertyChangeEvent is about to get fired.
   * @param evt the PropertyChangeEvent object containing the event details.??
   */

  public void propertyChange(PropertyChangeEvent evt)
  {

  }
}
