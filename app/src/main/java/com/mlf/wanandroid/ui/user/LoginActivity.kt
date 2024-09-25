package com.mlf.wanandroid.ui.user

import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.Observer
import com.mlf.wanandroid.R
import com.mlf.wanandroid.base.BaseActivity
import com.mlf.wanandroid.databinding.ActivityLoginBinding
import com.mlf.wanandroid.ui.MainActivity

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
        getViewModel().loginLiveData.observe(this, Observer {
            val response = it.getOrNull()
            if (response != null){
                if (response.errorCode == 0){
                    Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show()
                    toHome()
                    getViewModel().saveUser(response.data.id)
                }else{
                    Toast.makeText(this, response.errorMsg, Toast.LENGTH_SHORT).show()
                }
            }else{
                it.exceptionOrNull()?.printStackTrace()
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