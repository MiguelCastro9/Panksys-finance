package com.api.dto.response;

import com.api.model.UserModel;
import java.time.LocalDate;
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
public class UserResponseDto {
    
    private Long id;
    
    private String name;
    
    private LocalDate birth_date;
    
    private String email;
    
    private String password;
    
    private String role;
    
    private boolean enabled;
    
    public static UserResponseDto convertEntityForUserDto(UserModel userModel) {
        return new UserResponseDto(userModel.getId(), userModel.getName(), 
                userModel.getBirth_date(), userModel.getEmail(), userModel.getPassword(), userModel.getRole(), userModel.isEnabled());
    }
}