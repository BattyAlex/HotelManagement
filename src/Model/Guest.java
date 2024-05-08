package Model;

public class Guest
{
  private String firstName;
  private String lastName;
  private String contact;

  public Guest(String firstName, String lastName, String contact)
  {
    this.firstName = firstName;
    this.lastName = lastName;
    this.contact = contact;
  }

  public String getFirstName()
  {
    return firstName;
  }

  public String getLastName()
  {
    return lastName;
  }

  public String getContact()
  {
    return contact;
  }

  public void setFirstName(String firstName)
  {
    this.firstName = firstName;
  }

  public void setLastName(String lastName)
  {
    this.lastName = lastName;
  }

  public void setContact(String contact)
  {
    this.contact = contact;
  }


}
