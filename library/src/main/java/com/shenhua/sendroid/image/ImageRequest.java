package com.shenhua.sendroid.image;

import android.graphics.Bitmap;
import android.util.LruCache;
import android.widget.ImageView;

import com.shenhua.sendroid.image.dispatcher.LocalDispatcher;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by shenhua on 2017-11-13-0013.
 *
 * @author shenhua
 *         Email shenhuanet@126.com
 */
public class ImageRequest {

    private Scheme scheme;
    private ImageView mImageView;
    private Bitmap bitmap;

    private LruCache<String, Bitmap> mLruCache;
    private ExecutorService mTaskDistribute = Executors.newFixedThreadPool(1);
    private LocalDispatcher mLocalDispatcher;
    private volatile BlockingQueue<ImageRequest> mLocalQueue;
    private CacheDispatcher mCacheDispatcher;
    private volatile BlockingQueue<ImageRequest> mCacheQueue;
    private NetworkDispatcher mNetworkDispatcher;
    private volatile BlockingQueue<ImageRequest> mNetworkQueue;
    private DiskLruCacheHelper mDiskLruCacheHelper;


    public ImageRequest(Scheme scheme) {
        this.scheme = scheme;

    }


    public String getCacheKey() {

    }

    public void into(ImageView imageView) {
        if (imageView == null) {
            return;
        }
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
        } else {
//            imageView.setErrorHolder();
        }
    }

    public ImageRequest request() {

        return null;
    }
}
