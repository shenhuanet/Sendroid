package com.shenhua.sendroid.image;

import android.support.annotation.DrawableRes;
import android.util.Log;

/**
 * Created by shenhua on 2017-11-13-0013.
 *
 * @author shenhua
 *         Email shenhuanet@126.com
 */
public class ImageTypeRequest {

    public static ImageRequest buildImageRequest(@DrawableRes int resId) {
        // TODO: 2017-11-13-0013
        Log.d("shenhuaLog -- " + ImageTypeRequest.class.getSimpleName(), "buildImageRequest: >>> " + resId);
        return new ImageRequest(Scheme.DRAWABLE);
    }
}
