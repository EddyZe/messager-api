package ru.eddyz.messagerapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.eddyz.messagerapi.models.entities.Chat;

import java.util.List;


@Repository
public interface ChatReposotory extends JpaRepository<Chat, Integer> {

    List<Chat> findByChatName(String chatName);
}
