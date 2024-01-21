package com.api.dto.response;

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
public class JWTResponseDto extends RepresentationModel {
    
    private String token;
    
    private String refresh_token;
}
