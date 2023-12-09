package com.api.model;

import com.api.enums.RoleEnum;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 *
 * @author Miguel Castro
 */
@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserModel implements Serializable, UserDetails {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false, length = 45)
    private String name;

    @Column(nullable = false)
    private LocalDate birth_date;

    @Column(nullable = false, length = 255)
    private String email;

    @Column(nullable = false, length = 255)
    private String password;

    @Column(nullable = false)
    private String role = RoleEnum.USER.getName();

    @Column(nullable = false)
    private boolean enabled = true;

    public UserModel(String name, LocalDate birth_date, String email, String password, String role) {
        this.name = name;
        this.birth_date = birth_date;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public UserModel(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.birth_date = builder.birth_date;
        this.email = builder.email;
        this.password = builder.password;
        this.role = builder.role;
        this.enabled = builder.enabled;
    }

    public static class Builder {

        private Long id;

        private String name;

        private LocalDate birth_date;

        private String email;

        private String password;

        private String role = RoleEnum.USER.getName();

        private boolean enabled = true;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setBirth_date(LocalDate birth_date) {
            this.birth_date = birth_date;
            return this;
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder setRole(String role) {
            this.role = role;
            return this;
        }

        public Builder setEnabled(boolean enabled) {
            this.enabled = enabled;
            return this;
        }

        public UserModel build() {
            return new UserModel(this);
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
