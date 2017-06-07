package hello;

import hello.model.Customer;
import hello.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application extends SpringBootServletInitializer {
  public static final Logger log = LoggerFactory.getLogger(Application.class);

  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
    return builder.sources(Application.class);
  }

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Bean
  public CommandLineRunner demo(CustomerRepository repo) {
    return (args -> {
      // save some customers
      repo.save(new Customer("Jack", "Reacher"));
      repo.save(new Customer("Ethan", "Hunt"));
      repo.save(new Customer("Cole", "Trickle"));
      repo.save(new Customer("Charlie", "Babbit"));
      repo.save(new Customer("Bill", "Cage"));

      // fetch all customers
      log.info("Customers found with findAll():");
      log.info("--------------------------------");
      repo.findAll().forEach(c -> log.info(c.toString()));
      log.info("");

      // fetch an individual customer by Id
      log.info("Customer found with findOne(1L):");
      log.info("--------------------------------");
      Customer customer = repo.findOne(1L);
      log.info(customer.toString());
      log.info("");

      // fetch customers by last name
      log.info("Customer found by findByLastName('Trickle')");
      log.info("--------------------------------");
      repo.findByLastName("Trickle").forEach(c -> log.info(c.toString()));
      log.info("");
    });
  }
}
