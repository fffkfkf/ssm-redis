package com.dell.anli01filter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
 * 我们自定义的权限注解,只要servlet的方法上,添加了这个注解,则证明这个方法需要权限才能访问,否则不需要权限;
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface MyQX {
    public abstract String value() default "user";

}
