package com.mlf.wanandroid.http

import com.mlf.wanandroid.model.response.LoginResponse
import com.mlf.wanandroid.model.response.RegisterResponse
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


/**
 * @description: TODO Http管理
 * @author: mlf
 * @date: 2024/9/7 12:04
 * @version: 1.0
 */
object HttpManage {
    private const val baseUrl = "https://www.wanandroid.com/"

    private var retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private var service: WanAndroidService = retrofit.create(WanAndroidService::class.java)
    fun login(username: String, password: String,callback: Callback<LoginResponse>)= service.login(username, password).enqueue(callback)
    fun register(username: String, password: String, repassword: String,callback: Callback<RegisterResponse>)= service.register(username, password, repassword).enqueue(callback)
}