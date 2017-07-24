package com.shenhua.sendroid.annotation.view.inner;

import android.view.View;
import android.widget.AdapterView;

import java.lang.reflect.Method;

/**
 * Created by shenhua on 2017-07-21.
 * Email shenhuanet@126.com
 */
public class ViewListener implements View.OnClickListener, AdapterView.OnItemClickListener,
        View.OnLongClickListener, AdapterView.OnItemSelectedListener {

    private Object object;
    private String clickMethod;
    private String itemClickMethod;
    private String longClickMethod;
    private String itemLongClickMehtod;
    private String itemSelectMethod;
    private String itemUnSelectMethod;

    public ViewListener(Object object) {
        this.object = object;
    }

    public ViewListener click(String method) {
        clickMethod = method;
        return this;
    }

    @Override
    public void onClick(View v) {
        if (object == null) return;
        try {
            Method method = object.getClass().getDeclaredMethod(clickMethod, View.class);
            if (method != null) {
                method.invoke(object, v);
            } else {
                throw new Exception("no such method:" + clickMethod);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ViewListener itemClick(String method) {
        this.itemClickMethod = method;
        return this;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (object == null) return;
        try {
            Method method = object.getClass().getDeclaredMethod(itemClickMethod, AdapterView.class,
                    View.class, int.class, long.class);
            if (method != null) {
                method.invoke(object, view);
            } else {
                throw new Exception("no such method:" + itemClickMethod);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ViewListener longClick(String method) {
        longClickMethod = method;
        return this;
    }

    @Override
    public boolean onLongClick(View v) {
        if (object == null) return false;
        try {
            Method method = object.getClass().getDeclaredMethod(longClickMethod, View.class);
            if (method != null) {
                Object obj = method.invoke(object, v);
                return obj == null ? false : Boolean.valueOf(obj.toString());
            } else {
                throw new Exception("no such method:" + itemClickMethod);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public ViewListener select(String method) {
        itemSelectMethod = method;
        return this;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (object == null) return;
        try {
            Method method = object.getClass().getDeclaredMethod(itemSelectMethod, AdapterView.class,
                    View.class, int.class, long.class);
            if (method != null) {
                method.invoke(object, view);
            } else {
                throw new Exception("no such method:" + itemSelectMethod);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ViewListener unSelect(String method) {
        itemUnSelectMethod = method;
        return this;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        if (object == null) return;
        try {
            Method method = object.getClass().getDeclaredMethod(itemUnSelectMethod, AdapterView.class);
            if (method != null) {
                method.invoke(object, parent);
            } else {
                throw new Exception("no such method:" + itemUnSelectMethod);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
