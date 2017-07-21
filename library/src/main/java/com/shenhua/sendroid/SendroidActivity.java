package com.shenhua.sendroid;

import android.app.Activity;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

import com.shenhua.sendroid.annotation.view.BindView;
import com.shenhua.sendroid.annotation.view.MethodIntercepter;
import com.shenhua.sendroid.annotation.view.ViewListener;
import com.shenhua.sendroid.annotation.view.ViewOption;
import com.shenhua.sendroid.annotation.view.inner.ListenerClass;

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
//                        viewListener(object, field, bindView.onClick(), ViewOption.Click);
//                        viewListener(object, field, bindView.onItemClick(), ViewOption.ItemClick);
//                        viewListener(object, field, bindView.onLongClick(), ViewOption.LongClick);
//                        Select select = bindView.select();
//                        if (!TextUtils.isEmpty(select.onSelected())) {
//                            Object obj = field.get(object);
//                            if (obj instanceof View) {
//                                ((AbsListView) obj).setOnItemSelectedListener(new ViewListener(obj)
//                                        .select(select.onSelected()).unSelect(select.unSelected()));
//                            }
//                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        Method[] methods = object.getClass().getDeclaredMethods();
        if (methods != null && methods.length > 0) {
            for (Method method : methods) {
                Annotation[] annotations = method.getAnnotations();
                for (Annotation annotation : annotations) {
                    Class<? extends Annotation> annotationType = annotation.annotationType();
                    if (annotationType != null) {
                        ListenerClass event = annotationType.getAnnotation(ListenerClass.class);
                        if (event != null) {
                            String callback = event.callback();
                            Class type = event.targerType();
                            String setLister = event.setter();
                            try {
                                Method declaredMethod = annotationType.getDeclaredMethod("value");
                                int[] values = (int[]) declaredMethod.invoke(annotation);
                                MethodIntercepter intercepter = new MethodIntercepter(object);
                                intercepter.add(callback, method);
                                Proxy proxy = (Proxy) Proxy.newProxyInstance(type.getClassLoader(),
                                        new Class[]{type}, intercepter);
                                for (int valutesId : values) {
                                    View v = view.findViewById(valutesId);
                                    Method listener = v.getClass().getMethod(setLister, type);
                                    listener.invoke(v, proxy);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
    }

    private static void viewListener(Object object, Field field, String type, ViewOption option) throws Exception {
        if (!TextUtils.isEmpty(type) && type.trim().length() > 0) {
            Object obj = field.get(object);
            switch (option) {
                case Click:
                    if (obj instanceof View) {
                        ((View) obj).setOnClickListener(new ViewListener(object).click(type));
                    }
                    break;
                case ItemClick:
                    if (obj instanceof AbsListView) {
                        ((AbsListView) obj).setOnItemClickListener(new ViewListener(object).itemClick(type));
                    }
//                if (obj instanceof RecyclerView){
//
//                }
                    break;
                case LongClick:
                    if (obj instanceof View) {
                        ((View) obj).setOnLongClickListener(new ViewListener(object).longClick(type));
                    }
                    break;
            }
        }
    }
}
