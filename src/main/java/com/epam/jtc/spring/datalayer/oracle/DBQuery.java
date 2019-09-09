package com.epam.jtc.spring.datalayer.oracle;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Defines method containing sql query
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DBQuery {
    /**
     * Query text
     *
     * @return text
     */
    String text();
}
