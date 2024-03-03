package com.bookroles.exception;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class AssertUtil {

	final static Logger logger = LoggerFactory.getLogger(AssertUtil.class);

	public static void isNull(Object object, String message) {
		if (object == null) {
			logger.error(message);
			throw new BussinessException(message);
		}
	}
	
	public static void notNull(Object object, String message) {
		if (object != null) {
			logger.error(message);
			throw new BussinessException(message);
		}
	}

	public static void isStringEmpty(String str, String message) {
		if (str == null || str.trim().isEmpty()) {
			logger.error(message);
			throw new BussinessException(message);
		}
	}

	public static void notStringEmpty(String str, String message) {
		if (str != null && !str.trim().isEmpty()) {
			logger.error(message);
			throw new BussinessException(message);
		}
	}
	
	public static void isTrue(boolean expression, String message) {
		if (expression) {
			logger.error(message);
			throw new BussinessException(message);
		}
	}
	
	public static void isFalse(boolean expression, String message) {
		if (!expression) {
			logger.error(message);
			throw new BussinessException(message);
		}
	}
	
	
}
