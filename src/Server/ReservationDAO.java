package Server;

import java.sql.DriverManager;
import java.sql.SQLException;

public class ReservationDAO extends DatabaseHandlerFactory
{
  private static ReservationDAO instance;
  private ReservationDAO() throws SQLException
  {
    DriverManager.registerDriver(new org.postgresql.Driver());
  }

  public static synchronized ReservationDAO getInstance()
  {
    try
    {
      if (instance == null)
      {
        instance = new ReservationDAO();
      }
      return instance;
    }
    catch (SQLException e)
    {
      e.printStackTrace();
      return null;
    }
  }




}
