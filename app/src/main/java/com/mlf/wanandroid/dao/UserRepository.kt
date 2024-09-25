package com.mlf.wanandroid.dao
import com.mlf.wanandroid.base.BaseRepository
import android.util.Log
import com.mlf.wanandroid.base.BaseApp

import com.mlf.wanandroid.http.HttpManage
import com.mlf.wanandroid.model.User
import kotlinx.coroutines.Dispatchers


/**
 * @description: TODO 数据仓库
 * @author: mlf
 * @date: 2024/9/8 11:37
 * @version: 1.0
 */
object UserRepository : BaseRepository(BaseApp()){
    fun login(username: String, password: String)= fire(Dispatchers.IO){
        val loginResponse = HttpManage.login(username, password)
        Result.success(loginResponse)
    }
    fun register(username: String, password: String, repassword: String)= fire(Dispatchers.IO){
        Log.d("LoginRepository", "register username: $username password: $password repassword: $repassword")
        val registerResponse = HttpManage.register(username, password, repassword)
        Result.success(registerResponse)
    }

    fun saveUser(user: User, id: Int) {
        //开启子线程
        Thread{
            user.id=id
            val user1 =this.database.getUserDao().getUser(user.username)
            if (user1==null){
                this.database.getUserDao().insertUser(user)
            }else{
                Log.d("UserRepository", "saveUser user2: $user1")
            }
        }.start()

    }


}