package com.cash_manager.api.security;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.cash_manager.api.User;
import com.cash_manager.api.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class SeedDataConfig implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        
      if (userRepository.count() == 0) {

        User admin = User
                      .builder()
                      .nom("admin")
                      .email("admin@admin.com")
                      .password(passwordEncoder.encode("DEV_13"))
                      .admin(true)
                      .build();

        userRepository.save(admin);
        log.debug("created ADMIN user - {}", admin);
      }
    }

}