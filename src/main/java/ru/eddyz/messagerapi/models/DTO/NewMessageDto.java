package ru.eddyz.messagerapi.models.DTO;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewMessageDto {

    @JsonProperty("text")
    @NotEmpty(message = "Сообщение не может быть пустым!")
    private String text;
}
