package ru.sberbank.local;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import ru.sberbank.local.model.security.user.RoleType;
import ru.sberbank.local.model.security.user.User;
import ru.sberbank.local.model.security.user.UserLogon;
import ru.sberbank.local.repo.UserLogonRepository;
import ru.sberbank.local.repo.UserRepository;

import java.util.Arrays;
import java.util.Collections;

@SpringBootApplication
public class TerminalDiplomProjectApplication {

    @Autowired
    UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(TerminalDiplomProjectApplication.class, args);
    }


    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
        corsConfiguration.setAllowedHeaders(Arrays.asList("Origin", "Access-Control-Allow-Origin", "Content-Type",
                "Accept", "Authorization", "Origin, Accept", "X-Requested-With",
                "Access-Control-Request-Method", "Access-Control-Request-Headers"));
        corsConfiguration.setExposedHeaders(Arrays.asList("Origin", "Content-Type", "Accept", "Authorization",
                "Access-Control-Allow-Origin", "Access-Control-Allow-Origin", "Access-Control-Allow-Credentials"));
        corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(urlBasedCorsConfigurationSource);
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
