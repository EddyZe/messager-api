package ru.eddyz.messagerapi.models.entities;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("user_id")
    @Column(name = "user_id")
    private int userId;

    @NotEmpty (message = "username не должен быть пустым!")
    @Column(name = "username")
    private String username;

    @JsonProperty("first_name")
    @Column (name = "first_name")
    private String firstName;

    @JsonProperty("last_name")
    @Column (name = "last_name")
    private String lastName;

    @ManyToMany
    @JoinTable(
            name = "participant_chats",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "chat_id")
    )
    private List<Chat> chats;

}