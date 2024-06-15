package ru.eddyz.messagerapi.services;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.eddyz.messagerapi.exeption.MessageNotFoundException;
import ru.eddyz.messagerapi.models.entities.Message;
import ru.eddyz.messagerapi.repositories.MessageRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;

    public void save (Message message) {
        messageRepository.save(message);
    }

    public void deleteById(Integer messageId) {
        messageRepository.deleteById(messageId);
    }

    public void deleteByAll(List<Message> messages) {
        messageRepository.deleteAll(messages);
    }

    public Message findByUsername (String senderUsername) {
        return messageRepository.findBySenderUsername(senderUsername)
                .orElseThrow(() -> new MessageNotFoundException("Сообщение с таким отправителем не найдено!"));
    }

    public Message findByText (String text) {
        return messageRepository.findByTextMessageContainsIgnoreCase(text)
                .orElseThrow(() -> new MessageNotFoundException("Сообщения с таким текстом не найдены!"));
    }

    public Message findById (Integer messageId) {
        return messageRepository.findById(messageId)
                .orElseThrow(() -> new MessageNotFoundException("Сообщение с таким ID не найдено!"));
    }
}
