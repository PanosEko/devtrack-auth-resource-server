//package com.panoseko.devtrack.user;
//
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.time.LocalDate;
//import java.time.Month;
//import java.util.List;
//
//@Configuration
//public class UserConfig {
//
//    @Bean
//    CommandLineRunner commandLineRunner(UserRepository repository) {
//        return args -> {
//            User john = new User(
//                    0L,
//                    "John Jonson",
//                    1,
//                    LocalDate.of(2000, Month.JANUARY, 5),
//                    "jonas@gmail.com",
//                    "jonabrotha",
//                    "jonas123",
//                    Role.USER);
//            User panos = new User(
//                    1L,
//                    "Panos Εκο",
//                    2,
//                    LocalDate.of(1995, Month.JANUARY, 5),
//                    "panaras@gmail.com",
//                    "panais",
//                    "panos123",
//                    Role.ADMIN);
//
//            repository.saveAll(
//                    List.of(john, panos));
//        };
//    }
//}
