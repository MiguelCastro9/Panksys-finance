package com.api;

import com.api.enums.RoleEnum;
import com.api.model.UserModel;
import com.api.repository.UserRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class ApiServiceApplication implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(ApiServiceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        UserModel getRoleUser = userRepository.findByRole(RoleEnum.ADMIN);

        if (getRoleUser == null) {
            UserModel.Builder builder = new UserModel.Builder()
                    .setName("admin")
                    .setBirth_date(LocalDate.now())
                    .setEmail("admin@email.com")
                    .setPassword(new BCryptPasswordEncoder().encode("adminadmin"))
                    .setRole(RoleEnum.ADMIN)
                    .setCreated_date(LocalDateTime.now())
                    .setUpdated_date(LocalDateTime.now());
            userRepository.save(builder.build());
        }
    }
}
