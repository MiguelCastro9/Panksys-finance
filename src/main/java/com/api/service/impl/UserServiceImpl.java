package com.api.service.impl;

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

    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public UserModel singup(UserModel userModel) {
        Optional<UserModel> checkUserPresent = userRepository.findByEmail(userModel.getEmail());
        if (checkUserPresent.isEmpty()) {
            UserModel builder = new UserModel.Builder()
                    .setName(userModel.getName())
                    .setBirth_date(LocalDate.parse(dateFormatter.format(userModel.getBirth_date()), dateFormatter))
                    .setEmail(userModel.getEmail())
                    .setPassword(passwordEncoder.encode(userModel.getPassword()))
                    .build();
            return userRepository.save(builder);
        } else {
            throw new IllegalArgumentException("User with email " + userModel.getEmail() + " already exists");
        }
    }

    @Override
    public UserModel update(Long id, UserModel userModel) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof UserDetails) {
            UserModel infoUserAuthenticated = (UserModel) authentication.getPrincipal();
            if (!id.equals(infoUserAuthenticated.getId())) {
                throw new IllegalArgumentException("You are not allowed to update other users.");
            }
        } else {
            throw new IllegalArgumentException("User details not found in the authentication context.");
        }
        return userRepository.findById(id)
                .map(existingUser -> {
                    UserModel.Builder builder = new UserModel.Builder()
                            .setId(existingUser.getId())
                            .setName(userModel.getName())
                            .setBirth_date(userModel.getBirth_date())
                            .setEmail(userModel.getEmail())
                            .setPassword(passwordEncoder.encode(userModel.getPassword()));
                    return userRepository.save(builder.build());
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof UserDetails) {
            UserModel infoUserAuthenticated = (UserModel) authentication.getPrincipal();

            if (!id.equals(infoUserAuthenticated.getId())) {
                throw new IllegalArgumentException("You are not allowed to disabled other users.");
            }
        } else {
            throw new IllegalArgumentException("User details not found in the authentication context.");
        }
        return userRepository.findById(id)
                .map(existingUser -> {
                    UserModel.Builder builder = new UserModel.Builder()
                            .setId(existingUser.getId())
                            .setName(existingUser.getName())
                            .setBirth_date(existingUser.getBirth_date())
                            .setEmail(existingUser.getEmail())
                            .setPassword(passwordEncoder.encode(existingUser.getPassword()))
                            .setEnabled(false);
                    return userRepository.save(builder.build());
                })
                .orElseThrow(() -> new IllegalArgumentException("User not found."));
    }
}
