package Model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.rmi.RemoteException;

public class StaffModelManager implements StaffModel
{
  private Staff staff;
  private PropertyChangeSupport support;


  @Override public void tryLogin(String username, String password)
  {

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
