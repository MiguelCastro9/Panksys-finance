package com.api.service.impl;

import com.api.enums.RoleEnum;
import com.api.exception.MessageCustomException;
import com.api.model.UserModel;
import com.api.repository.UserRepository;
import com.api.service.UserService;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
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
            return userRepository.save(newUser);
        }
        return null;
    }

    @Override
    public UserModel update(Long id, UserModel userModel) {
        return userRepository.findById(id)
                .map(existingUser -> {
                    existingUser.setName(userModel.getName());
                    existingUser.setBirth_date(userModel.getBirth_date());
                    existingUser.setEmail(userModel.getEmail());
                    existingUser.setPassword(passwordEncoder.encode(userModel.getPassword()));
                    return userRepository.save(existingUser);
                })
                .orElseThrow(() -> new MessageCustomException("Person not found."));
    }

    @Override
    public List<UserModel> list() {
        return userRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(UserModel::getId, Comparator.reverseOrder()))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<UserModel> find(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public void deleteAll() {
        userRepository.deleteAll();
    }
}
