package com.mlf.wanandroid.ui.user

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import com.mlf.wanandroid.base.BaseViewModel
import com.mlf.wanandroid.dao.UserRepository
import com.mlf.wanandroid.room.entity.User


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
    private val userLiveData = MutableLiveData<User>()
    val loginLiveData=userLiveData.switchMap {
        Log.d("LoginViewModel", "login username: ${it.username} password: ${it.password}")
        UserRepository.login(it.username, it.password)
    }
    fun login() {
       userLiveData.value = user.value
    }
    fun saveUser(id: Int) {
        UserRepository.saveUser(user.value!!,id)
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
