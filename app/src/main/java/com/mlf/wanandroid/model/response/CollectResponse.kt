package com.mlf.wanandroid.model.response

data class CollectResponse(
    val `data`: Any,
    val errorCode: Int,
    val errorMsg: String
)