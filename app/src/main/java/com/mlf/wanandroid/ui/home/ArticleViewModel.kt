package com.mlf.wanandroid.ui.home


import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.mlf.wanandroid.base.BaseViewModel
import com.mlf.wanandroid.dao.ArticleRepository
import com.mlf.wanandroid.model.response.Article
import com.mlf.wanandroid.model.response.BannerData
import com.mlf.wanandroid.util.CodeModel
import kotlinx.coroutines.flow.MutableStateFlow


class ArticleViewModel : BaseViewModel() {
    private val page = MutableLiveData<Int>()

    /** Banner列表 */
    private val _bannerListStateFlow = MutableStateFlow<List<BannerData>>(value = emptyList())
    val bannerListStateFlow = _bannerListStateFlow

    // 文章列表
    private val _articleListLiveData = MutableLiveData<List<Article>>()
    val articleLiveData = _articleListLiveData

    /** 请求首页轮播图 */
    fun fetchBanners() {
        launch({
            handleRequest(ArticleRepository.getBannerList(), {
                _bannerListStateFlow.value = it.data
                Log.d(
                    "HomeArticleViewModel",
                    "fetchBanners _bannerListStateFlow.value:${_bannerListStateFlow.value}"
                )
            })
        })
    }

    fun loadArticleList(page: Int, type: Int?) {
        this.page.value = page
        when (type) {
            CodeModel.TYPE_CODE_HOME -> {
                launch({
                    handleRequest(ArticleRepository.getHomeArticleList(page), {
                        _articleListLiveData.value = it.data.datas
                        Log.d(
                            "HomeArticleViewModel",
                            "loadArticleList page:$page type:$type} article:${_articleListLiveData.value}"
                        )
                    })
                }, {
                    Log.d("HomeArticleViewModel", "loadArticleList error${exception.value}")
                })
            }

            CodeModel.TYPE_CODE_SQUARE -> {
                launch({
                    handleRequest(ArticleRepository.getSquareArticleList(page), {
                        _articleListLiveData.value = it.data.datas
                        Log.d(
                            "HomeArticleViewModel",
                            "loadArticleList page:$page type:$type} article:${_articleListLiveData.value}"
                        )
                    })
                }, {
                    Log.d("HomeArticleViewModel", "loadArticleList error${exception.value}")
                })
            }

            CodeModel.TYPE_CODE_QA -> {
                launch({
                    handleRequest(ArticleRepository.getQaArticleList(page + 1), {
                        _articleListLiveData.value = it.data.datas
                    })
                })
            }

            else -> {
                return
            }
        }
    }
}
