package com.mlf.wanandroid.ui.mine

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

import com.mlf.wanandroid.base.BaseViewModel

/**
 * @description: TODO
 * @author: mlf
 * @date: 2024/9/6 15:44
 * @version: 1.0
 */
class MineViewModel : BaseViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "我的"
    }
    val text: LiveData<String> = _text
}