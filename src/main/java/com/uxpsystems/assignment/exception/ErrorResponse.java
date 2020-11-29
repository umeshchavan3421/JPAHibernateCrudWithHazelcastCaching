package com.uxpsystems.assignment.exception;

import lombok.Data;

@Data
public class ErrorResponse {

	private String timestamp;
	private String errorMessage;
	private String errorDetails;

	public ErrorResponse(String timestamp, String errorMessage, String errorDetails) {
		super();
		this.timestamp = timestamp;
		this.errorMessage = errorMessage;
		this.errorDetails = errorDetails;
	}

}
