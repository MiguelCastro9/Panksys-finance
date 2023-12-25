package com.api.service.impl;

import com.api.model.FeedbackModel;
import com.api.model.UserModel;
import com.api.repository.FeedbackRepository;
import com.api.service.FeedbackService;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 *
 * @author Miguel Castro
 */
@Service
public class FeedbackServiceImpl implements FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Override
    public FeedbackModel save(FeedbackModel feedbackModel) {
        UserModel userAuthenticated = getUserAuthenticated();
        FeedbackModel feedbackBuilder = new FeedbackModel.Builder()
                .setName(userAuthenticated.getName())
                .setTotal_stars(feedbackModel.getTotal_stars())
                .setTitle(feedbackModel.getTitle())
                .setDescription(feedbackModel.getDescription())
                .setCreated_date(LocalDateTime.now())
                .setUser(userAuthenticated)
                .build();
        return feedbackRepository.save(feedbackBuilder);
    }

    @Override
    public List<FeedbackModel> list() {
        return feedbackRepository.findAll().stream()
                .sorted(Comparator.comparing(FeedbackModel::getId, Comparator.reverseOrder()))
                .collect(Collectors.toList());
    }

    @Override
    public List<FeedbackModel> find(Integer stars) {
        return feedbackRepository.find(stars).stream()
                .sorted(Comparator.comparing(FeedbackModel::getId, Comparator.reverseOrder()))
                .collect(Collectors.toList());
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
