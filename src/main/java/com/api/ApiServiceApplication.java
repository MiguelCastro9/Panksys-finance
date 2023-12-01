package com.api;

import com.api.enums.RoleEnum;
import com.api.model.UserModel;
import com.api.repository.UserRepository;
import java.time.LocalDate;
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
        
        UserModel adminAccount = userRepository.findByRole(RoleEnum.ADMIN);
        
        if (adminAccount == null) {
            UserModel userModel = new UserModel();
            userModel.setName("admin");
            userModel.setBirth_date(LocalDate.now());
            userModel.setEmail("admin@email.com");
            userModel.setPassword(new BCryptPasswordEncoder().encode("adminadmin"));
            userModel.setRole(RoleEnum.ADMIN);
            userModel.setActive(true);
            userRepository.save(userModel);
        }
    }
}
