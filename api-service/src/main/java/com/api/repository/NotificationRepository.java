package com.api.repository;

import com.api.model.NotificationModel;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * @author Miguel Castro
 */
public interface NotificationRepository extends MongoRepository<NotificationModel, String> {
    
    @Query(value = "{ 'user' : ?0 }", nativeQuery = true)
    List<NotificationModel> findByUser(Long user);
}
