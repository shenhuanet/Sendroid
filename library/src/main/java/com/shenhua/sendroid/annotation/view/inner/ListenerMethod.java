package com.shenhua.sendroid.annotation.view.inner;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by shenhua on 2017-07-21.
 * Email shenhuanet@126.com
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ListenerMethod {

    String name();

    String[] parameters() default {};

    String returnType() default "void";

    String defaultReture() default "null";

}
