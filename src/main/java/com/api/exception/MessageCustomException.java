package com.api.exception;

/**
 *
 * @author Miguel Castro
 */
public class MessageCustomException extends RuntimeException {
    
    public MessageCustomException(String message) {
        super(message);
    }
}
