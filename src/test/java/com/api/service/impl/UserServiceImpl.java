package com.api.service.impl;

import com.api.model.UserModel;
import com.api.repository.UserRepository;
import com.api.service.UserService;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

    @Override
    public UserModel singup(UserModel userModel) {
        Optional<UserModel> checkUserPresent = userRepository.findByEmail(userModel.getEmail());
        if (checkUserPresent.isEmpty()) {
            UserModel userBuilder = new UserModel.Builder()
                    .setName(userModel.getName())
                    .setBirth_date(LocalDate.now())
                    .setEmail(userModel.getEmail())
                    .setPassword(passwordEncoder.encode(userModel.getPassword()))
                    .setCreated_date(LocalDateTime.now())
                    .setUpdated_date(LocalDateTime.now())
                    .build();
            return userRepository.save(userBuilder);
        } else {
            throw new IllegalArgumentException("User with email " + userModel.getEmail() + " already exists");
        }
    }

    @Override
    public UserModel update(Long id, UserModel userModel) {
        return userRepository.findById(id)
                .map(existingUser -> {
                    UserModel userBuilder = new UserModel.Builder()
                            .setId(existingUser.getId())
                            .setName(userModel.getName())
                            .setBirth_date(LocalDate.now())
                            .setEmail(userModel.getEmail())
                            .setPassword(passwordEncoder.encode(userModel.getPassword()))
                            .setCreated_date(LocalDateTime.now())
                            .setUpdated_date(LocalDateTime.now())
                            .build();
                    return userRepository.save(userBuilder);
                })
                .orElseThrow(() -> new IllegalArgumentException("User not found."));
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

    @Override
    public UserModel disabled(Long id) {
        return userRepository.findById(id)
                .map(existingUser -> {
                    UserModel userBuilder = new UserModel.Builder()
                            .setId(existingUser.getId())
                            .setName(existingUser.getName())
                            .setBirth_date(existingUser.getBirth_date())
                            .setEmail(existingUser.getEmail())
                            .setPassword(passwordEncoder.encode(existingUser.getPassword()))
                            .setEnabled(false)
                            .setCreated_date(existingUser.getCreated_date())
                            .setUpdated_date(existingUser.getUpdated_date())
                            .build();
                    return userRepository.save(userBuilder);
                })
                .orElseThrow(() -> new IllegalArgumentException("User not found."));
    }
}
