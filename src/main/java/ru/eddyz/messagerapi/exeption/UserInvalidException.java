package ru.eddyz.messagerapi.exeption;

public class UserInvalidException extends RuntimeException{

    public UserInvalidException(String message) {
        super(message);
    }
}
