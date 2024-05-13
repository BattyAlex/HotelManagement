package Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 This is an abstract class that serves as a factory for creating database connections
 */
public abstract class DatabaseHandlerFactory
{
  /**
   * Establishes a connection to the PostgreSQL database
   * @return A connection object if the connection is established sucessfully or null if the connection fails.
   */
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
