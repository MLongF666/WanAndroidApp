package com.mlf.wanandroid.base

import android.content.Context
import androidx.lifecycle.liveData
import com.mlf.wanandroid.room.AppDatabase
import kotlin.coroutines.CoroutineContext

open class BaseRepository(context: Context) {
    val database = AppDatabase.getInstance(context)

    companion object {
        var INSTANCE: BaseRepository? = null
        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = BaseRepository(context)
            }
        }

        fun get(): BaseRepository {
            return INSTANCE ?: throw IllegalStateException("BaseRepository must be initialized")
        }
    }


    fun <T> fire(context: CoroutineContext, block: suspend () -> Result<T>) =
        liveData<Result<T>>(context) {
            val result = try {
                block()
            } catch (e: Exception) {
                Result.failure<T>(e)
            }
            emit(result)
        }
}
