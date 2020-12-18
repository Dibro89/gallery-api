package me.dibro.gallery.api.controller;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import me.dibro.gallery.api.model.User;
import me.dibro.gallery.api.repository.UserRepository;
import me.dibro.gallery.api.security.JwtHelper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@Slf4j
public class AuthController {

    private final UserRepository userRepository;

    @Value("${bot.token}")
    private String botToken;

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/auth")
    public Map<String, Object> auth(@RequestBody Map<String, Object> authData) {
        if (!verify(authData)) {
            return null;
        }
        User user = toUser(authData);
        String subject = String.valueOf(user.getTelegramId());
        String token = JwtHelper.buildJwt(subject);
        return Map.of("token", token);
    }

    private User toUser(Map<String, Object> authData) {
        User user = new User();
        user.setName((String) authData.get("first_name"));
        user.setTelegramId(Long.parseLong(authData.get("id").toString()));
        return userRepository.save(user);
    }

    // https://core.telegram.org/widgets/login
    private boolean verify(Map<String, Object> authData) {
        byte[] hash = Hex.decode((String) authData.remove("hash"));

        byte[] dataCheckString = authData.entrySet().stream()
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .sorted()
                .collect(Collectors.joining("\n"))
                .getBytes(StandardCharsets.UTF_8);

        byte[] secretKey = SHA256(botToken
                .getBytes(StandardCharsets.UTF_8));

        return MessageDigest.isEqual(hash, HMAC_SHA256(dataCheckString, secretKey));
    }

    @SneakyThrows
    private byte[] SHA256(byte[] data) {
        final String algorithm = "SHA-256";
        MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
        return messageDigest.digest(data);
    }

    @SneakyThrows
    private byte[] HMAC_SHA256(byte[] data, byte[] key) {
        final String algorithm = "HmacSHA256";
        Mac mac = Mac.getInstance(algorithm);
        mac.init(new SecretKeySpec(key, algorithm));
        return mac.doFinal(data);
    }
}
