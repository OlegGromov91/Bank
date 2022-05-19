package bank.core;

import com.data.model.security.user.RoleType;
import com.data.model.security.user.User;
import com.data.model.security.user.UserLogon;
import com.data.repo.UserLogonRepository;
import com.data.repo.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication(scanBasePackages = {"bank.core", "com.data", "com.history", "security", "com.transfer", "com.validation", "com.frod"})
@EnableJpaRepositories(basePackages = {"com.data.repo"})
@EntityScan(basePackages = {"com.data.model"})
@EnableScheduling
@ConfigurationPropertiesScan(basePackages = {"security"})

public class BankApplication {

    public static void main(String[] args) {
        SpringApplication.run(BankApplication.class, args);
    }

    /**
     * Заменинть на liquibase
     */
    @Bean
    CommandLineRunner runner(UserRepository userRepository, UserLogonRepository userLogonRepository) {
        return args ->
        {
            /**
             * Создаем админа логин/пароль - admin admin
             */
            User admin = User.builder()
                    .firstName("Олег")
                    .secondName("Олегов")
                    .middleName("Олегович")
                    .userLogin("admin")
                    .isActive(true)
                    .email("Olegovich@mail.ru")
                    .phoneNumber("12345678910")
                    .secretWord("$2a$08$XCApIjvuqI3lRgFd9WsHle000g2Zw8MjUXIIGxiSsb/7fYZCCt0cm")
                    .build();

            if (userLogonRepository.findById(1L).isEmpty()) {
                userRepository.save(admin);
                User userAdmin = userRepository.findById(1L).get();
                userLogonRepository.save(UserLogon.builder()
                        .userLogin("admin")
                        .passwordHash("$2a$08$ZnxDohVLvWo6w0GMilSFA.StqYOUu8xaOdoywd32.W2ClueuzhkKC")
                        .roleType(RoleType.ADMIN)
                        .user(userAdmin)
                        .build());
            }
            /**
             * Создаем юзера логин/пароль - user user
             */
            User client = User.builder()
                    .firstName("Дмитрий")
                    .secondName("Дмитриев")
                    .middleName("Дмитриевич")
                    .userLogin("user")
                    .secretWord("$2a$08$XCApIjvuqI3lRgFd9WsHle000g2Zw8MjUXIIGxiSsb/7fYZCCt0cm")
                    .isActive(true)
                    .email("Dmitrievich@mail.ru")
                    .phoneNumber("32345678910")
                    .sex("male")
                    .build();

            if (userLogonRepository.findById(2L).isEmpty()) {
                userRepository.save(client);
                User userClient = userRepository.findById(2L).get();
                userLogonRepository.save(UserLogon.builder()
                        .userLogin("user")
                        .passwordHash("$2a$08$SxQtU/.R7b679NFuOWHCOuooBNClFX0q.PQUGCGDalS9ztp/oNRPu")
                        .roleType(RoleType.USER)
                        .user(userClient)
                        .build());

            }
            /**
             * Создаем оператора логин/пароль - oper oper
             */
            if (userLogonRepository.findById(3L).isEmpty()) {
                User operator = User.builder()
                        .firstName("Алексей")
                        .secondName("Алексеев")
                        .middleName("Алексеевич")
                        .userLogin("oper")
                        .isActive(true)
                        .email("Lexa@mail.ru")
                        .phoneNumber("11345672910")
                        .secretWord("$2a$08$XCApIjvuqI3lRgFd9WsHle000g2Zw8MjUXIIGxiSsb/7fYZCCt0cm")
                        .build();

                userRepository.save(operator);

                User userOperator = userRepository.findById(3L).get();

                userLogonRepository.save(UserLogon.builder()
                        .userLogin("oper")
                        .passwordHash("$2a$08$Qgpf7KBUmd8GhMvWsXeld.eoyxHVDU7AhtdRaR3GeLQk8DTpaa.FW")
                        .roleType(RoleType.OPERATOR)
                        .user(userOperator)
                        .build());
            }
        };
    }




}