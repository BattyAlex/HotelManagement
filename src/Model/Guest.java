package Model;

/**
 * Represents a guest in the hotel management system.
 * This class stores the personal details of the guest.
 */
public class Guest
{
  private String firstName;
  private String lastName;
  private String contact;

  /**
   * Constructs a new Guest with the specified details
   * @param firstName the first name of the guest
   * @param lastName the last name of the guest
   * @param contact the contact information of the guest
   */
  public Guest(String firstName, String lastName, String contact)
  {
    this.firstName = firstName;
    this.lastName = lastName;
    this.contact = contact;
  }

  /**
   * Returns first name of the guest.
   * @return the first name of the guest.
   */
  public String getFirstName()
  {
    return firstName;
  }

  /**
   * Returns last name of the guest.
   * @return the last name of the guest.
   */
  public String getLastName()
  {
    return lastName;
  }

  /**
   * Returns contact information of the guest.
   * @return the contact information of the guest.
   */
  public String getContact()
  {
    return contact;
  }

  /**
   * Sets the first name of the guest.
   * @param firstName the new first name to set.
   */
  public void setFirstName(String firstName)
  {
    this.firstName = firstName;
  }

  /**
   * Sets the last name of the guest.
   * @param lastName the new last name to set.
   */
  public void setLastName(String lastName)
  {
    this.lastName = lastName;
  }

  /**
   * Sets the contact information of the guest.
   * @param contact the new contact to set.
   */
  public void setContact(String contact)
  {
    this.contact = contact;
  }


}
