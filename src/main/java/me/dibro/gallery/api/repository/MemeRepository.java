package me.dibro.gallery.api.repository;

import me.dibro.gallery.api.model.Meme;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MemeRepository extends JpaRepository<Meme, UUID> {
}
