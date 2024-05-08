package Model;

public class DateHandler
{
  private int day;
  private int month;
  private int year;

  public DateHandler(int day, int month, int year)
  {
    this.day = day;
    this.month = month;
    this.year = year;
  }

  public int getDay()
  {
    return day;
  }

  public int getMonth()
  {
    return month;
  }

  public int getYear()
  {
    return year;
  }

  public void setDay(int day)
  {
    this.day = day;
  }

  public void setMonth(int month)
  {
    this.month = month;
  }

  public void setYear(int year)
  {
    this.year = year;
  }
  public boolean isLeapYear()
  {
    if((year%4==0 && !(year%100==0)) || year%400==0 )
      return true;
    else
      return false;
  }
  public int daysInMonth()
  {
    if(month == 4 || month == 6 || month == 9 || month == 11)
      return 30;
    else if(month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12)
      return 31;
    else if(month == 2)
      if(isLeapYear())
        return 29;
      else
        return 28;
    else
      return 0;
  }
  public int daysUntil(DateHandler date)
  {
    
  }
}
