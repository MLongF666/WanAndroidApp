package com.mlf.wanandroid.http.interceptor;

import androidx.annotation.NonNull;

import com.mlf.wanandroid.base.BaseApp;
import com.mlf.wanandroid.room.AppDatabase;
import com.mlf.wanandroid.util.Shape;

import java.io.IOException;
import java.util.Set;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @description: TODO
 * @author: mlf
 * @date: 2024/9/12 13:45
 * @version: 1.0
 */
public class AddCookiesInterceptor implements Interceptor {

    @NonNull
    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        final Request.Builder builder = chain.request().newBuilder();
        //添加Cookie
        Set<String> cookies = Shape.getSetString("cookie",null);
        if (cookies!=null){
            for (String string:cookies) {
                builder.addHeader("Cookie",string);
            }
        }
        return chain.proceed(builder.build());
    }
}
