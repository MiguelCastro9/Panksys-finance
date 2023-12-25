package com.api.service;

import com.api.model.FeedbackModel;
import java.util.List;

/**
 *
 * @author Miguel Castro
 */
public interface FeedbackService {
    
    public FeedbackModel save(FeedbackModel feedbackModel);
    
    public List<FeedbackModel> list();
    
    public List<FeedbackModel> find(Integer stars);
}
