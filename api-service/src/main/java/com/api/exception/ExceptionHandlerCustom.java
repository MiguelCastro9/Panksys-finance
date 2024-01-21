package com.api.exception;

import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 *
 * @author Miguel Castro
 */
@ControllerAdvice
public class ExceptionHandlerCustom extends ResponseEntityExceptionHandler {

    private List<MessageException> generateListAtMessages(BindingResult bindingResult) {
        List<MessageException> message = new ArrayList<>();
        bindingResult.getFieldErrors().forEach(erro -> {
            String messageUser = erro.getDefaultMessage();
            String messageDeveloper = erro.toString();
            message.add(new MessageException(messageUser, messageDeveloper));
        });
        return message;
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        List<MessageException> message = generateListAtMessages(ex.getBindingResult());
        return handleExceptionInternal(ex, message, headers, HttpStatus.BAD_REQUEST, request);
    }
    
    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        MessageException message = new MessageException(ex.getMessage(), ex.getMessage());
        return handleExceptionInternal(ex, message, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
}
