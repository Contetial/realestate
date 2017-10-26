package com.contetial.realEstate.persistance.exception;

public class DataAccessLayerException extends RuntimeException{
	 /**
	 * 
	 */
	private static final long serialVersionUID = -8985895157379658073L;

	
		public DataAccessLayerException() {
	    }

	    /**
	     * @param message
	     */
	    public DataAccessLayerException(String message) {
	        super(message);
	    }

	    /**
	     * @param cause
	     */
	    public DataAccessLayerException(Throwable cause) {
	        super(cause);
	    }

	    /**
	     * @param message
	     * @param cause
	     */
	    public DataAccessLayerException(String message, Throwable cause) {
	        super(message, cause);
	    }

}
