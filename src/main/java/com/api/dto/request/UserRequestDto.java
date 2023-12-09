package com.api.dto.request;

import com.api.enums.RoleEnum;
import com.api.model.UserModel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

/**
 *
 * @author Miguel Castro
 */
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDto {

    private Long id;

    @NotBlank(message = "Name is required.")
    @Length(min = 3, max = 45, message = "Name required at minimum {min} and at maximum {max} characters.")
    private String name;

    @NotNull(message = "Birth date is required.")
    private LocalDate birth_date;

    @NotBlank(message = "E-mail is required.")
    @Length(min = 10, max = 255, message = "E-mail required at minimum {min} and at maximum {max} characters.")
    private String email;

    @NotBlank(message = "Password is required.")
    @Length(min = 10, max = 255, message = "Password required at minimum {min} and at maximum {max} characters.")
    private String password;

    @NotBlank(message = "Password repeated is required.")
    private String passwordRepeated;

    private String role = RoleEnum.USER.getName();

    private boolean enabled = true;

    public UserRequestDto(String name, LocalDate birth_date, String email, String password, String passwordRepeated, String role, boolean enabled) {
        this.name = name;
        this.birth_date = birth_date;
        this.email = email;
        this.password = password;
        this.passwordRepeated = passwordRepeated;
        this.role = role;
        this.enabled = enabled;
    }

    public UserModel convertUserDtoForEntity() {
        UserModel.Builder userBuilder = new UserModel.Builder();
        userBuilder.setName(name)
                .setBirth_date(birth_date)
                .setEmail(email)
                .setPassword(password)
                .setRole(role);
        return new UserModel(userBuilder);
    }

    public UserModel convertUserUpdateDtoForEntity() {
        UserModel.Builder userBuilder = new UserModel.Builder();
        userBuilder.setId(id)
                .setName(name)
                .setBirth_date(birth_date)
                .setEmail(email)
                .setPassword(password)
                .setRole(role)
                .setEnabled(enabled);
        return new UserModel(userBuilder);
    }
}
