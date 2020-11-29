package com.uxpsystems.assignment.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception throws when any User not found while searching.
 * 
 * @author Umesh.Chavan
 *
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends UserException {

    /**
     * 
     */
    private static final long serialVersionUID = 7477727836088475182L;

    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(String message, Throwable th) {
        super(message, th);
    }
}
