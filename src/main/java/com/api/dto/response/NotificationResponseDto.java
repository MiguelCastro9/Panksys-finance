package com.api.dto.response;

import com.api.model.NotificationModel;
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
public class NotificationResponseDto extends RepresentationModel {

    private String description;

    private LocalDateTime created_date;

    public static NotificationResponseDto convertEntityForNotificationDto(NotificationModel notificationModel) {
        return new NotificationResponseDto(notificationModel.getDescription(), notificationModel.getCreated_date());
    }
}
