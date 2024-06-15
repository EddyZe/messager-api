package ru.eddyz.messagerapi.models.DTO;


import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class MessageDTO {

    private Integer id;
    private String text;
    private LocalDateTime createAt;
    private String senderUsername;

}
