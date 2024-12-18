package com.mlf.wanandroid.model.bean

import android.icu.text.CaseMap.Title
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class ArticleData(
    var id: Int = 0,
    var link: String = "",
    var title: String = "",
    var collect: Boolean = false
) : Parcelable