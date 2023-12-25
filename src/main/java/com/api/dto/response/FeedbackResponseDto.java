package com.api.dto.response;

import com.api.model.FeedbackModel;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

/**
 *
 * @author Miguel Castro
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackResponseDto extends RepresentationModel {

    private Long id;

    private String name;

    private Integer total_stars;

    private String title;

    private String description;

    private LocalDateTime created_date;

    public static FeedbackResponseDto convertEntityForFeedbackDto(FeedbackModel feedbackModel) {
        return new FeedbackResponseDto(feedbackModel.getId(), feedbackModel.getName(),
                feedbackModel.getTotal_stars(), feedbackModel.getTitle(),
                feedbackModel.getDescription(), feedbackModel.getCreated_date());
    }
}
