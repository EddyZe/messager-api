package ru.eddyz.messagerapi.models.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "message")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Message implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private int messageId;

    @Column(name = "sender_username")
    private String senderUsername;

    @NotEmpty(message = "Вы не можете отправить пустое сообщение!")
    @Column(name = "text_message")
    private String textMessage;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "message_from_chat", referencedColumnName = "chat_id")
    private Chat chat;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

}
