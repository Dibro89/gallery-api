package me.dibro.gallery.api.controller;

import lombok.extern.slf4j.Slf4j;
import me.dibro.gallery.api.model.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @GetMapping
    public Map<String, Object> info(@AuthenticationPrincipal User user) {
        return Map.of("user", user);
    }
}
