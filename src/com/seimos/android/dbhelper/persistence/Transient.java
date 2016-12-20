package com.seimos.android.dbhelper.persistence;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author moesio @ gmail.com
 * @date Dec 20, 2016 2:38:01 PM
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD) 
public @interface Transient {

}
