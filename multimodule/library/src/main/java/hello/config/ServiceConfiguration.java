package hello.config;

import hello.service.Service;
import hello.service.ServiceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(ServiceProperties.class)
public class ServiceConfiguration {
  @Bean
  public Service service(ServiceProperties props) {
    return new Service(props.getMessage());
  }

}
