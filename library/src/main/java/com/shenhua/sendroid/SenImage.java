package com.shenhua.sendroid;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.DrawableRes;
import android.util.LruCache;

import com.shenhua.sendroid.image.CancelableRequestDelegate;
import com.shenhua.sendroid.image.ImageRequest;
import com.shenhua.sendroid.image.ImageTypeRequest;

import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * Created by shenhua on 2017/7/23.
 * Email shenhuanet@126.com
 */
public class SenImage {

    public static final int MSG_CACHE_HINT = 0x110;
    public static final int MSG_CACHE_UN_HINT = MSG_CACHE_HINT + 1;
    public static final int MSG_HTTP_GET_ERROR = MSG_CACHE_UN_HINT + 1;
    public static final int MSG_HTTP_GET_SUCCESS = MSG_HTTP_GET_ERROR + 1;
    public static final int MSG_LOCAL_GET_SUCCESS = MSG_HTTP_GET_SUCCESS + 1;
    public static final int MSG_LOCAL_GET_ERROR = MSG_LOCAL_GET_SUCCESS + 1;
    private static SenImage sInstance = null;
    private int defaultThreadCount = 4;
    private LruCache<String, Bitmap> mLruCache;
    private ExecutorService mThreadPool;
    private LinkedList<Runnable> mTaskQueue;
    private Thread mPoolThread;
    private Handler mPoolThreadHandler;
    private Handler mUIHandler;
    private Semaphore mSemaphorePoolThreadHandler = new Semaphore(0);
    private Semaphore mSemaphoreThreadPool;
    private boolean isDiskCacheEnable = true;
    private CancelableRequestDelegate mCancelableRequestDelegate = new CancelableRequestDelegate();

    public static SenImage with(Context context) {
        if (sInstance == null) {
            synchronized (SenImage.class) {
                if (sInstance == null) {
                    sInstance = new SenImage(context);
                }
            }
        }
        return sInstance;
    }

    private SenImage(Context context) {
        intBackThread();
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cacheMemory = maxMemory / 8;
        mLruCache = new LruCache<String, Bitmap>(cacheMemory) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes() * value.getHeight();
            }
        };
        mThreadPool = Executors.newFixedThreadPool(defaultThreadCount);
        mTaskQueue = new LinkedList<Runnable>();
        mSemaphoreThreadPool = new Semaphore(defaultThreadCount);
    }

    private void intBackThread() {
        mPoolThread = new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                mPoolThreadHandler = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        /**
                         * 线程池取出一个任务进行执行
                         */
                        mThreadPool.execute(getTask());
                        try {
                            mSemaphoreThreadPool.acquire();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                mSemaphoreThreadPool.release();
                Looper.loop();
            }
        };
        mPoolThread.start();
    }

    /**
     * 先进先出方式
     *
     * @return Runnable
     */
    private Runnable getTask() {
        return mTaskQueue.removeFirst();
    }

    public ImageRequest load(@DrawableRes int drawableId) {
        final ImageRequest req = ImageTypeRequest.buildImageRequest(drawableId);
        final String cacheKey = req.getCacheKey();
        // mCancelableRequestDelegate.putRequest();
        // Bitmap bitmap = mLruCache.get(cacheKey);
        return req.request();
    }

//    private ImageSize getImageViewSize(ImageView imageView) {
//        DisplayMetrics displayMetrics = imageView.getContext().getResources().getDisplayMetrics();
//        ViewGroup.LayoutParams lp = imageView.getLayoutParams();
//        int width = imageView.getWidth();
//        if (width <= 0) {
//            width = lp.width;
//        }
//        if (width <= 0) {
//            width = getImageViewFieldValue(imageView, "mMaxWidth");
//        }
//        if (width <= 0) {
//            width = displayMetrics.widthPixels;
//        }
//        int height = imageView.getHeight();
//        if (height <= 0) {
//            height = lp.height;
//        }
//        if (height <= 0) {
//            height = getImageViewFieldValue(imageView, "mMaxHeight");
//        }
//        if (height <= 0) {
//            height = displayMetrics.heightPixels;
//        }
//        return new ImageSize(width, height);
//    }
}
