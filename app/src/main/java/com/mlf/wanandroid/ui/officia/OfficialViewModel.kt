package com.mlf.wanandroid.ui.officia

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

import com.mlf.wanandroid.base.BaseViewModel

class OfficialViewModel : BaseViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "公众号"
    }
    val text: LiveData<String> = _text

}
