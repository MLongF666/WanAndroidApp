package com.mlf.wanandroid.viewmodel

import androidx.lifecycle.MutableLiveData

import com.mlf.wanandroid.base.BaseViewModel

class ProjectViewModel: BaseViewModel() {
    private val _text=MutableLiveData<String>().apply {
        value="This is project Fragment"
    }
    val text:MutableLiveData<String> = _text

}
