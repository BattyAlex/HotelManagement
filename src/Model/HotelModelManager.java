package Model;

import Server.UserDAO;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class HotelModelManager implements HotelModel
{
  private Staff staff;
  private PropertyChangeSupport support;

  public HotelModelManager()
  {
    this.support = new PropertyChangeSupport(this);
  }


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

  @Override public void addPropertyChangeListener(
      PropertyChangeListener listener)
  {
    support.addPropertyChangeListener(listener);
  }

  @Override public void removePropertyChangeListener(
      PropertyChangeListener listener)
  {
    support.removePropertyChangeListener(listener);
  }
  public void propertyChange(PropertyChangeEvent evt)
  {

  }
}
