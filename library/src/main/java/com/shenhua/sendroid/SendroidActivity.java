package com.shenhua.sendroid;

import android.app.Activity;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

import com.shenhua.sendroid.annotation.view.BindView;
import com.shenhua.sendroid.annotation.view.OnRecyclerItemClick;
import com.shenhua.sendroid.annotation.view.Select;
import com.shenhua.sendroid.annotation.view.inner.ViewListener;
import com.shenhua.sendroid.annotation.view.inner.ListenerClass;
import com.shenhua.sendroid.annotation.view.inner.MethodIntercepter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class SendroidActivity extends AppCompatActivity {

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        bindView(this);
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
        bindView(this);
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        bindView(this);
    }

    public static void bindView(Activity activity) {
        bindView(activity, activity.getWindow().getDecorView());
    }

    public static void bindView(Object object, View view) {
        Field[] fields = object.getClass().getDeclaredFields();
        if (fields != null && fields.length > 0) {
            for (Field field : fields) {
                try {
                    field.setAccessible(true);
                    if (field.get(object) != null) {
                        continue;
                    }
                    BindView bindView = field.getAnnotation(BindView.class);
                    if (bindView != null) {
                        int viewId = bindView.viewId();
                        field.set(object, view.findViewById(viewId));
                    }
                    Select select = field.getAnnotation(Select.class);
                    if (select != null) {
                        if (!TextUtils.isEmpty(select.onSelected())) {
                            if (object instanceof View) {
                                ((AbsListView) object).setOnItemSelectedListener(
                                        new ViewListener(object).select(select.onSelected())
                                                .unSelect(select.unSelected()));
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        viewClickListener(object, view);
    }

    private static void viewClickListener(Object object, View view) {
        Method[] methods = object.getClass().getDeclaredMethods();
        if (methods != null && methods.length > 0) {
            for (Method method : methods) {
                Annotation[] annotations = method.getAnnotations();
                for (Annotation annotation : annotations) {
                    Class<? extends Annotation> type = annotation.annotationType();
                    if (type != null) {
                        ListenerClass listener = type.getAnnotation(ListenerClass.class);
                        if (listener != null) {
                            String callback = listener.callback();
                            Class target = listener.targetType();
                            String setListener = listener.setter();
                            try {
                                Method declaredMethod = type.getDeclaredMethod("value");
                                int[] values = (int[]) declaredMethod.invoke(annotation);
                                MethodIntercepter intercepter = new MethodIntercepter(object);
                                intercepter.add(callback, method);
                                Proxy proxy = (Proxy) Proxy.newProxyInstance(target.getClassLoader(),
                                        new Class[]{target}, intercepter);
                                for (int valutesId : values) {
                                    View v = view.findViewById(valutesId);
                                    Method listenerMethod = v.getClass().getMethod(setListener, target);
                                    listenerMethod.invoke(v, proxy);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        OnRecyclerItemClick recyclerListener = type.getAnnotation(OnRecyclerItemClick.class);
                        if (recyclerListener != null) {

                        }
                    }
                }
            }
        }
    }

}
