package hello;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class BookingService {
  public static final Logger log = LoggerFactory.getLogger(BookingService.class);

  private final JdbcTemplate jdbcTemplate;

  public BookingService(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Transactional
  public void book(String... persons) {
    for (String person: persons) {
      log.info(String.format("Booking %s in a seat...", person));
      jdbcTemplate.update("INSERT INTO bookings(first_name) VALUES (?)", person);
    }
  }

  public List<String> findAllBookings() {
    return jdbcTemplate.query("SELECT first_name FROM bookings",
        (resultSet, rowNum) -> resultSet.getString("first_name"));
  }
}
