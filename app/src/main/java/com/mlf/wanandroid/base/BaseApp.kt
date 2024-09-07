package com.mlf.wanandroid.base

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

/**
 * @description: TODO
 * @author: mlf
 * @date: 2024/9/6 16:38
 * @version: 1.0
 */
open  class BaseApp:Application() {
    companion object{
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }
    override fun onCreate() {
        super.onCreate()
        context=applicationContext
    }
}