package com.mlf.wanandroid.dao

import com.mlf.wanandroid.http.HttpManage
import com.mlf.wanandroid.model.bean.PageResponse
import com.mlf.wanandroid.model.response.ApiResponse
import com.mlf.wanandroid.model.response.Article

import com.mlf.wanandroid.model.response.BannerData


object ArticleRepository {
    suspend fun getHomeArticleList(page: Int): ApiResponse<PageResponse<Article>> {
        return HttpManage.getHomeArticleList(page)
    }

    suspend fun getBannerList(): ApiResponse<List<BannerData>> {
        return HttpManage.getBanner()
    }

    suspend fun collectArticle(id: Int): ApiResponse<Any?> {
        return HttpManage.collectArticle(id)
    }

    suspend fun uncollected(id: Int): ApiResponse<Any?> {
        return HttpManage.unCollectArticle(id)
    }

    suspend fun getSquareArticleList(page: Int): ApiResponse<PageResponse<Article>> {
        return HttpManage.getSquareArticleList(page)
    }

    suspend fun getQaArticleList(page: Int): ApiResponse<PageResponse<Article>> {
        return HttpManage.getQaArticleList(page)
    }

}