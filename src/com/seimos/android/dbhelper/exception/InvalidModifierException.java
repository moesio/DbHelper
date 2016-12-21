package com.seimos.android.dbhelper.exception;

import java.lang.reflect.Field;

import com.seimos.android.dbhelper.persistence.BaseEntity;

/**
 * Thrown when a invalid modifier of an entity is used
 * 
 * @author moesio @ gmail.com
 * @date Dec 11, 2016 12:19:28 AM
 */
public class InvalidModifierException extends RuntimeException {

	public InvalidModifierException(Class<? extends BaseEntity> entityClass, Field field) {
		super("Invalid modifier is used to field " + field.getName() + " in class " + entityClass.getCanonicalName());
	}
}
