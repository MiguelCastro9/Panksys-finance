package com.api.service.impl;

import com.api.enums.RoleEnum;
import com.api.model.UserModel;
import com.api.repository.UserRepository;
import com.api.service.UserService;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author Miguel Castro
 */
@Service
public class UserServiceImpl implements UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
    @Override
    public UserModel singup(UserModel userModel) {
        Optional<UserModel> checkUserPresent = userRepository.findByEmail(userModel.getEmail());
        if (checkUserPresent.isEmpty()) {
            UserModel newUser = new UserModel();
            newUser.setName(userModel.getName());
            newUser.setBirth_date(LocalDate.parse(dateFormatter.format(userModel.getBirth_date()), dateFormatter));
            newUser.setEmail(userModel.getEmail());
            newUser.setPassword(passwordEncoder.encode(userModel.getPassword()));
            newUser.setRole(RoleEnum.USER);
            newUser.setActive(true);

            return userRepository.save(newUser);
        }
        return null;
    }
}
