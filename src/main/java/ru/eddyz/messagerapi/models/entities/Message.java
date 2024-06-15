package ru.eddyz.messagerapi.models.entities;


import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("message_id")
    @Column(name = "message_id")
    private int messageId;

    @JsonProperty("sender_username")
    @Column(name = "sender_username")
    private String senderUsername;

    @NotEmpty(message = "Вы не можете отправить пустое сообщение!")
    @JsonProperty("text_message")
    @Column(name = "text_message")
    private String textMessage;

    @ManyToOne
    @JoinColumn(name = "message_from_chat", referencedColumnName = "id")
    private Chat chat;

    @JsonProperty("message_id")
    @Column(name = "message_id")
    private LocalDateTime createdAt;

}
