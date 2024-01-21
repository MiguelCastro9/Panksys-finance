package com.api.dto.response;

import com.api.model.UserModel;
import java.time.LocalDate;
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
public class UserResponseDto extends RepresentationModel {
    
    private Long id;
    
    private String name;
    
    private LocalDate birth_date;
    
    private String email;
    
    public static UserResponseDto convertEntityForUserResponseDto(UserModel userModel) {
        return new UserResponseDto(userModel.getId(), userModel.getName(), 
                userModel.getBirth_date(), userModel.getEmail());
    }
}