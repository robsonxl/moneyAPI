package com.money.api.exceptionHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class MoneyExceptionHandler extends ResponseEntityExceptionHandler{
	
	@Autowired
	private MessageSource messageSource;
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		String businessMsg = messageSource.getMessage("mensagem.invalida", null, LocaleContextHolder.getLocale());
		String errorMsg = ex.getCause()!=null? ex.getCause().toString(): ex.toString();
		List<Error> errors = Arrays.asList(new Error(businessMsg, errorMsg));
		return handleExceptionInternal(ex,errors, headers, HttpStatus.BAD_REQUEST, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
			List<Error> errors = errorList(ex.getBindingResult());	
		return handleExceptionInternal(ex, errors, headers, HttpStatus.BAD_REQUEST, request); 
	}
	
	private List<Error> errorList(BindingResult bindingResult){
		List<Error> errorList = new ArrayList<MoneyExceptionHandler.Error>();
		for (FieldError fieldError : bindingResult.getFieldErrors()) {
			String businessMsg = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
			String errorMsg = fieldError.toString();
			errorList.add(new Error(businessMsg, errorMsg));
		}
		return errorList;
	}
	
	@ExceptionHandler({DataIntegrityViolationException.class})
	public ResponseEntity<Object> dataIntegrityViolationException(DataIntegrityViolationException ex, WebRequest request){
		List<Error> errorList = new ArrayList<MoneyExceptionHandler.Error>();
		String businessMsg = messageSource.getMessage("recurso.operacao.nao.permitida", null, LocaleContextHolder.getLocale());
		String errorMsg = ExceptionUtils.getRootCauseMessage(ex);
		errorList.add(new Error(businessMsg, errorMsg));
		return handleExceptionInternal(ex, errorList, new HttpHeaders(), HttpStatus.BAD_REQUEST, request); 
	} 
	
	@ExceptionHandler({EmptyResultDataAccessException.class})
	public ResponseEntity<Object> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex, WebRequest request) {
		String businessMsg = messageSource.getMessage("recurso.nao-encontrado", null, LocaleContextHolder.getLocale());
		String errorMsg = ex.getCause()!=null? ex.getCause().toString(): ex.toString();
		List<Error> errors = Arrays.asList(new Error(businessMsg, errorMsg));
		return handleExceptionInternal(ex, errors, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}
	
	public static class Error{
		
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

}
