package Model;

import java.beans.PropertyChangeListener;

public interface HotelModel
{
  void tryLogin(String username, String password);
  void exitClient();
  void addPropertyChangeListener(PropertyChangeListener listener);
  void removePropertyChangeListener(PropertyChangeListener listener);
}
