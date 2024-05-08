package Model;

import java.time.LocalDate;

public class test
{
  public static void main(String[] args)
  {
    LocalDate one = LocalDate.of(2024, 1,1);
    LocalDate two = LocalDate.of(2024,5,20);

    System.out.println(two.compareTo(one));
  }
}
