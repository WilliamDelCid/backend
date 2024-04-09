package com.backend.backend.security.exceptions;

import com.backend.backend.security.dto.Mensaje;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;
import java.security.SignatureException;
import java.util.ArrayList;
import java.util.List;

/**
 * Manejador de excepciones globales para el controlador REST.
 */
@RestControllerAdvice
public class RestControllerException {

    /**
     * Maneja excepciones genéricas.
     *
     * @param e La excepción que se produjo.
     * @return ResponseEntity que contiene un mensaje de error interno del servidor.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Mensaje> handleException(Exception e){
        return ResponseEntity.internalServerError()
                .body(new Mensaje(e.getMessage()));
    }

    /**
     * Maneja excepciones personalizadas.
     *
     * @param e La excepción personalizada que se produjo.
     * @return ResponseEntity que contiene un mensaje de error personalizado.
     */
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Mensaje> handleCustomException(CustomException e){

        return ResponseEntity.status(e.getStatus())
                .body(new Mensaje(e.getMessage()));
    }

    /**
     * Maneja excepciones de credenciales no válidas.
     *
     * @param e La excepción de credenciales no válidas que se produjo.
     * @return ResponseEntity que contiene un mensaje de error de "no encontrado" con estado HTTP 404.
     */
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Mensaje> handleBadCredentialsException(BadCredentialsException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new Mensaje(e.getMessage()));
    }

    /**
     * Maneja excepciones de acceso denegado.
     *
     * @param e La excepción de acceso denegado que se produjo.
     * @return ResponseEntity que contiene un mensaje de error de "prohibido" con estado HTTP 403.
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Mensaje> handleAccessDeniedException(AccessDeniedException e){
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new Mensaje(e.getMessage()));
    }

    /**
     * Maneja excepciones de validación de argumentos del método.
     *
     * @param e La excepción de validación de argumentos del método que se produjo.
     * @return ResponseEntity que contiene un mensaje de error de "solicitud incorrecta" con estado HTTP 400.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Mensaje> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<String> mensajes = new ArrayList<>();

        for (ObjectError error : e.getBindingResult().getAllErrors()) {
            if (error instanceof FieldError) {
                FieldError fieldError = (FieldError) error;
                mensajes.add(fieldError.getField() + ": " + error.getDefaultMessage());
            } else {
                mensajes.add(error.getDefaultMessage());
            }
        }

        String mensajeConcatenado = String.join(",", mensajes);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new Mensaje(mensajeConcatenado));
    }

    /**
     * Maneja excepciones relacionadas con JWT.
     *
     * @param e La excepción JWT que se produjo.
     * @return ResponseEntity que contiene un mensaje de error de "prohibido" con estado HTTP 403.
     */
    @ExceptionHandler(value = {MalformedJwtException.class, UnsupportedJwtException.class, IllegalArgumentException.class, SignatureException.class})
    public ResponseEntity<Mensaje> jwtException(JwtException e){
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new Mensaje(e.getMessage()));
    }
}
