package com.seimos.android.dbhelper.persistence;

import java.lang.annotation.Target;
import java.lang.annotation.Retention;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author moesio @ gmail.com
 * @date Dec 17, 2016 10:43:55 PM
 */
@Target({ METHOD, FIELD })
@Retention(RUNTIME)
public @interface Temporal {
	TemporalType value();
}
