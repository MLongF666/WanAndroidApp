package com.mlf.wanandroid.ui.user


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import com.mlf.wanandroid.base.BaseViewModel
import com.mlf.wanandroid.dao.UserRepository
import com.mlf.wanandroid.model.User


class RegisterViewModel:BaseViewModel() {
    private var _toLogin:MutableLiveData<Boolean> = MutableLiveData()
    val toLogin:MutableLiveData<Boolean>
        get() = _toLogin
    private var user:MutableLiveData<User> = MutableLiveData()
    private val userLiveData:MutableLiveData<User> = MutableLiveData()
    val registerResponse=userLiveData.switchMap {
        UserRepository.register(it.username, it.password, it.repassword)
    }
    init {
        user.value = User()
    }
    fun register() {
        userLiveData.value = user.value
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
        return user.value?.repassword
    }
    fun setRepassword(repassword:String){
        user.value?.repassword=repassword
    }
    fun toLogin() {
        _toLogin.value = true
    }

}
