package com.backend.backend.security.entity;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class MainUsuario implements UserDetails {

    @Getter
    private final String nombre;
    private final String nombreUsuario;
    @Getter
    private final String email;
    private final String password;
    private final GrantedAuthority rol;

    public MainUsuario(String nombre, String nombreUsuario, String email, String password, GrantedAuthority rol) {
        this.nombre = nombre;
        this.nombreUsuario = nombreUsuario;
        this.email = email;
        this.password = password;
        this.rol = rol;
    }

    /**
     * Método estático para construir un objeto MainUsuario a partir de un objeto Usuario.
     *
     * @param usuario El objeto Usuario del que se creará el MainUsuario.
     * @return Un objeto MainUsuario construido a partir del objeto Usuario dado.
     */
    public static MainUsuario build(Usuario usuario) {
        GrantedAuthority rol = new SimpleGrantedAuthority(usuario.getRol().getNombreRol());
        return new MainUsuario(usuario.getNombre(), usuario.getNombreUsuario(), usuario.getEmail(), usuario.getPassword(), rol);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(rol);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return nombreUsuario;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


}
