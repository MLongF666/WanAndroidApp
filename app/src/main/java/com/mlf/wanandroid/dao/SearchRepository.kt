package com.mlf.wanandroid.dao

import android.content.Context
import com.mlf.wanandroid.http.HttpManage
import com.mlf.wanandroid.model.bean.PageResponse
import com.mlf.wanandroid.model.response.ApiResponse
import com.mlf.wanandroid.model.response.Article
import com.mlf.wanandroid.model.response.Hotkey
import com.mlf.wanandroid.room.AppDatabase
import com.mlf.wanandroid.room.entity.HistorySearch

object SearchRepository {
	suspend fun getHotKeyList(): ApiResponse<List<Hotkey>> {
		return HttpManage.getHotKeyList()
	}
	suspend fun searchHistoryList(context: Context): List<HistorySearch> {
		return AppDatabase.getInstance(context).getHistorySearchDao().getAllHistorySearch()
	}

	suspend  fun insertHistorySearch(context: Context,content:String) {
		val historySearch=HistorySearch(0,content)
		return AppDatabase.getInstance(context).getHistorySearchDao().insertHistorySearch(historySearch)
	}

	suspend fun deleteAllHistorySearch(context: Context) {
		return AppDatabase.getInstance(context).getHistorySearchDao().deleteAllHistorySearch()
	}
	suspend fun searchHistory(context: Context,content:String):List<HistorySearch>{
		return AppDatabase.getInstance(context).getHistorySearchDao().searchHistory(content)
	}

	suspend fun deleteHistorySearchById(context: Context, id: Int) {
		return AppDatabase.getInstance(context).getHistorySearchDao().deleteHistorySearchById(id)
	}

	suspend fun getArticleList(content: String, page: Int): ApiResponse<PageResponse<Article>> {
		return HttpManage.searchArticleList(content,page)
	}

}