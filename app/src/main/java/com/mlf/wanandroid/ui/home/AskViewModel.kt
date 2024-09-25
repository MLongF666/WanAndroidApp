package com.mlf.wanandroid.ui.home

import com.mlf.wanandroid.base.BaseViewModel
import com.mlf.wanandroid.util.SingleLiveData

class AskViewModel :BaseViewModel() {
    //text
    val text = SingleLiveData<String>().apply {
        value = "This is home Fragment"
    }

}
