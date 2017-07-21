package com.shenhua.sendroid.annotation.view;

import android.support.annotation.MenuRes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by shenhua on 2017-07-21.
 * Email shenhuanet@126.com
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface BindView {

    int viewId() default -1;// view id

    String onClick() default "";

    String onItemClick() default "";

    String onLongClick() default "";

    Select select() default @Select(onSelected = "");

    boolean supportToolbar() default false;

    boolean toolbarHomeAsUp() default false;// 是否可返回

    boolean hasOptionsMenu() default false;// 是否允许溢出菜单（仅Fragment中使用）

    @MenuRes int menuId() default -1;// 菜单Id
}