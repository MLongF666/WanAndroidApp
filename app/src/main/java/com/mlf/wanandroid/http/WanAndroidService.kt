package com.mlf.wanandroid.http

import com.mlf.wanandroid.model.response.LoginResponse
import com.mlf.wanandroid.model.response.RegisterResponse
import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * @description: TODO
 * @author: mlf
 * @date: 2024/9/7 12:03
 * @version: 1.0
 */
interface WanAndroidService {
    @POST("user/login")
    fun login(@Query("username") username: String,@Query("password") password: String): Call<LoginResponse>
    @POST("user/register")
    fun register(@Query("username")username: String,@Query("password") password: String,@Query("repassword") repassword: String): Call<RegisterResponse>

}