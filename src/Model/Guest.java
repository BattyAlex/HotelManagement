package Model;

/**
 * Represents a guest in the hotel management system.
 * This class stores the personal details of the guest.
 */
public class Guest
{
  private String firstName;
  private String lastName;
  private String paymentInfo;
  private int id;

  /**
   * Constructs a new Guest with the specified details
   * @param firstName the first name of the guest
   * @param lastName the last name of the guest
   * @param paymentInfo the payment information of the guest
   */

  public Guest(String firstName, String lastName, String paymentInfo)
  {
    this.firstName = firstName;
    this.lastName = lastName;
    this.paymentInfo = paymentInfo;
    id = -1;
  }

  public Guest(String firstName, String lastName, String paymentInfo, int id)
  {
    this.firstName = firstName;
    this.lastName = lastName;
    this.paymentInfo = paymentInfo;
    this.id = id;
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
   * Returns payment information of the guest.
   * @return the payment information of the guest.
   */
  public String getPaymentInfo()
  {
    return paymentInfo;
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
   * Sets the payment information of the guest.
   * @param paymentInfo the new payment information to set.
   */
  public void setPaymentInfo(String paymentInfo)
  {
    this.paymentInfo = paymentInfo;
  }

  public int getId()
  {
    return id;
  }

  public void setId(int id)
  {
    this.id = id;
  }


}
