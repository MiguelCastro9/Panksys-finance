package com.api.dto.request;

import com.api.model.FeedbackModel;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Miguel Castro
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackRequestDto {

    private Long id;

    @NotNull(message = "Total stars is required.")
    @Max(value = 5, message = "Total stars required at maximum 5 stars.")
    @Min(value = 1, message = "Total stars required at minimum 1 star.")
    private Integer total_stars;

    private String title;

    private String description;

    public FeedbackRequestDto(Integer total_stars, String title, String description) {
        this.total_stars = total_stars;
        this.title = title;
        this.description = description;
    }

    public FeedbackModel convertFeedbackDtoForEntity() {
        FeedbackModel.Builder feedbackBuilder = new FeedbackModel.Builder();
        feedbackBuilder.setTotal_stars(total_stars)
                .setTitle(title)
                .setDescription(description);
        return new FeedbackModel(feedbackBuilder);
    }
}
