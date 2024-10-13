package com.mateus.loginsecurity.service;

import com.mateus.loginsecurity.DTO.CreateUserDTO;
import com.mateus.loginsecurity.DTO.LoginUserDTO;
import com.mateus.loginsecurity.DTO.ResponseLoginDTO;
import com.mateus.loginsecurity.DTO.UserDTO;
import com.mateus.loginsecurity.config.SecurityConfig;
import com.mateus.loginsecurity.entity.Role;
import com.mateus.loginsecurity.entity.User;
import com.mateus.loginsecurity.entity.UserDetailsImpl;
import com.mateus.loginsecurity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final SecurityConfig securityConfig;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenService jwtTokenService;

    public ResponseLoginDTO authenticateUser(LoginUserDTO loginUserDTO) {
        User user = this.userRepository.findByEmail(loginUserDTO.email()).get();
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(loginUserDTO.email(), loginUserDTO.password());
        Authentication authentication = this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        UserDTO userDTO = UserDTO.builder()
                .name(user.getName())
                .email(user.getEmail()).
                build();

        return new ResponseLoginDTO(userDTO, this.jwtTokenService.generateToken(userDetails));
    }

    public User createUser(CreateUserDTO createUserDTO) {
        try {
            User user = User.builder()
                    .name(createUserDTO.name())
                    .email(createUserDTO.email())
                    .password(this.securityConfig.passwordEncoder().encode(createUserDTO.password()))
                    .role(List.of(Role.builder().name(createUserDTO.role()).build()))
                    .build();

            return this.userRepository.save(user);
        } catch (RuntimeException exception) {
            throw new RuntimeException("Houve um erro ao criar usuário", exception);
        }
    }
}
