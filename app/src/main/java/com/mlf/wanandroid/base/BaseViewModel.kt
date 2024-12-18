package com.mlf.wanandroid.base


import android.text.style.LineHeightSpan.WithDensity
import android.util.Log

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mlf.wanandroid.dao.ArticleRepository
import com.mlf.wanandroid.model.response.ApiResponse
import com.mlf.wanandroid.util.SingleLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * @description: TODO 父类ViewModel
 * @author: mlf
 * @date: 2024/9/7 12:59
 * @version: 1.0
 */
open class BaseViewModel: ViewModel() {
	/** 请求异常（服务器请求失败，譬如：服务器连接超时等） */
	val exception = SingleLiveData<Exception>()

	/** 请求服务器返回错误（服务器请求成功但status错误，譬如：登录过期等） */
	val errorResponse = SingleLiveData<ApiResponse<*>?>()


	fun collectArticle(id: Int, successCallBack: () -> Any? = {}) {
		launch({
			handleRequest(ArticleRepository.collectArticle(id), {
				successCallBack.invoke()
				Log.d("BaseViewModel", "collectArticle success: $this")
			})
		},{
			exception.value.apply {
				Log.e("BaseViewModel", "collectArticle exception: $this")
			}
		})
	}

	/**
	 * 取消收藏文章
	 * @param id 文章id
	 */
	fun unCollectArticle(id: Int, successCallBack: () -> Any? = {}) {
		launch({
			handleRequest(ArticleRepository.uncollected(id), {
				successCallBack.invoke()
			},)
		})
	}

	fun BaseViewModel.launch(
		tryBlock: suspend CoroutineScope.() -> Unit,
		catchBlock: suspend CoroutineScope.() -> Unit = {},
		finallyBlock: suspend CoroutineScope.() -> Unit = {}
	) {
		// 默认是执行在主线程，相当于launch(Dispatchers.Main)
		viewModelScope.launch(Dispatchers.IO) {
			try {
				tryBlock()
			} catch (e: Exception) {
				exception.value = e
				catchBlock()
			} finally {
				finallyBlock()
			}
		}
	}

	fun <T> BaseViewModel.handleRequest(
		response: ApiResponse<T>,
		successBlock: suspend CoroutineScope.(response: ApiResponse<T>) -> Unit = {},
		errorBlock: suspend CoroutineScope.(response: ApiResponse<T>) -> Boolean = { false }
	) {
		viewModelScope.launch(Dispatchers.Main) {
			when (response.errorCode) {
				0 -> successBlock(response) // 服务器返回请求成功码
				else -> { // 服务器返回的其他错误码
					if (!errorBlock(response)) {
						// 只有errorBlock返回false不拦截处理时，才去统一提醒错误提示
						errorResponse.value = response
					}
				}
			}
		}
	}
}