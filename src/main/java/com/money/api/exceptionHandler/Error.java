package com.money.api.exceptionHandler;

public class Error {

	private String businessMsg;
	private String errorMsg;
	
	public Error(String businessMsg, String errorMsg) {
		this.businessMsg = businessMsg;
		this.errorMsg =  errorMsg;
	}
	public String getBusinessMsg() {
		return businessMsg;
	}

	public void setBusinessMsg(String businessMsg) {
		this.businessMsg = businessMsg;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}	
}
