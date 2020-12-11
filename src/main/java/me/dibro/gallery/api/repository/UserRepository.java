package me.dibro.gallery.api.repository;

import me.dibro.gallery.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByTelegramId(long telegramId);
}
