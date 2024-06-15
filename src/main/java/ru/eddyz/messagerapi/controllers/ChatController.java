package ru.eddyz.messagerapi.controllers;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.eddyz.messagerapi.exeption.ChatInvalidException;
import ru.eddyz.messagerapi.models.DTO.AddUserChatDTO;
import ru.eddyz.messagerapi.models.DTO.CreateChatDTO;
import ru.eddyz.messagerapi.models.entities.Chat;
import ru.eddyz.messagerapi.models.entities.User;
import ru.eddyz.messagerapi.services.ChatService;
import ru.eddyz.messagerapi.services.CreateChatService;
import ru.eddyz.messagerapi.services.UserService;

import java.util.List;

@RestController
@RequestMapping("chat")
@RequiredArgsConstructor
@Slf4j
public class ChatController {

    private final ChatService chatService;
    private final CreateChatService createChatService;
    private final UserService userService;

    @PostMapping("{username}/create")
    public ResponseEntity<HttpStatus> createNewChat(@PathVariable("username") String username,
                                                    @RequestBody @Valid CreateChatDTO createChatDTO,
                                                    BindingResult bindingResult) {
        log.info("starting to creating a chat");

        handlerBindingResult(bindingResult);

        createChatDTO.setOwnerUsername(username);

        createChatService.createChat(createChatDTO);

        log.info("the chat has been created");

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("{username}/chats")
    public List<Chat> userChats(@PathVariable String username) {
        return userService.findByUsername(username).getChats();
    }
    @PostMapping("{chatId}/add-new-user")
    public ResponseEntity<HttpStatus> addNewUser(@PathVariable("chatId") Integer chatId,
                                                 @RequestBody @Valid AddUserChatDTO addUserChatDTO,
                                                 BindingResult bindingResult) {
        log.info("add new user in chat [%s]".formatted(chatId));

        handlerBindingResult(bindingResult);

        User newUser = userService.findByUsername(addUserChatDTO.getUsername());
        Chat currentChat = chatService.findById(chatId);

        if (currentChat.getUsers().contains(newUser))
            throw new ChatInvalidException("%s уже состоит в чате".formatted(newUser.getUsername()));


        currentChat.getUsers().add(newUser);
        newUser.getChats().add(currentChat);

        userService.update(newUser);
        chatService.update(currentChat);
        log.info("%s added in chat[%s]".formatted(addUserChatDTO.getUsername(), chatId));

        return ResponseEntity.ok(HttpStatus.OK);
    }


    @DeleteMapping("{chatId}/delete")
    public ResponseEntity<HttpStatus> deleteChat(@PathVariable("chatId") Integer chatId) {
        log.info("start deleting a chat[%s]".formatted(chatId));

        chatService.deleteById(chatId);

        log.info("chat[%s] deleting completed".formatted(chatId));
        return ResponseEntity.ok(HttpStatus.OK);
    }


    private void handlerBindingResult(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorsMessage = new StringBuilder();

            bindingResult.getFieldErrors().forEach(fieldError -> errorsMessage
                    .append(fieldError.getField()
                            .replaceAll("([A-Z]+)([A-Z][a-z])", "$1_$2")
                            .replaceAll("([a-z])([A-Z])", "$1_$2")
                            .toLowerCase())
                    .append(" - ")
                    .append(fieldError.getDefaultMessage())
                    .append("; "));

            throw new ChatInvalidException(errorsMessage.toString());
        }
    }

}
