package com.mlf.wanandroid.ui.user

import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.Observer
import com.mlf.wanandroid.R
import com.mlf.wanandroid.base.BaseActivity
import com.mlf.wanandroid.databinding.ActivityRegisterBinding

class RegisterActivity : BaseActivity<ActivityRegisterBinding, RegisterViewModel>() {
    override fun initView() {
        getBinding().registerViewModel = getViewModel()
        getViewModel().registerResponse.observe(this, Observer {
            val response = it.getOrNull()
            if (response!= null){
                if (response.errorCode == 0){
                    Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show()
                    toLogin()
                }else{
                    Toast.makeText(this, response.errorMsg, Toast.LENGTH_SHORT).show()
                }
            }
            else{
                it.exceptionOrNull()?.printStackTrace()
            }
        })
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