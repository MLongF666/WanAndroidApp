package com.mlf.wanandroid.base

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.ViewModelFactoryDsl
import com.mlf.wanandroid.util.Shape

/**
 * @description: TODO
 * @author: mlf
 * @date: 2024/9/6 16:38
 * @version: 1.0
 */
open  class BaseApp:Application(),ViewModelStoreOwner {
    private var mFactory: ViewModelProvider.Factory? = null
    private lateinit var mAppViewModelStore: ViewModelStore
    companion object{
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }
    override fun onCreate() {
        super.onCreate()
        context=applicationContext
        mAppViewModelStore = ViewModelStore()
        BaseRepository.initialize(this)
        Shape.init(this)
    }
    fun getAppViewModelProvider(): ViewModelProvider {
        return ViewModelProvider(this, getAppViewModelFactory())
    }
    private fun getAppViewModelFactory(): ViewModelProvider.Factory {
        if (mFactory == null) {
            mFactory = ViewModelProvider.AndroidViewModelFactory.getInstance(this)
        }
        return mFactory as ViewModelProvider.Factory
    }

    override val viewModelStore: ViewModelStore
        get() = mAppViewModelStore
}