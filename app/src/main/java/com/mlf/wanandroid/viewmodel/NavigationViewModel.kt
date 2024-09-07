package com.mlf.wanandroid.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

import com.mlf.wanandroid.base.BaseViewModel

class NavigationViewModel :BaseViewModel(){
    private val _text = MutableLiveData<String>().apply {
        value = "This is navigation Fragment"
    }
    val text: LiveData<String> = _text

}
