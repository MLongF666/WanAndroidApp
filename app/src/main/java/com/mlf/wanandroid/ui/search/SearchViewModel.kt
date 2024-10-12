package com.mlf.wanandroid.ui.search

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.mlf.wanandroid.base.BaseViewModel
import com.mlf.wanandroid.dao.SearchRepository
import com.mlf.wanandroid.model.response.Article
import com.mlf.wanandroid.model.response.Hotkey
import com.mlf.wanandroid.room.entity.HistorySearch

class SearchViewModel:BaseViewModel() {
	private val  _searchHotKeyList= MutableLiveData<List<Hotkey>>(emptyList())
	val searchHotKeyList=_searchHotKeyList
	private val  _searchHistoryList= MutableLiveData<List<HistorySearch>>(emptyList())
	val searchHistoryList=_searchHistoryList

	private val _articleListLiveData= MutableLiveData<List<Article>>(emptyList())
	val articleLiveData=_articleListLiveData

	fun getHotKeyList(){
		launch({
			handleRequest(SearchRepository.getHotKeyList(),{
				_searchHotKeyList.value=it.data
			})
		})
	}
	//查询搜索历史列表
	fun  getHistoryList(context: Context){
		launch({
			_searchHistoryList.value=SearchRepository.searchHistoryList(context)
			Log.d("SearchViewModel", "getHistoryList _searchHistoryList.value:${_searchHistoryList.value}")
		},{
			exception.value.apply {
			}
		})
	}
	fun searchList(page: Int,context: Context,content:String){
		launch({
			handleRequest(SearchRepository.getArticleList(content,page),{
				_articleListLiveData.value=it.data.datas
				Log.d("SearchViewModel", "searchList page:$page content:$content _articleListLiveData.value:${_articleListLiveData.value}")
			})
			addHistory(context,content)
		})
	}

	//添加搜索历史
	private fun addHistory(context: Context,content:String){
		Log.d("SearchViewModel", "addHistory content: $content")
		launch({
			//如果查询为空则证明没有存在 可以继续插入，否则不插入
			val list=SearchRepository.searchHistory(context, content)
			if (list.isEmpty()){
				SearchRepository.insertHistorySearch(context,content)
			}
			getHistoryList(context)
		},{
			exception.value.apply {
				Log.e("SearchViewModel", "addHistory exception: $this")
			}
		})
	}
	//删除搜索历史
	fun deleteHistory(context: Context){
		launch({
			SearchRepository.deleteAllHistorySearch(context)
			getHistoryList(context)
		})
	}
	fun deleteHistoryById(context: Context, id:Int){
		launch({
			SearchRepository.deleteHistorySearchById(context,id)
			getHistoryList(context)
		})
	}

}
