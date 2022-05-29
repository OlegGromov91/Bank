package bank.core.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;



@Configuration
@EnableJpaRepositories(basePackages = {"bank.data"})
@EntityScan(basePackages = {"bank.data"})
public class DataConfig {
}
