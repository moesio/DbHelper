package com.seimos.android.dbhelper.persistence;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author moesio @ gmail.com
 * @date Jul 30, 2015 2:03:39 PM
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD) 
public @interface Id {

}
