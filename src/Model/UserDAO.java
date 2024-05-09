package Model;

import java.sql.DriverManager;
import java.sql.SQLException;

public class UserDAO extends DatabaseHandlerFactory
{
  public UserDAO() throws SQLException
  {
    DriverManager.registerDriver(new org.postgresql.Driver());
  }

  public Staff getStaffBasedOnUsername(String username)
  {
    return null;
  }
}
