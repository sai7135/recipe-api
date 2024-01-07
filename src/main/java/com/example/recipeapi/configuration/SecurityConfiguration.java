package com.example.recipeapi.configuration;

import com.example.recipeapi.filters.RequestFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final AuthenticationProvider provider;
    private final RequestFilter requestFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http
                .csrf(conf->conf.disable())
                .authorizeHttpRequests(conf->{
                    conf
                            .requestMatchers("/api/auth/**")
                            .permitAll()
                            .anyRequest()
                            .authenticated();
                })
                .sessionManagement(conf->{
                    conf.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })
                .authenticationProvider(provider)
                .addFilterBefore(requestFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
