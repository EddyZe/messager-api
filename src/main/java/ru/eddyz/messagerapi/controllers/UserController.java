package ru.eddyz.messagerapi.controllers;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.eddyz.messagerapi.exeption.UserInvalidException;
import ru.eddyz.messagerapi.models.entities.User;
import ru.eddyz.messagerapi.services.UserService;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("user")
public class UserController {

    private final UserService userService;



    @PostMapping
    public ResponseEntity<HttpStatus> createUser(@RequestBody @Valid User user,
                                                 BindingResult bindingResult) {
        log.info("crating new user");
        handlerBindingResult(bindingResult);
        userService.save(user);
        log.info("a new user has been created");
        return ResponseEntity.ok(HttpStatus.OK);
    }



    private void handlerBindingResult(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorsMessage = new StringBuilder();

            bindingResult.getFieldErrors().forEach(fieldError -> errorsMessage
                    .append(fieldError.getField()
                            .replaceAll("([A-Z]+)([A-Z][a-z])", "$1_$2")
                            .replaceAll("([a-z])([A-Z])", "$1_$2")
                            .toLowerCase())
                    .append(" - ")
                    .append(fieldError.getDefaultMessage())
                    .append("; "));

            throw new UserInvalidException(errorsMessage.toString());
        }
    }
}
