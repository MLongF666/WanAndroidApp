package com.mlf.wanandroid.ui.user

import android.content.Intent
import androidx.lifecycle.Observer
import com.mlf.wanandroid.R
import com.mlf.wanandroid.base.BaseActivity
import com.mlf.wanandroid.databinding.ActivityRegisterBinding
import com.mlf.wanandroid.viewmodel.RegisterViewModel

class RegisterActivity : BaseActivity<ActivityRegisterBinding, RegisterViewModel>() {
    override fun initView() {
        getBinding().registerViewModel = getViewModel()
        getViewModel().toLogin.observe(this, Observer {
            if (it){
                toLogin()
                getViewModel().toLogin.value = false
            }
        })
    }

    private fun toLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_register
    }

    override fun getViewModelClass(): Class<RegisterViewModel> {
        return RegisterViewModel::class.java
    }

}