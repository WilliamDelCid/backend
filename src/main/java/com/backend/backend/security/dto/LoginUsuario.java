package com.backend.backend.security.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginUsuario {
    private String nombreUsuario;
    private String password;
}
