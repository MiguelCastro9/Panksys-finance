package com.api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Miguel Castro
 */
@Entity
@Table(name = "feedbacks")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false, length = 45)
    private String name;

    @Column(nullable = false)
    private Integer total_stars;

    @Column(length = 45)
    private String title;

    @Column(length = 255)
    private String description;

    @Column(nullable = false)
    private LocalDateTime created_date;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserModel user;

    public FeedbackModel(String name, Integer total_stars, String title, String description, LocalDateTime created_date, UserModel user) {
        this.name = name;
        this.total_stars = total_stars;
        this.title = title;
        this.description = description;
        this.created_date = created_date;
        this.user = user;
    }

    public FeedbackModel(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.total_stars = builder.total_stars;
        this.title = builder.title;
        this.description = builder.description;
        this.created_date = builder.created_date;
        this.user = builder.user;
    }

    public static class Builder {

        private Long id;

        private String name;

        private Integer total_stars;

        private String title;

        private String description;

        private LocalDateTime created_date;

        private UserModel user;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setTotal_stars(Integer total_stars) {
            this.total_stars = total_stars;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
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

        public Builder setUser(UserModel user) {
            this.user = user;
            return this;
        }
        
        public FeedbackModel build() {
            return new FeedbackModel(this);
        }
    }
}
