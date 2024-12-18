package com.mlf.wanandroid.base

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import com.tencent.bugly.crashreport.CrashReport

class App : BaseApp() {
    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
        lateinit var appViewModel: AppViewModel
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        appViewModel = getAppViewModelProvider()[AppViewModel::class.java]
        Log.d("App onCreate", "$appViewModel")

        CrashReport.initCrashReport(this, "bfe06b123a", false)
    }
}