package com.mlf.wanandroid.http

import android.graphics.pdf.PdfDocument.Page
import com.mlf.wanandroid.model.response.Hotkey
import com.mlf.wanandroid.model.bean.PageResponse
import com.mlf.wanandroid.model.response.ApiResponse
import com.mlf.wanandroid.model.response.Article
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * @description: TODO Http管理
 * @author: mlf
 * @date: 2024/9/7 12:04
 * @version: 1.0
 */
object HttpManage {
    private val service=ServiceCreator.create<WanAndroidService>()
    suspend fun login(username: String, password: String)= service.login(username, password).await()
    suspend fun register(username: String, password: String, repassword: String)= service.register(username, password, repassword).await()
    suspend fun getBanner()= service.getBanner()
    suspend fun getHomeArticleList(page: Int)= service.getHomeArticleList(page)

    suspend fun unCollectArticle(id: Int)= service.unCollectArticle(id)
    suspend fun collectArticle(id: Int)= service.collectArticle(id)
    private suspend fun <T> Call<T>.await(): T {
        return suspendCoroutine { continuation ->
            enqueue(object : Callback<T> {
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body()
                    if (body != null) continuation.resume(body)
                    else continuation.resumeWithException(RuntimeException("response body is null"))
                }

                override fun onFailure(call: Call<T>, t: Throwable) {
                    continuation.resumeWithException(t)
                }
            })
        }
    }

    suspend fun getSquareArticleList(page: Int)= service.getSquareArticleList(page)
    suspend fun getQaArticleList(page: Int): ApiResponse<PageResponse<Article>> {
        return service.getQaArticleList(page)
    }
    suspend fun getHotKeyList(): ApiResponse<List<Hotkey>> {
        return service.getHotKeyList()
    }

    suspend fun searchArticleList(content: String,page: Int): ApiResponse<PageResponse<Article>> {
        return service.searchArticleList( page,content)
    }


}