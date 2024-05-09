package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class DatabaseHandlerFactory
{
  public Connection establishConnection()
  {
    try
    {
      return DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres?currentSchema=hotelmanagementsystem", "postgres", "Why1sthisn33ded");
    }
    catch (SQLException e)
    {
      System.out.println("Connection failed. Try a different password perhaps.");
      return null;
    }
  }
}
