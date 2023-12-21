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
public class ApiServiceApplication {


    public static void main(String[] args) {
        SpringApplication.run(ApiServiceApplication.class, args);
    }

    
}
