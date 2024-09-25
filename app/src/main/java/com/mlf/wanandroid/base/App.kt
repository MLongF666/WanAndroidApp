package com.mlf.wanandroid.base

import android.util.Log
import com.tencent.bugly.crashreport.CrashReport

class App:BaseApp() {

	companion object{
		lateinit var appViewModel: AppViewModel
	}
	override fun onCreate() {
		super.onCreate()
		appViewModel=getAppViewModelProvider()[AppViewModel::class.java]
		Log.d("App onCreate","$appViewModel")

		CrashReport.initCrashReport(this, "bfe06b123a", false)
	}
}