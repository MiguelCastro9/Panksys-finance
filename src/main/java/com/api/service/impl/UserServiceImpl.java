package com.api.service.impl;

import com.api.model.UserModel;
import com.api.repository.UserRepository;
import com.api.service.UserService;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
                    .setBirth_date(userModel.getBirth_date())
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
        UserModel userAuthenticated = getUserAuthenticated();
        if (!id.equals(userAuthenticated.getId())) {
            throw new IllegalArgumentException("You are not allowed to update other users.");
        }
        return userRepository.findById(id)
                .map(existingUser -> {
                    UserModel userBuilder = new UserModel.Builder()
                            .setId(existingUser.getId())
                            .setName(userModel.getName())
                            .setBirth_date(userModel.getBirth_date())
                            .setEmail(userModel.getEmail())
                            .setPassword(passwordEncoder.encode(userModel.getPassword()))
                            .setCreated_date(existingUser.getCreated_date())
                            .setUpdated_date(LocalDateTime.now())
                            .build();
                    return userRepository.save(userBuilder);
                })
                .orElseThrow(() -> new IllegalArgumentException("User don't exists."));
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
    public UserModel disabled(Long id) {
        UserModel userAuthenticated = getUserAuthenticated();
        if (!id.equals(userAuthenticated.getId())) {
            throw new IllegalArgumentException("You are not allowed to disabled other users.");
        }
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
                            .setUpdated_date(LocalDateTime.now())
                            .build();
                    return userRepository.save(userBuilder);
                })
                .orElseThrow(() -> new IllegalArgumentException("User don't exists."));
    }

    private UserModel getUserAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserModel userAuthenticated = (UserModel) authentication.getPrincipal();
        if (authentication.getPrincipal() instanceof UserDetails) {
            if (!userAuthenticated.isEnabled()) {
                throw new IllegalArgumentException("Your user is disabled.");
            }
        } else {
            throw new IllegalArgumentException("User details not found in the authentication context.");
        }
        return userAuthenticated;
    }
}
