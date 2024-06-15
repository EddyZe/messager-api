package ru.eddyz.messagerapi.services;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.eddyz.messagerapi.exeption.ChatInvalidException;
import ru.eddyz.messagerapi.exeption.UserInvalidException;
import ru.eddyz.messagerapi.exeption.UserNotFoundException;
import ru.eddyz.messagerapi.models.DTO.CreateChatDTO;
import ru.eddyz.messagerapi.models.entities.Chat;
import ru.eddyz.messagerapi.models.entities.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreateChatService {

    private final ChatService chatService;
    private final UserService userService;

    @Transactional
    public void createChat(CreateChatDTO createChatDTO) {

        User ownerChat = userService.findByUsername(createChatDTO.getOwnerUsername());

        List<Chat> ownerChats = ownerChat.getChats();

        ownerChats.forEach(chat -> {
            if (chat.getChatName().equals(createChatDTO.getChatName()))
                throw new ChatInvalidException("Чат с таким именем уже существует");
        });

        List<User> userChat = new ArrayList<>();
        userChat.add(ownerChat);

        createChatDTO.getUsers().forEach(user -> {
            try {
                userChat.add(userService.findByUsername(user.getUsername()));
            } catch (UserNotFoundException e){
                log.error(user.getUsername() + ": " + e.getMessage());
            }
        });

        if (userChat.isEmpty())
            throw new UserInvalidException("Пользователи с такими username не найдены!");

        Chat newChat = createNewChat(createChatDTO, userChat);

        userChat.forEach(user -> {
            List<Chat> chats = user.getChats();

            if (chats == null)
                chats = new ArrayList<>();

            chats.add(newChat);
        });

        chatService.save(newChat);
    }

    private Chat createNewChat(CreateChatDTO createChatDTO, List<User> userChat) {
        return Chat.builder()
                .ownerUsername(createChatDTO.getOwnerUsername())
                .createdAt(LocalDateTime.now())
                .chatName(createChatDTO.getChatName())
                .users(userChat)
                .build();
    }


}
