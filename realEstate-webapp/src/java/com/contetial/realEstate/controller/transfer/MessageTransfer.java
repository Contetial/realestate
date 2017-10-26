package com.contetial.realEstate.controller.transfer;

public class MessageTransfer {

	private final String message;

	public MessageTransfer(String message){
		this.message = message;
	}

	public String getMessage(){
		return this.message;
	}
}
