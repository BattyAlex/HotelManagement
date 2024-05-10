package Model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

public class HotelModelManager implements HotelModel
{
  private Staff staff;
  private Room room;
  private Reservation reservations;
  private PropertyChangeSupport support;

  public HotelModelManager()
  {
    this.support = new PropertyChangeSupport(this);
  }


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
