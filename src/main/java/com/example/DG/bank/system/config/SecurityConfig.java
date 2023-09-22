package com.example.DG.bank.system.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;


@Configuration
@EnableWebSecurity
public class SecurityConfig {


    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails userAhmed = User.withUsername("Ahmed")
                .password(encoder().encode("moderesta"))
                .roles("CUSTOMER")
                .build();

        UserDetails userEmployee = User.withUsername("reda")
                .password(encoder().encode("redaa"))
                .roles("EMPLOYEE")
                .build();

        UserDetails userAdminstrator = User.withUsername("hesham")
                .password(encoder().encode("heshamm"))
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(userAhmed, userEmployee,userAdminstrator);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers(HttpMethod.GET, "/customer/**").hasRole("CUSTOMER")
                                .requestMatchers(HttpMethod.POST, "/customer/**").hasRole("CUSTOMER")
                                .requestMatchers(HttpMethod.PUT, "/customer/**").hasRole("CUSTOMER")
                                .requestMatchers(HttpMethod.DELETE, "/customer/**").hasRole("EMPLOYEE")
                                .requestMatchers("/account/**").hasRole("EMPLOYEE")
                                .requestMatchers("/employee/**").hasRole("ADMIN")
                                .requestMatchers( "/transaction/**").hasRole("EMPLOYEE")


                )
                .httpBasic(withDefaults()).csrf(csrf -> csrf.disable());


        return http.build();
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

}
