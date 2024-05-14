package Client;

import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.Date;

public interface HotelClient
{
  void tryLogin(String username, String password) throws IOException;
  void getAllRooms() throws IOException;
  void getRoomsAvailable(Date startDate, Date endDate) throws IOException;
  void addPropertyChangeListener(PropertyChangeListener listener);
  void removePropertyChangeListener(PropertyChangeListener listener);
  void receiveBroadcast(String message);
}
