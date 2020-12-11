package me.dibro.gallery.api.security;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

@Slf4j
public class JwtAuthenticationFilter extends GenericFilterBean {

    private static final AntPathRequestMatcher REQUEST_MATCHER = new AntPathRequestMatcher("/auth", "POST");

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        doFilter((HttpServletRequest) request, (HttpServletResponse) response, chain);
    }

    private void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (!REQUEST_MATCHER.matches(request)) {
            chain.doFilter(request, response);
            return;
        }

        TypeReference<Map<String, Object>> typeReference = new TypeReference<>() {};
        Map<String, Object> data = new ObjectMapper().readValue(request.getInputStream(), typeReference);

        logger.info("data: " + data);

        String token = Jwts.builder()
                .setClaims(Jwts.claims().setSubject(data.get("id").toString()))
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(SignatureAlgorithm.HS256, "HelloWorldHelloWorldHelloWorldHelloWorldHelloWorld")
                .compact();

        response.getWriter().println(token);
    }
}
