package com.backend.backend.security.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class NuevoUsuario {

    private String nombre;

    @NotBlank(message = "nombre vacio")
    private String nombreUsuario;

    @Email(message = "dirección de email no válida")
    private String email;

    private String password;

    private int rolId;

}
