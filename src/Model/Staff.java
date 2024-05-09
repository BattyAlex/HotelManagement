package Model;

public class Staff
{
  private final String username;
  private final String password;

  public Staff(String username, String password)
  {
    this.username = username;
    this.password = password;
  }

  public String getUsername()
  {
    return username;
  }

  public String getPassword()
  {
    return password;
  }

}
