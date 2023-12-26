package com.api.service.impl;

import com.api.model.NotificationModel;
import com.api.model.UserModel;
import com.api.repository.NotificationRepository;
import com.api.service.NotificationService;
import java.time.LocalDateTime;
import java.util.List;
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
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Override
    public NotificationModel save(NotificationModel notificationModel) {
        UserModel userAuthenticated = getUserAuthenticated();
        NotificationModel notificationBuilder = new NotificationModel.Builder()
                .setUser(userAuthenticated.getId())
                .setDescription(notificationModel.getDescription())
                .setCreated_date(LocalDateTime.now())
                .build();
        return notificationRepository.save(notificationBuilder);
    }

    @Override
    public List<NotificationModel> findByUserId() {
        UserModel userAuthenticated = getUserAuthenticated();
        return notificationRepository.findByUser(userAuthenticated.getId());
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
