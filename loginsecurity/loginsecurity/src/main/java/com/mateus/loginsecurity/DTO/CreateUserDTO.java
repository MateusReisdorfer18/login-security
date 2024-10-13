package com.mateus.loginsecurity.DTO;

import com.mateus.loginsecurity.enums.RoleEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateUserDTO(
        @NotNull @NotBlank String name,
        @NotNull @NotBlank String email,
        @NotNull @NotBlank String password,
        @NotNull RoleEnum role) {
}
