package com.api.service.impl;

import com.api.model.FeedbackModel;
import com.api.model.NotificationModel;
import com.api.model.UserModel;
import com.api.repository.FeedbackRepository;
import com.api.repository.NotificationRepository;
import com.api.service.FeedbackService;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
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
    
    @Autowired
    private NotificationRepository notificationRepository;

    @Override
    public FeedbackModel save(FeedbackModel feedbackModel) {
        UserModel userAuthenticated = getUserAuthenticated();
        FeedbackModel feedbackBuilder = new FeedbackModel.Builder()
                .setName(userAuthenticated.getName())
                .setTotal_stars(feedbackModel.getTotal_stars())
                .setTitle(feedbackModel.getTitle())
                .setDescription(feedbackModel.getDescription())
                .setCreated_date(LocalDateTime.now())
                .setEnabled(true)
                .setUser(userAuthenticated)
                .build();
        feedbackRepository.save(feedbackBuilder);
        NotificationModel notificationModel = new NotificationModel.Builder()
                .setUser(userAuthenticated.getId())
                .setDescription("Your new feedback was saved with success.")
                .setCreated_date(LocalDateTime.now())
                .build();
        notificationRepository.save(notificationModel);
        return feedbackBuilder;
    }

    @Override
    public List<FeedbackModel> findAllFeedbacks() {
        return feedbackRepository.findAllFeedbacks().stream()
                .sorted(Comparator.comparing(FeedbackModel::getId, Comparator.reverseOrder()))
                .collect(Collectors.toList());
    }

    @Override
    public List<FeedbackModel> find(Integer stars) {
        return feedbackRepository.find(stars).stream()
                .sorted(Comparator.comparing(FeedbackModel::getId, Comparator.reverseOrder()))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<FeedbackModel> myFeedbacks() {
        UserModel userAuthenticated = getUserAuthenticated();
        return feedbackRepository.myFeedbacks(userAuthenticated.getId()).stream()
                .sorted(Comparator.comparing(FeedbackModel::getId, Comparator.reverseOrder()))
                .collect(Collectors.toList());
    }
    
    @Override
    public FeedbackModel disabled(Long id) {
        UserModel userAuthenticated = getUserAuthenticated();
        Optional<FeedbackModel> getFeedback = feedbackRepository.getFeedback(id);
        if (getFeedback.isEmpty()) {
            throw new IllegalArgumentException("Feedback don't exists.");
        }
        if (getFeedback.get().getUser().getId() == null) {
            throw new IllegalArgumentException("Feedback don't exists.");
        }
        if (!getFeedback.get().getUser().getId().equals(userAuthenticated.getId())) {
            throw new IllegalArgumentException("You are not allowed to disabled other users' feedbacks.");
        }
        
        return feedbackRepository.findById(id)
                .map(existingFeedback -> {
                    FeedbackModel feedbackBuilder = new FeedbackModel.Builder()
                            .setId(existingFeedback.getId())
                            .setName(existingFeedback.getName())
                            .setTotal_stars(existingFeedback.getTotal_stars())
                            .setTitle(existingFeedback.getTitle())
                            .setDescription(existingFeedback.getDescription())
                            .setCreated_date(existingFeedback.getCreated_date())
                            .setEnabled(false)
                            .setUser(existingFeedback.getUser())
                            .build();
                    return feedbackRepository.save(feedbackBuilder);
                })
                .orElseThrow(() -> new IllegalArgumentException("Feedback don't exists."));
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
