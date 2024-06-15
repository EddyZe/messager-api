package ru.eddyz.messagerapi.models.entities;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "chat")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Chat implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("chat_id")
    @Column(name = "chat_id")
    private int chatId;

    @NotEmpty(message = "Название чата не должно быть пустым!")
    @Column(name = "chat_name")
    private String chatName;

    @OneToMany(mappedBy = "chat")
    private List<Message> messages;

    @ManyToMany(mappedBy = "chats")
    private List<User> users;

    @JsonProperty("created_at")
    @Column(name = "created_at")
    private LocalDateTime createdAt;

}
