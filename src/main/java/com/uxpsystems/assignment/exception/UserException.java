package com.uxpsystems.assignment.exception;

/**
 * Generic exception throws when any User specific operation failed. inherited
 * by multiple exception generated while performing user specific CRUD
 * operations.
 * 
 * @author Umesh.Chavan
 *
 */
public class UserException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = 7477727836088475182L;

    public UserException(String message) {
        super(message);
    }

    public UserException(String message, Throwable th) {
        super(message, th);
    }
}
