package com.mlf.wanandroid.ui.home.home



import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import com.mlf.wanandroid.base.BaseViewModel
import com.mlf.wanandroid.dao.ArticleRepository
import com.mlf.wanandroid.model.response.BannerData
import kotlinx.coroutines.flow.MutableStateFlow


class HomeArticleViewModel: BaseViewModel() {
    private val page= MutableLiveData<Int>()
    /** Banner列表 */
    private val _bannerListStateFlow = MutableStateFlow<List<BannerData>>(value = emptyList())
    val bannerListStateFlow = _bannerListStateFlow
    val articleList = page.switchMap {
        page -> ArticleRepository.getArticleList(page)
    }
    fun loadArticleList(page: Int){
        this.page.value = page
    }
    /** 请求首页轮播图 */
    fun fetchBanners() {
        Log.d("HomeArticleViewModel", "fetchBanners")
        launch({
            handleRequest(ArticleRepository.getBannerList(), {
                _bannerListStateFlow.value = it.data
            })
        })
    }

}
