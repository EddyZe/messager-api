package ru.eddyz.messagerapi.services;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.eddyz.messagerapi.exeption.UserInvalidException;
import ru.eddyz.messagerapi.exeption.UserNotFoundException;
import ru.eddyz.messagerapi.models.entities.User;
import ru.eddyz.messagerapi.repositories.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public void save(User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent())
            throw new UserInvalidException("Пользователь с таким username же существует!");

        userRepository.save(user);
    }

    @Transactional
    public void update(User user) {
        userRepository.save(user);
    }


    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UserNotFoundException("Пользователь с таким username не найден"));
    }


    @Transactional
    public void updateAll(List<User> users) {
        userRepository.saveAll(users);
    }

    @Transactional
    public void deleteByUsername(String username) {
        if (userRepository.findByUsername(username).isEmpty())
            throw new UserNotFoundException("Пользователь с таким username не найден");

        userRepository.deleteByUsername(username);
    }

}
