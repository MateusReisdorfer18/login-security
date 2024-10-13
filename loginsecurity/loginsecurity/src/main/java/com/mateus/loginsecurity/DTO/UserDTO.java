package com.mateus.loginsecurity.DTO;

import lombok.Builder;

@Builder
public record UserDTO(String name, String email) {
}
