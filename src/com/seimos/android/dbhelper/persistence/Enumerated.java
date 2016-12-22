package com.seimos.android.dbhelper.persistence;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author moesio @ gmail.com
 * @date Dec 21, 2016 9:04:21 PM
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD) 
public @interface Enumerated {
	EnumType value() default EnumType.ORDINAL;
}
