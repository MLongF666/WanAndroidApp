package com.mlf.wanandroid.base

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.mlf.wanandroid.filter.showToast
import com.mlf.wanandroid.ui.user.LoginActivity


/**
 * @description: TODO 父类Activity
 * @author: mlf
 * @date: 2024/9/6 14:31
 * @version: 1.0
 */
abstract class BaseActivity<V : ViewDataBinding, VM : BaseViewModel> : AppCompatActivity() {
    private lateinit var mViewModel: VM
    private lateinit var mBinding: V

    private fun transparentStatusBar(activity: Activity) {
        transparentStatusBar(activity.window)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    fun laucher(activity: Activity, clazz: Class<*>, bundle: Bundle? = null) {
        val intent = Intent(activity, clazz)
        if (bundle != null) intent.putExtras(bundle)
        activity.startActivity(intent)
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

    //是否隐藏标题栏
    open fun isHideTitle(isHide: Boolean) {
        if (isHide) {
            supportActionBar?.hide()
        } else {
            supportActionBar?.show()
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, getLayoutId())
        mViewModel = ViewModelProvider(this)[getViewModelClass()]
//        transparentStatusBar(this)
//        setAndroidNativeLightStatusBar(this,true)
        isHideTitle(true)
        initView()
        //默认隐藏
        createObserve()

    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding.unbind()
    }

    abstract fun initView()
    abstract fun getLayoutId(): Int
    abstract fun getViewModelClass(): Class<VM>
    fun getBinding(): V {
        return mBinding
    }

    fun getViewModel(): VM {
        return mViewModel
    }

    /** 提供编写LiveData监听逻辑的方法 */
    open fun createObserve() {
        // 全局服务器返回的错误信息监听
        mViewModel.errorResponse.observe(this) {
            if (it?.errorCode == -1001) {
                LoginActivity.launch(this@BaseActivity)
            }
            it?.errorMsg?.run {
                showToast(App.context)
            }
            // 全局服务器返回的错误信息监听

        }
        // 全局服务器请求错误监听
//        mViewModel.apply {
//            exception.observe(this@BaseVMBActivity) {
//                requestError(it.message)
//                LogUtil.e("网络请求错误：${it.message}")
//                when (it) {
//                    is SocketTimeoutException -> ToastUtil.showShort(
//                        this@BaseVMBActivity,
//                        getString(R.string.request_time_out)
//                    )
//
//                    is ConnectException, is UnknownHostException -> ToastUtil.showShort(
//                        this@BaseVMBActivity,
//                        getString(R.string.network_error)
//                    )
//
//                    else -> ToastUtil.showShort(
//                        this@BaseVMBActivity, it.message ?: getString(R.string.response_error)
//                    )
//                }
//            }
//
//            // 全局服务器返回的错误信息监听
//            errorResponse.observe(this@BaseVMBActivity) {
//                requestError(it?.errorMsg)
//                it?.errorMsg?.run {
//                    ToastUtil.showShort(this@BaseVMBActivity, this)
//                }
//            }
//        }
    }

}