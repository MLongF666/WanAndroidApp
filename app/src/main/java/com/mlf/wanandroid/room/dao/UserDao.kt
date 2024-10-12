package com.mlf.wanandroid.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.mlf.wanandroid.room.entity.User

@Dao
interface UserDao {
    @Insert()
    fun insertUser(user: User)
    @Query("SELECT * FROM user where username=(:userName)")
    fun getUser(userName: String): User?


}
