package me.dibro.gallery.api.repository;

import me.dibro.gallery.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
