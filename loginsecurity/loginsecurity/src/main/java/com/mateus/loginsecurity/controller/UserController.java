package com.mateus.loginsecurity.controller;

import com.mateus.loginsecurity.DTO.CreateUserDTO;
import com.mateus.loginsecurity.DTO.LoginUserDTO;
import com.mateus.loginsecurity.DTO.ResponseLoginDTO;
import com.mateus.loginsecurity.entity.User;
import com.mateus.loginsecurity.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/authenticated")
    public ResponseEntity<String> authenticatedCommon() {
        return ResponseEntity.ok("Você está autenticado");
    }

    @GetMapping("/authenticated/customer")
    public ResponseEntity<String> authenticatedCustomer() {
        return ResponseEntity.ok("Você está autenticado como cliente");
    }

    @GetMapping("/authenticated/seller")
    public ResponseEntity<String> authenticatedSeller() {
        return ResponseEntity.ok("Você está autenticado como vendedor");
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseLoginDTO> authenticateUser(@RequestBody @Valid LoginUserDTO loginUserDTO) {
        ResponseLoginDTO responseLoginDTO = this.userService.authenticateUser(loginUserDTO);
        if(responseLoginDTO == null)
            return ResponseEntity.badRequest().build();

        return ResponseEntity.ok(responseLoginDTO);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody @Valid CreateUserDTO createUserDTO) {
        return ResponseEntity.ok(this.userService.createUser(createUserDTO));
    }
}
