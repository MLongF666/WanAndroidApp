package com.mlf.wanandroid.model.response

data class ArticleResponse(
    val `data`: Data,
    val errorCode: Int,
    val errorMsg: String
)