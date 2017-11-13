package com.shenhua.sendroid.image;

import java.util.Locale;

/**
 * Created by shenhua on 2017-11-13-0013.
 *
 * @author shenhua
 *         Email shenhuanet@126.com
 */
public enum Scheme {

    HTTP("http"),
    HTTPS("https"),
    FILE("file"),
    CONTENT("content"),
    DRAWABLE("drawable"),
    ASSETS("assets"),
    UNKNOWN("");

    private String scheme;
    private String uriPrefix;

    Scheme(String scheme) {
        this.scheme = scheme;
        uriPrefix = scheme + "://";
    }

    public static Scheme ofUri(String uri) {
        if (uri != null) {
            for (Scheme s : values()) {
                if (s.belongsTo(uri)) {
                    return s;
                }
            }
        }
        return UNKNOWN;
    }

    public boolean belongsTo(String uri) {
        return uri.toLowerCase(Locale.US).startsWith(uriPrefix);
    }

    public String wrap(String path) {
        return uriPrefix + path;
    }

    public String crop(String uri) {
        if (!belongsTo(uri)) {
            throw new IllegalArgumentException(String.format("URI [%1$s] doesn't have expected scheme [%2$s]", uri, scheme));
        }
        return uri.substring(uriPrefix.length());
    }
}
