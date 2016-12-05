package com.seimos.android.dbhelper.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.seimos.android.dbhelper.database.EnumType;

/**
 * @author moesio @ gmail.com
 * @date Dec 5, 2016 8:20:12 PM
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Enumerated {
	EnumType value() default EnumType.ORDINAL;
}
