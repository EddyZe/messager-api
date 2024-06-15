package ru.eddyz.messagerapi.exeption;

public class MessageInvalidException extends RuntimeException{

    public MessageInvalidException(String msg) {
        super(msg);
    }
}
