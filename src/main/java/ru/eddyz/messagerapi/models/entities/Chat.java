package ru.eddyz.messagerapi.models.entities;


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
    @Column(name = "chat_id")
    private int chatId;

    @NotEmpty(message = "Название чата не должно быть пустым!")
    @Column(name = "chat_name")
    private String chatName;

    @Column(name = "owner_username")
    private String ownerUsername;

    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL)
    private List<Message> messages;

    @ManyToMany(mappedBy = "chats", cascade = CascadeType.ALL)
    private List<User> users;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

}
