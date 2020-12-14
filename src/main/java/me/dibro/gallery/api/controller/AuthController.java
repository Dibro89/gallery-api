package me.dibro.gallery.api.controller;

import lombok.extern.slf4j.Slf4j;
import me.dibro.gallery.api.security.JwtHelper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Slf4j
public class AuthController {

    @PostMapping("/auth")
    public Object auth(@RequestBody Map<String, Object> user) {
        String token = JwtHelper.buildJwt(user.get("id").toString());
        return Map.of("token", token);
    }
}
