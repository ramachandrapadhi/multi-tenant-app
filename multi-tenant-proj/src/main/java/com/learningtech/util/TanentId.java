package com.learningtech.util;

public enum TanentId {
	
	TANENT_TYPE_ADMIN(1001),
	TANENT_TYPE_HR(1002),
	TANENT_TYPE_FINANCE(1003),
	TANENT_TYPE_TECHNICAL(1004);

	public final Long tId;
	
	TanentId(long tId) {
		this.tId = tId;
	}
}
