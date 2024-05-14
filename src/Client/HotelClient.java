package Client;

import java.beans.PropertyChangeListener;
import java.io.IOException;

public interface HotelClient
{
  void tryLogin(String username, String password) throws IOException;
  void getAllRooms() throws IOException;
  void addPropertyChangeListener(PropertyChangeListener listener);
  void removePropertyChangeListener(PropertyChangeListener listener);
  void receiveBroadcast(String message);
}
