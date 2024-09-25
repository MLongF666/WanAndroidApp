package com.mlf.wanandroid.dao

import android.util.Log
import com.mlf.wanandroid.dao.UserRepository.fire
import com.mlf.wanandroid.http.HttpManage
import com.mlf.wanandroid.model.response.ApiResponse

import com.mlf.wanandroid.model.response.ArticleResponse
import com.mlf.wanandroid.model.response.BannerData

import kotlinx.coroutines.Dispatchers


object ArticleRepository {
    fun getArticleList(page: Int)= fire(Dispatchers.IO){
        Log.d("LoginRepository", "getHomeArticleList page: $page")
        val response: ArticleResponse = HttpManage.getHomeArticleList(page)
        Result.success(response)
    }

    suspend fun getBannerList(): ApiResponse<List<BannerData>> {
        return HttpManage.getBanner()
    }

        suspend fun collectArticle(id: Int):ApiResponse<Any?>{
            Log.d("LoginRepository", "collectArticle id: $id")
        return HttpManage.collectArticle(id)
    }
    suspend fun uncollect(id: Int):ApiResponse<Any?>{
        return HttpManage.unCollectArticle(id)
    }
}