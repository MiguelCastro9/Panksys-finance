package com.api.service;

import com.api.model.FeedbackModel;
import java.util.List;

/**
 *
 * @author Miguel Castro
 */
public interface FeedbackService {
    
    public FeedbackModel save(FeedbackModel feedbackModel);
    
    public List<FeedbackModel> findAllFeedbacks();
    
    public List<FeedbackModel> filterFeedbackByStars(Integer stars);
    
    public List<FeedbackModel> findAllMyFeedbacks();
    
    public FeedbackModel disabled(Long id);
}
