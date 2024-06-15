package ru.eddyz.messagerapi.controllers;


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
import ru.eddyz.messagerapi.services.MessageService;
import ru.eddyz.messagerapi.services.UserService;

@RestController
@RequestMapping("chat")
@RequiredArgsConstructor
@Slf4j
public class ChatController {

    private final ChatService chatService;
    private final CreateChatService createChatService;
    private final MessageService messageService;
    private final UserService userService;

    @PostMapping("create")
    public ResponseEntity<HttpStatus> createNewChat(@RequestBody CreateChatDTO createChatDTO,
                                                    BindingResult bindingResult) {
        log.info("Начало создания нового чата");

        handlerBindingResult(bindingResult);

        createChatService.createChat(createChatDTO);

        log.info("Чат создан");

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("{chatId}/add-new-user")
    public ResponseEntity<HttpStatus> addNewUser(@PathVariable("chatId") Integer chatId,
                                                 @RequestBody AddUserChatDTO addUserChatDTO,
                                                 BindingResult bindingResult) {
        log.info("add new user in chat [%s]".formatted(chatId));

        handlerBindingResult(bindingResult);

        User newUser = userService.findByUsername(addUserChatDTO.getUsername());
        Chat currentChat = chatService.findById(chatId);
        currentChat.getUsers().add(newUser);
        newUser.getChats().add(currentChat);

        userService.update(newUser);
        chatService.save(currentChat);
        log.info("%s added in chat[%s]".formatted(addUserChatDTO.getUsername(), chatId));

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("{chatId}/delete")
    public ResponseEntity<HttpStatus> deleteChat(@PathVariable("chatId") Integer chatId) {
        log.info("start deleting a chat[%s]".formatted(chatId));
        Chat chat = chatService.findById(chatId);


        messageService.deleteByAll(chat.getMessages());
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
