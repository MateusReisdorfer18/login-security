package com.mateus.loginsecurity.service;

import com.mateus.loginsecurity.entity.User;
import com.mateus.loginsecurity.entity.UserDetailsImpl;
import com.mateus.loginsecurity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsSerivceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userRepository.findByEmail(username).orElseThrow(() -> new RuntimeException("User not found"));
        return UserDetailsImpl.builder()
                .user(user)
                .build();
    }
}
