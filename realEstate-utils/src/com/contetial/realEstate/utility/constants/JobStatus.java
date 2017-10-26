package com.contetial.realEstate.utility.constants;

public enum JobStatus {

	NEW("N"), IN_PROGRESS("P"), SUSPRNDED("S"), COMPLETED("C"), ERRORED("E");
	 
	private String statusCode;
 
	private JobStatus(String s) {
		statusCode = s;
	}
 
	public String getStatusCode() {
		return statusCode;
	}
	
}
