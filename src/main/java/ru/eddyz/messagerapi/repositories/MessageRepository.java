package ru.eddyz.messagerapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.eddyz.messagerapi.models.entities.Message;

import java.util.Optional;


@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

    Optional<Message> findBySenderUsername(String username);
    Optional<Message> findByTextMessageContainsIgnoreCase(String text);
}
