package com.backend.backend.security.exceptions;

import org.springframework.http.HttpStatus;

/**
 * Excepción personalizada para representar errores específicos dentro de la lógica de la aplicación.
 */
public class CustomException extends RuntimeException {

    private HttpStatus status;

    /**
     * Crea una nueva instancia de CustomException con un estado HTTP y un mensaje.
     *
     * @param status  El estado HTTP asociado con la excepción.
     * @param message El mensaje de la excepción.
     */
    public CustomException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    /**
     * Obtiene el estado HTTP asociado con la excepción.
     *
     * @return El estado HTTP.
     */
    public HttpStatus getStatus() {
        return status;
    }
}
