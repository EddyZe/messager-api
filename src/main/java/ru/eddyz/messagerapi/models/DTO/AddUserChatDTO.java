package ru.eddyz.messagerapi.models.DTO;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AddUserChatDTO {

    @NotEmpty(message = "Укажите username пользователя, которого хотите добавить!")
    @JsonProperty("username")
    private String username;

}
