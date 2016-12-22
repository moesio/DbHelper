package com.seimos.android.dbhelper.exception;

/**
 * @author moesio @ gmail.com
 * @date Dec 11, 2016 2:09:51 PM
 */
public class ReflectionException extends RuntimeException {

	public ReflectionException(String message) {
		super(message);
	}

	public ReflectionException(Exception e) {
		super(e);
	}

}
