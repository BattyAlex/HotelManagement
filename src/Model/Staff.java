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
