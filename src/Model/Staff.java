package Model;

public class Staff
{
  private String firstName;
  private String lastName;
  private String id;

  public Staff(String firstName, String lastName, String id)
  {
    this.firstName = firstName;
    this.lastName = lastName;
    this.id = id;
  }

  public String getFirstName()
  {
    return firstName;
  }

  public String getLastName()
  {
    return lastName;
  }

  public String getId()
  {
    return id;
  }

  public void setFirstName(String firstName)
  {
    this.firstName = firstName;
  }

  public void setLastName(String lastName)
  {
    this.lastName = lastName;
  }

  public void setId(String id)
  {
    this.id = id;
  }
}
