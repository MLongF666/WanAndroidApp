package com.mlf.wanandroid.ui.project

import androidx.lifecycle.MutableLiveData

import com.mlf.wanandroid.base.BaseViewModel

class ProjectViewModel : BaseViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "项目"
    }
    val text: MutableLiveData<String> = _text

}
