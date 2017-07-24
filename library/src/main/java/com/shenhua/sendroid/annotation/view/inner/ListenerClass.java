package com.shenhua.sendroid.annotation.view.inner;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by shenhua on 2017-07-21.
 * Email shenhuanet@126.com
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ListenerClass {

    Class targetType();

    String setter();

    String callback();
}
