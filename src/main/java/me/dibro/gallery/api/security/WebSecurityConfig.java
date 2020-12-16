package me.dibro.gallery.api.security;

import lombok.RequiredArgsConstructor;
import me.dibro.gallery.api.repository.UserRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserRepository userRepository;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http
            .csrf()
                .disable()
            .cors()
                .configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues())
            .and()
            .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/auth").permitAll()
                .anyRequest().authenticated()
            .and()
            .addFilter(new JwtAuthenticationFilter(authenticationManager(), userRepository));
        // @formatter:on

        // TODO: json errors
    }
}
