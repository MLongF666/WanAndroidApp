package com.mlf.wanandroid.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

import com.mlf.wanandroid.base.BaseViewModel

class OfficialViewModel:BaseViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "This is official Fragment"
    }
    val text: LiveData<String> = _text

}
