package com.api.exception;

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
public class MessageException {
    
    private String message_user;
    
    private String message_developer;
}
