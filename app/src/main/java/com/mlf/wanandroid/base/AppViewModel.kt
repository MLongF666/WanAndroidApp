package com.mlf.wanandroid.base

import androidx.lifecycle.MutableLiveData
import com.mlf.wanandroid.model.bean.CollectData

class AppViewModel : BaseViewModel() {
    /** 文章收藏 */
    val collectEvent = MutableLiveData<CollectData>()
}