package hello;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class AppRunner implements CommandLineRunner {

  private static final Logger log = LoggerFactory.getLogger(AppRunner.class);
  private final BookingService bookingService;

  public AppRunner(BookingService bookingService) {
    this.bookingService = bookingService;
  }

  @Override
  public void run(String... args) throws Exception {
    bookingService.book("Alice", "Bob", "Carol");
    Assert.isTrue(bookingService.findAllBookings().size() == 3,
        "First booking should work without problem");

    log.info("Alice, Bob and Carol have been booked");

    try {
      bookingService.book("Chris", "Samuel");
    } catch (RuntimeException e) {
      log.info("v--- The following exception is expected because 'Samuel' is too big for the db. ---v");
      log.error(e.getMessage());
    }

    bookingService.findAllBookings().forEach(booking -> log.info("So far, person {} is booked.", booking));
    log.info("You should not see Chris or Samuel in the bookings list. " +
        "Samuel violated DB constraints and Chris was rolled back in the same transaction");

    try {
      bookingService.book("Buddy", null);
    } catch (RuntimeException e) {
      log.info("v--- The following exception is expected because 'null' is not valid for the DB. ---v");
      log.error(e.getMessage());
    }

    bookingService.findAllBookings().forEach(booking -> log.info("So far, person {} is booked.", booking));
    log.info("You shouldn't see Buddy or 'null' in the bookings list as 'null' violated DB constraint and " +
        "Buddy was rolled back in the same transaction");
    Assert.isTrue(bookingService.findAllBookings().size() == 3,
        "'null' should have triggered a rollback");
  }
}
