package com.api.service;

import com.api.model.NotificationModel;
import java.util.List;

/**
 *
 * @author Miguel Castro
 */
public interface NotificationService {
    
    public NotificationModel save(NotificationModel notificationModel);
    
    public List<NotificationModel> findByUserId();
}
