package me.dibro.gallery.api.security;

import me.dibro.gallery.api.model.User;
import me.dibro.gallery.api.repository.UserRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private static final String AUTHENTICATION_SCHEME_BEARER = "Bearer" + " ";

    private final UserRepository userRepository;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserRepository userRepository) {
        super(authenticationManager);
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String token = getToken(request);
        if (token != null) {
            String subject = JwtHelper.parseJwt(token);
            if (subject != null) {
                SecurityContextHolder.getContext().setAuthentication(createAuthentication(subject));
            } else {
                SecurityContextHolder.clearContext();
            }
        }
        chain.doFilter(request, response);
    }

    private Authentication createAuthentication(String subject) {
        Long telegramId = Long.valueOf(subject);
        User user = userRepository.findById(telegramId).orElseThrow();
        return new PreAuthenticatedAuthenticationToken(user, null, null);
    }

    private String getToken(HttpServletRequest request) {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header != null && header.startsWith(AUTHENTICATION_SCHEME_BEARER)) {
            return header.substring(AUTHENTICATION_SCHEME_BEARER.length());
        }
        return null;
    }
}
