package com.mlf.wanandroid.http

import com.mlf.wanandroid.model.response.Hotkey
import com.mlf.wanandroid.model.bean.PageResponse
import com.mlf.wanandroid.model.response.ApiResponse
import com.mlf.wanandroid.model.response.Article
import com.mlf.wanandroid.model.response.BannerData
import com.mlf.wanandroid.model.response.LoginResponse
import com.mlf.wanandroid.model.response.RegisterResponse
import retrofit2.Call
import retrofit2.http.GET
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
    fun login(
        @Query("username") username: String,
        @Query("password") password: String
    ): Call<LoginResponse>

    @POST("user/register")
    fun register(
        @Query("username") username: String,
        @Query("password") password: String,
        @Query("repassword") repassword: String
    ): Call<RegisterResponse>

    @GET("article/list/{page}/json")
    suspend fun getHomeArticleList(@Path("page") page: Int): ApiResponse<PageResponse<Article>>

    @GET("banner/json")
    suspend fun getBanner(): ApiResponse<List<BannerData>>

    @POST("lg/collect/{id}/json")
    suspend fun collectArticle(@Path("id") id: Int): ApiResponse<Any?>

    @POST("lg/uncollect_originId/{id}/json")
    suspend fun unCollectArticle(@Path("id") id: Int): ApiResponse<Any?>

    @GET("user_article/list/{page}/json")
    suspend fun getSquareArticleList(@Path("page") page: Int): ApiResponse<PageResponse<Article>>

    @GET("wenda/list/{page}/json ")
    suspend fun getQaArticleList(@Path("page") page: Int): ApiResponse<PageResponse<Article>>

    @GET("hotkey/json")
    suspend fun getHotKeyList(): ApiResponse<List<Hotkey>>

    @POST("article/query/{page}/json")
    suspend fun searchArticleList(
        @Path("page") page: Int,
        @Query("k") content: String
    ): ApiResponse<PageResponse<Article>>

}