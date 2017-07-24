package com.shenhua.sendroid.annotation.view;

import android.view.View;

import com.shenhua.sendroid.annotation.view.inner.ListenerClass;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by shenhua on 2017-07-21.
 * Email shenhuanet@126.com
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@ListenerClass(
        targetType = View.OnClickListener.class,
        setter = "setOnClickListener",
        callback = "onClick"
)
public @interface OnClick {

    int[] value() default {View.NO_ID};
}
