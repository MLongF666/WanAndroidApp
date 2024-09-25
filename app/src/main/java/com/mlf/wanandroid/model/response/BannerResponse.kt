package com.mlf.wanandroid.model.response

data class BannerResponse(
    val `data`: List<BannerData>,
    val errorCode: Int,
    val errorMsg: String
)