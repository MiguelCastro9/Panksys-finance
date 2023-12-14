package com.api.dto.request;

import com.api.model.UserModel;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

/**
 *
 * @author Miguel Castro
 */
@Data
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
    @Email(message = "E-mail invalid.")
    @Length(min = 10, max = 255, message = "E-mail required at minimum {min} and at maximum {max} characters.")
    private String email;

    @NotBlank(message = "Password is required.")
    @Length(min = 10, max = 255, message = "Password required at minimum {min} and at maximum {max} characters.")
    private String password;

    @NotBlank(message = "Password repeated is required.")
    @Length(min = 10, max = 255, message = "Password repeated required at minimum {min} and at maximum {max} characters.")
    private String passwordRepeated;

    public UserRequestDto(String name, LocalDate birth_date, String email, String password, String passwordRepeated) {
        this.name = name;
        this.birth_date = birth_date;
        this.email = email;
        this.password = password;
        this.passwordRepeated = passwordRepeated;
    }

    public UserModel convertUserDtoForEntity() {
        UserModel.Builder userBuilder = new UserModel.Builder();
        userBuilder.setName(name)
                .setBirth_date(birth_date)
                .setEmail(email)
                .setPassword(password);
        return new UserModel(userBuilder);
    }

    public UserModel convertUserUpdateDtoForEntity() {
        UserModel.Builder userBuilder = new UserModel.Builder();
        userBuilder.setId(id)
                .setName(name)
                .setBirth_date(birth_date)
                .setEmail(email)
                .setPassword(password);
        return new UserModel(userBuilder);
    }
}
