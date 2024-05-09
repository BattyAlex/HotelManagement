package Model;

import java.beans.PropertyChangeListener;

public interface StaffModel
{
  void tryLogin(String username, String password);
  void exitClient();
  void addPropertyChangeListener(PropertyChangeListener listener);
  void removePropertyChangeListener(PropertyChangeListener listener);
}
