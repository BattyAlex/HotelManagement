import Model.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class ReservationTest
{
  private Reservation test;
  @BeforeEach public void setUp()
  {
    LocalDate startDate = LocalDate.of(2024, 5, 22);
    LocalDate endDate = LocalDate.of(2024, 5, 28);
    test = new Reservation(startDate, endDate, new Guest("Sophia", "Justin", "1234123412341234"), new Room("single", 70, 1, "cleaned"), new Staff("admin", ""));
    test.addService(Service.ROOM_SERVICE, 200);
    test.addService(Service.AIRPORT_TRANSPORT, 20);
    test.addService(Service.LUNCH, 30);
  }

  @Test public void totalOfCurrentServicesIs250()
  {
    assertEquals(250, test.getServicesTotal());
  }

  @Test public void currentRoomPriceIs70()
  {
    assertEquals(70, test.getRoom().getPrice());
  }

  @Test public void totalDaysStayedIsSix()
  {
    assertEquals(6, test.getLengthOfStay());
  }

  @Test public void totalForRoomIs420()
  {
    assertEquals(420, test.getTotalForRoom());
  }

  @Test public void totalForStayIs670()
  {
    assertEquals(670, test.getTotalForStay());
  }
}
