package com.mateus.loginsecurity.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LoginUserDTO(@NotNull @NotBlank String email, @NotNull @NotBlank String password) {
}
