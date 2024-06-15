package ru.eddyz.messagerapi.services;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.eddyz.messagerapi.exeption.ChatInvalidException;
import ru.eddyz.messagerapi.exeption.ChatNotFoundException;
import ru.eddyz.messagerapi.models.entities.Chat;
import ru.eddyz.messagerapi.repositories.ChatReposotory;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatService {

    private final ChatReposotory chatReposotory;


    @Transactional
    public void save (Chat chat) {
        if (chatReposotory.findById(chat.getChatId()).isPresent())
            throw new ChatInvalidException("Такой чат уже существует!");

        chatReposotory.save(chat);
    }

    @Transactional
    public void update (Chat chat) {
        chatReposotory.save(chat);
    }



    public Chat findById(Integer chatId) {
        return chatReposotory.findById(chatId).orElseThrow(
                () -> new ChatNotFoundException("Такой чат не найден!"));
    }


    public List<Chat> findByChatName(String chatName) {
        List<Chat> chats = chatReposotory.findByChatName(chatName);

        if (chats.isEmpty())
            throw new ChatNotFoundException("Чат с таким названием не найден");

        return chats;
    }

    @Transactional
    public void deleteById (Integer chatId) {
        chatReposotory.deleteById(chatId);
    }

}
