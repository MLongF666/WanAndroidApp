package com.mlf.wanandroid.http.interceptor;

import android.util.Log;

import androidx.annotation.NonNull;

import com.mlf.wanandroid.http.NetClient;
import com.mlf.wanandroid.util.Shape;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * @description: TODO
 * @author: mlf
 * @date: 2024/9/12 13:47
 * @version: 1.0
 */

public class ReceivedCookiesInterceptor implements Interceptor {
    @NonNull
    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {

        Response originalResponse = chain.proceed(chain.request());
        if (!originalResponse.headers("Set-Cookie").isEmpty()) {
            //解析Cookie
            HashSet<String> cookies = new HashSet<>(originalResponse.headers("Set-Cookie"));
            Shape.setStringSet("cookie",cookies);
        }
        Log.d("ReceivedCookiesInterceptor", "ReceivedCookiesInterceptor: " + NetClient.COOKIE);
        return originalResponse;
    }

}
