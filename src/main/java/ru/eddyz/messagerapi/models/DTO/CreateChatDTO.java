package ru.eddyz.messagerapi.models.DTO;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CreateChatDTO {


    @NotEmpty(message = "Название чата не может быть пустым!")
    @Size(min = 3, message = "Название чата не может быть короче чем 3 символа")
    @JsonProperty("chat_name")
    private String chatName;

    private String ownerUsername;

    @NotNull(message = "Список участников не может быть пуст!")
    @JsonProperty("users")
    private List<AddUserChatDTO> users;

}
