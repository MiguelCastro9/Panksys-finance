package com.api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * @author Miguel Castro
 */
@Document(collection = "Notifications")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationModel {

    @Id
    private String id;

    @Column(nullable = false)
    private Long user;

    @Column(nullable = false, length = 255)
    private String description;

    @Column(nullable = false)
    private LocalDateTime created_date;

    public NotificationModel(Builder builder) {
        this.id = builder.id;
        this.user = builder.user;
        this.description = builder.description;
        this.created_date = builder.created_date;
    }

    public static class Builder {

        private String id;

        private Long user;

        private String description;

        private LocalDateTime created_date;

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setUser(Long user) {
            this.user = user;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setCreated_date(LocalDateTime created_date) {
            this.created_date = created_date;
            return this;
        }

        public NotificationModel build() {
            return new NotificationModel(this);
        }
    }
}
