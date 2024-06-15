package ru.eddyz.messagerapi.exeption;

public class MessageNotFoundException extends RuntimeException{

    public MessageNotFoundException(String msg) {
        super(msg);
    }
}
