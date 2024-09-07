package com.mlf.wanandroid.ui.user

import android.content.Intent
import androidx.lifecycle.Observer
import com.mlf.wanandroid.R
import com.mlf.wanandroid.base.BaseActivity
import com.mlf.wanandroid.databinding.ActivityLoginBinding
import com.mlf.wanandroid.ui.MainActivity
import com.mlf.wanandroid.viewmodel.LoginViewModel

class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel>() {

    override fun initView() {
        getBinding().viewModel = getViewModel()
        getViewModel().navigateToRegister.observe(this, Observer {
            if (it) {
                register()
                getViewModel().navigateToRegister.value = false
            }
        })
        getViewModel().navigateToHome.observe(this, Observer {
            if (it) {
                toHome()
                getViewModel().navigateToHome.value = false
            }
        })
    }

    private fun toHome() {
        startActivity(Intent(this, MainActivity::class.java))
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_login
    }

    override fun getViewModelClass(): Class<LoginViewModel> {
        return LoginViewModel::class.java
    }
    private fun register() {
        startActivity(Intent(this, RegisterActivity::class.java))
    }
}