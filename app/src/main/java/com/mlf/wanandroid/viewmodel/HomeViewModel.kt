package com.mlf.wanandroid.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mlf.wanandroid.base.BaseViewModel
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener
import com.scwang.smart.refresh.layout.listener.OnRefreshListener

class HomeViewModel : BaseViewModel(){

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }

}