package ru.eddyz.messagerapi.exeption;

public class ChatInvalidException extends RuntimeException{

    public ChatInvalidException (String msg) {
        super(msg);
    }

}
