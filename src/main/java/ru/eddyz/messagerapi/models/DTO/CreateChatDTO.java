package ru.eddyz.messagerapi.models.DTO;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import ru.eddyz.messagerapi.models.entities.User;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CreateChatDTO {


    @NotEmpty(message = "Название чата не может быть пустым!")
    @Size(min = 3, message = "Название чата не может быть короче чем 3 символа")
    private String chatName;


    @NotNull(message = "Список участников не может быть пуст!")
    private List<AddUserChatDTO> users;

}
