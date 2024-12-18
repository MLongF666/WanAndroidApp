package com.mlf.wanandroid.ui.navigtion

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

import com.mlf.wanandroid.base.BaseViewModel

class NavigationViewModel : BaseViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "导航"
    }
    val text: LiveData<String> = _text

}
