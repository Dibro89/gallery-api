package me.dibro.gallery.api.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.dibro.gallery.api.model.Meme;
import me.dibro.gallery.api.model.User;
import me.dibro.gallery.api.repository.UserRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class TestDataLoader implements ApplicationRunner {

    private final UserRepository userRepository;

    @Override
    public void run(ApplicationArguments args) {
        User admin = new User();
        admin.setTelegramId(0L);
        admin.setName("ADMIN");

        Meme meme1 = new Meme();
        meme1.setImageUri("https://via.placeholder.com/600?text=111");

        Meme meme2 = new Meme();
        meme2.setImageUri("https://via.placeholder.com/600?text=222");

        Meme meme3 = new Meme();
        meme3.setImageUri("https://via.placeholder.com/600?text=333");

        Meme meme4 = new Meme();
        meme4.setImageUri("https://via.placeholder.com/600?text=444");

        Meme meme5 = new Meme();
        meme5.setImageUri("https://via.placeholder.com/600?text=555");

        admin.setMemes(List.of(meme1, meme2, meme3, meme4, meme5));

        userRepository.save(admin);
    }
}
