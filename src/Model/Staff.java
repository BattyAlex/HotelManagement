package Model;

import java.io.Serializable;

/**
 * Represents a staff member in a hotel management system
 */
public class Staff implements Serializable
{
  private final String username;
  private final String password;

  /**
   * Constructs a new staff member with specified username and password
   * @param username The username for the staff member
   * @param password The password for the staff member
   */
  public Staff(String username, String password)
  {
    this.username = username;
    this.password = password;
  }

  /**
   * Retrieves the username of the staff member
   * @return The username of the staff member
   */
  public String getUsername()
  {
    return username;
  }

  /**
   * Retrieves the password of the staff member
   * @return The password of the staff member
   */
  public String getPassword()
  {
    return password;
  }

  /**
   * Compares this Staff object with another object for equality
   * The comparison is based on the username and password of the staff members
   * @param obj The object to compare the staff with
   * @return True if the given object represents the Staff equivallent to this staff, or otherwise, false.
   */
  public boolean equals(Object obj)
  {
    if(obj == null || obj.getClass() != getClass())
    {
      return false;
    }
    Staff other = (Staff) obj;
    return other.password.equals(this.password) && other.username.equals(this.username);
  }

}
