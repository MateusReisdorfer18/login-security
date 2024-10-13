package com.mateus.loginsecurity.DTO;

public record ResponseLoginDTO(UserDTO user, String token) {
}
