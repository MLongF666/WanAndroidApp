package com.mlf.wanandroid.viewmodel


import androidx.lifecycle.MutableLiveData
import com.mlf.wanandroid.base.BaseApp
import com.mlf.wanandroid.base.BaseViewModel
import com.mlf.wanandroid.http.HttpManage
import com.mlf.wanandroid.model.User
import com.mlf.wanandroid.model.response.RegisterResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel:BaseViewModel() {
    private var _toLogin:MutableLiveData<Boolean> = MutableLiveData()
    val toLogin:MutableLiveData<Boolean>
        get() = _toLogin
    private var user:MutableLiveData<User> = MutableLiveData()
    private var repassword:MutableLiveData<String> = MutableLiveData()
    init {
        user.value = User()
        repassword.value = ""
    }
    fun register() {
        "register".showToast(BaseApp.context)
        user.value?.let { user1 ->
            HttpManage.register(user1.username,user1.password,repassword.value!!,object :
                Callback<RegisterResponse> {
                override fun onResponse(p0: Call<RegisterResponse>, p1: Response<RegisterResponse>) {
                    p1.body()?.let {
                        if (it.errorCode==0) {
                            "注册成功".showToast(BaseApp.context)
                        }else{
                            val msg = it.errorMsg.toString()
                            msg.showToast(BaseApp.context)
                        }
                    }
                }

                override fun onFailure(p0: Call<RegisterResponse>, p1: Throwable) {
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
    fun getRepassword():String?{
        return repassword.value
    }
    fun setRepassword(repassword:String){
        this.repassword.value=repassword
    }
    fun toLogin() {
        _toLogin.value = true
    }

}
