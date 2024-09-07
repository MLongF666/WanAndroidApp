package com.mlf.wanandroid.viewmodel

import androidx.lifecycle.MutableLiveData
import com.mlf.wanandroid.base.BaseApp
import com.mlf.wanandroid.base.BaseViewModel
import com.mlf.wanandroid.http.HttpManage
import com.mlf.wanandroid.model.User
import com.mlf.wanandroid.model.response.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel: BaseViewModel() {
    private var _navigateToHome= MutableLiveData<Boolean>()
    val navigateToHome: MutableLiveData<Boolean>
        get() = _navigateToHome
    private var _navigateToRegister= MutableLiveData<Boolean>()
    val navigateToRegister: MutableLiveData<Boolean>
        get() = _navigateToRegister
    private var user: MutableLiveData<User> = MutableLiveData<User>()
    init {
        user.value = User()
    }
    fun login() {
        user.value?.let { user ->
            HttpManage.login(user.username, user.password,object :Callback<LoginResponse>{
                override fun onResponse(p0: Call<LoginResponse>, p1: Response<LoginResponse>) {
                    p1.body()?.let {
                        if (it.errorCode==0) {
                            navigateToHome()
                        }else{
                            val msg = it.errorMsg.toString()
                            msg.showToast(BaseApp.context)
                        }
                    }
                }

                override fun onFailure(p0: Call<LoginResponse>, p1: Throwable) {
                        p1.message.toString().showToast(BaseApp.context)
                }

            })
        }
    }
    fun getUserName():String?{
        return user.value?.username
    }
    fun setUserName(username:String){
        user.value?.username=username
    }
    fun getPassword():String?{
        return user.value?.password
    }
    fun setPassword(password:String){
        user.value?.password=password
    }
    fun navigateToRegister() {
        _navigateToRegister.value = true
    }
    private fun navigateToHome() {
        _navigateToHome.value = true
    }


}
