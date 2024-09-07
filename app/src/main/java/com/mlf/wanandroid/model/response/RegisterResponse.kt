package com.mlf.wanandroid.model.response

data class RegisterResponse(
    val `data`: RegisterData,
    val errorCode: Int,
    val errorMsg: String
)