package ru.eddyz.messagerapi.controllers;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.eddyz.messagerapi.exeption.ChatNotFoundException;
import ru.eddyz.messagerapi.exeption.MessageInvalidException;
import ru.eddyz.messagerapi.models.DTO.MessageDTO;
import ru.eddyz.messagerapi.models.DTO.NewMessageDto;
import ru.eddyz.messagerapi.models.entities.Chat;
import ru.eddyz.messagerapi.models.entities.Message;
import ru.eddyz.messagerapi.models.entities.User;
import ru.eddyz.messagerapi.services.ChatService;
import ru.eddyz.messagerapi.services.MessageService;
import ru.eddyz.messagerapi.services.UserService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("message")
public class MessageController {

    private final UserService userService;
    private final MessageService messageService;
    private final ChatService chatService;


    @GetMapping("{username}/{chatId}/messages")
    public List<MessageDTO> getChatMessages(@PathVariable String username,
                                            @PathVariable Integer chatId) {
        User user = userService.findByUsername(username);
        Chat chat = user.getChats()
                .stream()
                .filter(c -> c.getChatId() == chatId)
                .findAny()
                .orElseThrow(() -> new ChatNotFoundException("Вы не участник данного чата!"));


        return chat.getMessages().stream().map(c ->
                MessageDTO.builder()
                        .id(c.getMessageId())
                        .text(c.getTextMessage())
                        .createAt(c.getCreatedAt())
                        .senderUsername(c.getSenderUsername())
                        .build())
                .collect(Collectors.toList());
    }

    @PostMapping("{username}/{chatId}/new-message")
    public ResponseEntity<HttpStatus> newMessage(@PathVariable("username") String username,
                                                 @PathVariable("chatId") Integer chatId,
                                                 @RequestBody @Valid NewMessageDto newMessageDto,
                                                 BindingResult bindingResult) {

        handlerBindingResult(bindingResult);

        User user = userService.findByUsername(username);

        Chat chat = user.getChats().stream().filter(c -> c.getChatId() == chatId).findAny()
                .orElseThrow(() -> new ChatNotFoundException("Вы не участник данного чата!"));

        Message newMessage = createNewMessage(newMessageDto, chat, user);

        chat.getMessages().add(newMessage);

        messageService.save(newMessage);
        chatService.update(chat);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    private Message createNewMessage(NewMessageDto newMessageDto, Chat chat, User user) {
        return Message.builder()
                .textMessage(newMessageDto.getText())
                .chat(chat)
                .senderUsername(user.getUsername())
                .createdAt(LocalDateTime.now())
                .build();
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

            throw new MessageInvalidException(errorsMessage.toString());
        }
    }

}
