package com.mlf.wanandroid.base

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


/**
 * @description: TODO 父类Activity
 * @author: mlf
 * @date: 2024/9/6 14:31
 * @version: 1.0
 */
abstract class BaseActivity<V: ViewDataBinding, VM: ViewModel>:AppCompatActivity() {
    private lateinit var mViewModel: VM
    private lateinit var mBinding: V

    private fun transparentStatusBar(activity: Activity) {
        transparentStatusBar(activity.window)
    }

    @SuppressLint("ObsoleteSdkInt")
    private fun transparentStatusBar(window: Window) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            val option = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            val vis = window.decorView.systemUiVisibility
            window.decorView.systemUiVisibility = option or vis
            window.statusBarColor = Color.TRANSPARENT
        } else {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }
    }
    private fun setAndroidNativeLightStatusBar(activity: Activity, dark: Boolean) {
        val decor = activity.window.decorView
        if (dark) {
            decor.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        } else {
            decor.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding= DataBindingUtil.setContentView(this,getLayoutId())
        mViewModel= ViewModelProvider(this)[getViewModelClass()]
        transparentStatusBar(this)
        setAndroidNativeLightStatusBar(this,true)
        initView()
    }
    override fun onDestroy() {
        super.onDestroy()
        mBinding.unbind()
    }
    abstract fun initView()
    abstract fun getLayoutId(): Int
    abstract fun getViewModelClass(): Class<VM>
    fun getBinding(): V{
        return mBinding
    }
    fun getViewModel(): VM{
        return mViewModel
    }

}