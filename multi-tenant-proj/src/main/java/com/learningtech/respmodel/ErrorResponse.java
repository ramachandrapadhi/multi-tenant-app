package com.learningtech.respmodel;

import lombok.Data;

@Data
public class ErrorResponse {
	  private Boolean status;
	  private Integer statusCode;
	  private String message;
}
