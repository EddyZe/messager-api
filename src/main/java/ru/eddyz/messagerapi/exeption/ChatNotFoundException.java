package ru.eddyz.messagerapi.exeption;

public class ChatNotFoundException extends RuntimeException{

    public ChatNotFoundException (String msg) {
        super(msg);
    }
}
