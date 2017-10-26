/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.contetial.realEstate.utility.exception;

/**
 *
 * @author kalpana_nagle
 */
public class ServiceException extends Exception {

    /**
	 * 
	 */
	private static final long serialVersionUID = 6854807370283529795L;
	private String message = null;

    public ServiceException() {
        super();
    }

    public ServiceException(String message) {
        super(message);
        this.message = message;
    }
    
    public ServiceException(String message, Throwable cause) {
        super(message,cause);
        this.message = message;
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

    @Override
    public String toString() {
        return message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
