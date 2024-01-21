package com.api.dto.request;

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
public class SigninRequestDto {
    
    private String email;
    
    private String password;
}
