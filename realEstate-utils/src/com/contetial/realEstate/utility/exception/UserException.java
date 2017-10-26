package com.contetial.realEstate.utility.exception;

public class UserException extends Exception{

	
	private static final long serialVersionUID = 6006408636543279834L;
	
	private String message = null;
		 
		    public UserException() {
		        super();
		    }
		 
		    public UserException(String message) {
		        super(message);
		        this.message = message;
		    }
		 
		    public UserException(Throwable cause) {
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
