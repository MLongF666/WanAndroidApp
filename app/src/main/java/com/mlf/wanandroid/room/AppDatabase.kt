package com.mlf.wanandroid.room

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mlf.wanandroid.model.User
import com.mlf.wanandroid.model.response.Article
import com.mlf.wanandroid.room.dao.UserDao
import kotlin.concurrent.Volatile

/**
 * @description: TODO room数据库
 * @author: mlf
 * @date: 2024/9/12 14:36
 * @version: 1.0
 */
@Database(entities = [User::class], version = 1,exportSchema = false)
abstract class AppDatabase:RoomDatabase() {
    companion object{
        @Volatile
        private var mAppDatabase: AppDatabase? = null
        // 单例模式
        fun getInstance(context: Context): AppDatabase {
            if (mAppDatabase == null) {
                synchronized(AppDatabase::class.java) {
                    if (mAppDatabase == null) {
                        Log.d("AppDatabase", "Creating database")
                        mAppDatabase =Room.databaseBuilder(context, AppDatabase::class.java, "wanAndroid.db").build()
                    }
                }
            }
            return mAppDatabase!!
        }
    }
    abstract fun getUserDao(): UserDao
}