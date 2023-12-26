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
    
    public List<FeedbackModel> find(Integer stars);
    
    public List<FeedbackModel> myFeedbacks();
    
    public FeedbackModel disabled(Long id);
}
