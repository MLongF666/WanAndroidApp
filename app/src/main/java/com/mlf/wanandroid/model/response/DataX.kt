package com.mlf.wanandroid.model.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
@Parcelize
data class BannerData(
    val desc: String,
    val id: Int,
    val imagePath: String="",
    val isVisible: Int,
    val order: Int,
    val title: String,
    val type: Int,
    val url: String
): Parcelable