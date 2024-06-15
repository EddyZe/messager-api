package ru.eddyz.messagerapi.services;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.eddyz.messagerapi.exeption.UserInvalidException;
import ru.eddyz.messagerapi.exeption.UserNotFoundException;
import ru.eddyz.messagerapi.models.DTO.CreateChatDTO;
import ru.eddyz.messagerapi.models.entities.Chat;
import ru.eddyz.messagerapi.models.entities.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CreateChatService {

    private final ChatService chatService;
    private final UserService userService;

    @Transactional
    public void createChat(CreateChatDTO createChatDTO) {
        List<User> userChat = new ArrayList<>();

        createChatDTO.getUsers().forEach(user -> {
            try {
                userChat.add(userService.findByUsername(user.getUsername()));
            } catch (UserNotFoundException ignored){}
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

        userService.updateAll(userChat);
        chatService.save(newChat);
    }

    private Chat createNewChat(CreateChatDTO createChatDTO, List<User> userChat) {
        return Chat.builder()
                .createdAt(LocalDateTime.now())
                .chatName(createChatDTO.getChatName())
                .users(userChat)
                .build();
    }


}
