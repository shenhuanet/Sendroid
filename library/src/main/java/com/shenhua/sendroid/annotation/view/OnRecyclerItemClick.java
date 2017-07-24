package com.shenhua.sendroid.annotation.view;

import android.widget.AdapterView;

import com.shenhua.sendroid.annotation.view.inner.ListenerClass;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by shenhua on 2017-07-24-0024.
 * Email shenhuanet@126.com
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@ListenerClass(
        // TODO: 2017-07-24-0024
        targetType = AdapterView.OnItemClickListener.class,
        setter = "setOnItemClickListener",
        callback = "onItemClick"
)
/**
 * void onItemClick(View view, int position, T data);
 */
public @interface OnRecyclerItemClick {

    int value();
}
