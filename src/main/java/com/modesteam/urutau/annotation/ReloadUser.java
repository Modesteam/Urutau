package com.modesteam.urutau.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.inject.Qualifier;

import com.modesteam.urutau.interceptor.ReloaderInterceptor;

/**
 * This annotation is used to notify an method that user logged should be reloaded 
 * after your invocation. See {@link ReloaderInterceptor} to see how it works.
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER, ElementType.METHOD})
public @interface ReloadUser {
}
