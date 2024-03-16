package com.learningtech.respmodel;

import lombok.Data;

@Data
public class GenericResponse {
	private Boolean status;
	private Integer statusCode;
	private String message;
	private Object data;
}
