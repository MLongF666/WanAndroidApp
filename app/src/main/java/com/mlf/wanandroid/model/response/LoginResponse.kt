package com.mlf.wanandroid.model.response

data class LoginResponse(
    val `data`: LoginData,
    val errorCode: Int,
    val errorMsg: String
)