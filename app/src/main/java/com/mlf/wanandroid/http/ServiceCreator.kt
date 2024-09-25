package com.mlf.wanandroid.http


import com.mlf.wanandroid.http.interceptor.AddCookiesInterceptor
import com.mlf.wanandroid.http.interceptor.ReceivedCookiesInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * @description: TODO service构造器
 * @author: mlf
 * @date: 2024/9/7 20:28
 * @version: 1.0
 */
object  ServiceCreator {
    private const val baseUrl: String = "https://www.wanandroid.com/"

    private var retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .client(getClient())
        .build()

    private fun getClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(AddCookiesInterceptor())
            .addInterceptor(ReceivedCookiesInterceptor())
            .connectTimeout(10, TimeUnit.SECONDS)
            .build()
    }

    fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)
    inline fun <reified T> create(): T = create(T::class.java)
}