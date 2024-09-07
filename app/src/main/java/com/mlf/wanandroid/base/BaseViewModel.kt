package com.mlf.wanandroid.base

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel

/**
 * @description: TODO 父类ViewModel
 * @author: mlf
 * @date: 2024/9/7 12:59
 * @version: 1.0
 */
open class BaseViewModel: ViewModel() {
    fun String.showToast(context: Context, duration:Int = Toast.LENGTH_SHORT){
        Toast.makeText(context, this,duration).show()
    }
    fun Int.showToast(context: Context, duration:Int = Toast.LENGTH_SHORT){
        Toast.makeText(context, this, duration).show()
    }
}