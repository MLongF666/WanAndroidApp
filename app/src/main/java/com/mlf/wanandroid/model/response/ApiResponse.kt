package com.mlf.wanandroid.model.response

data class ApiResponse<T>(
    val data: T,
    val errorCode: Int,
    val errorMsg: String
)
