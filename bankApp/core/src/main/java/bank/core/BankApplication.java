package bank.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication(scanBasePackages = {"bank.core", "com.data", "com.history", "security", "com.transfer", "com.validation", "com.frod"})
@EnableJpaRepositories(basePackages = {"com.data.repo"})
@EntityScan(basePackages = {"com.data.model"})
@EnableScheduling
@ConfigurationPropertiesScan(basePackages = {"security"})
@PropertySources({
        @PropertySource(value = {"classpath:security.properties"}, encoding = "UTF-8"),
        @PropertySource(value = {"classpath:data.properties"}, encoding = "UTF-8"),
        @PropertySource(value = {"classpath:application.properties"}, encoding = "UTF-8"),
})
public class BankApplication {

    public static void main(String[] args) {
        SpringApplication.run(BankApplication.class, args);
    }


}
