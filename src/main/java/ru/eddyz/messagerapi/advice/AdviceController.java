package ru.eddyz.messagerapi.advice;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.eddyz.messagerapi.exeption.ChatInvalidException;
import ru.eddyz.messagerapi.exeption.ChatNotFoundException;
import ru.eddyz.messagerapi.exeption.UserInvalidException;
import ru.eddyz.messagerapi.exeption.UserNotFoundException;
import ru.eddyz.messagerapi.models.ErrorResponse;

import java.time.LocalDateTime;

@RestControllerAdvice
@Slf4j
public class AdviceController {

    @ExceptionHandler(ChatInvalidException.class)
    public ResponseEntity<ErrorResponse> handlerChatInvalidException (ChatInvalidException e) {
        log.error("chat invalid");
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ChatNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlerChatNotFoundException (ChatNotFoundException e) {
        log.error("chat not found");
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserInvalidException.class)
    public ResponseEntity<ErrorResponse> handlerUserInvalidException (UserInvalidException e) {
        log.error("user invalid");
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlerUserNotFoundException (UserNotFoundException e) {
        log.error("user not found");
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

}
