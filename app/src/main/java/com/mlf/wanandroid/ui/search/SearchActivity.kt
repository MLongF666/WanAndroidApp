package com.mlf.wanandroid.ui.search

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.Menu
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.flexbox.FlexboxLayoutManager
import com.mlf.wanandroid.R
import com.mlf.wanandroid.base.App
import com.mlf.wanandroid.base.BaseActivity
import com.mlf.wanandroid.databinding.ActivitySearchBinding
import com.mlf.wanandroid.model.bean.ArticleData
import com.mlf.wanandroid.model.response.Article
import com.mlf.wanandroid.model.response.Hotkey
import com.mlf.wanandroid.ui.adapter.ArticleAdapter
import com.mlf.wanandroid.ui.adapter.SearchHotAdapter
import com.mlf.wanandroid.ui.adapter.SearchHistoryAdapter
import com.mlf.wanandroid.ui.webview.WebViewActivity
import com.scwang.smart.refresh.footer.BallPulseFooter
import com.scwang.smart.refresh.header.MaterialHeader
import com.scwang.smart.refresh.layout.api.RefreshLayout

class SearchActivity : BaseActivity<ActivitySearchBinding, SearchViewModel>() {
    private val searchHistoryAdapter by lazy { SearchHistoryAdapter() }
    private val searchHotListAdapter by lazy { SearchHotAdapter() }
    private val searchArticleAdapter by lazy { ArticleAdapter() }
    private var page = 0
    private var isSearchList = false
    private lateinit var mArticleList: List<Article>
    override fun initView() {
        isHideTitle(true)
        initSearchData()
        getBinding().back.setOnClickListener {
            if (isSearchList) {
                getBinding().lySearch.visibility = VISIBLE
                getBinding().refreshLayout.visibility = GONE
                //切回搜索界面
                isSearchList = false
                getBinding().searchEdit.setText("")
                searchArticleAdapter.setList(null)
            } else {
                finish()
            }
        }
        //设置动态长宽相等
        getBinding().search.minimumWidth = getBinding().searchEdit.minimumHeight
        getBinding().search.setOnClickListener {
            searchArticleList()
        }
        getBinding().deleteHistory.setOnClickListener {
            if (searchHistoryAdapter.itemCount != 0) {
                getViewModel().deleteHistory(this)
            }
        }
    }

    private fun searchArticleList() {
        isSearchList = true
        getBinding().lySearch.visibility = GONE
        getBinding().refreshLayout.visibility = VISIBLE
        //开始刷新
        getBinding().refreshLayout.autoRefresh()


    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initSearchData() {
        getBinding().searchHotList.apply {
            searchHotListAdapter.setOnItemClickListener(object :
                SearchHotAdapter.OnItemClickListener {
                override fun onItemClick(position: Int, data: Hotkey) {
                    val hotkey = searchHotListAdapter.getItem(position)
                    getBinding().searchEdit.setText(hotkey.name)
                    searchArticleList()
                }
            })
            layoutManager = FlexboxLayoutManager(this@SearchActivity)
            adapter = searchHotListAdapter
        }
        getBinding().searchHistoryList.apply {
            layoutManager = LinearLayoutManager(this@SearchActivity)
            searchHistoryAdapter.apply {
                addChildClickViewIds(R.id.deleted, R.id.content)
                setOnItemChildClickListener { _, view, position ->
                    when (view.id) {
                        R.id.deleted -> {
                            val historySearch = searchHistoryAdapter.getItem(position)
                            getViewModel().deleteHistoryById(this@SearchActivity, historySearch.id)
                        }

                        R.id.content -> {
                            val historySearch = searchHistoryAdapter.getItem(position)
                            getBinding().searchEdit.setText(historySearch.content)
                            searchArticleList()
                        }

                    }
                }
            }
            adapter = searchHistoryAdapter
        }
        //界面打开之后就开始请求热点
        getViewModel().getHotKeyList()
        //查询历史搜索
        getViewModel().getHistoryList(this)
        //搜索列表
        getBinding().rvArticle.apply {
            layoutManager = LinearLayoutManager(this@SearchActivity)
            adapter = searchArticleAdapter.apply {
                addChildClickViewIds(R.id.zan, R.id.cardLayout)
                setOnItemChildClickListener { _, view, position ->
                    when (view.id) {
                        R.id.zan -> {
                            if (getItem(position).collect) {
                                //取消点赞
                                getViewModel().unCollectArticle(getItem(position).id) {
                                    getItem(position).collect = false
                                    notifyItemChanged(position)
                                }
                            } else {
                                //点赞
                                getViewModel().collectArticle(getItem(position).id) {
                                    getItem(position).collect = true
                                    notifyItemChanged(position)
                                }
                            }
                        }

                        R.id.cardLayout -> {
                            val article = getItem(position)
                            val articleData = ArticleData(
                                article.id,
                                article.link,
                                article.title,
                                article.collect
                            )
                            laucherToWebActivity(
                                this@SearchActivity,
                                WebViewActivity::class.java, articleData
                            )
                        }

                        else -> {
                            return@setOnItemChildClickListener
                        }
                    }
                }
            }
        }
        //监听数据变化
        getViewModel().searchHotKeyList.observe(this) {
            searchHotListAdapter.setList(it);
            searchHotListAdapter.notifyDataSetChanged()
        }
        getViewModel().searchHistoryList.observe(this) {
            Log.d("SearchActivity", "searchHistoryList: $it")
            searchHistoryAdapter.setList(it)
            searchHistoryAdapter.notifyDataSetChanged()
        }
        getViewModel().articleLiveData.observe(this) {
            mArticleList = it
            if (isSearchList) {
                if (mArticleList.isNotEmpty()) {
                    searchArticleAdapter.addData(mArticleList)
                    searchArticleAdapter.notifyDataSetChanged()

                } else {
                    showEmpty()
                    Log.d("SearchActivity articleList", "没有数据")
                }
            }


        }
        initRefresh()
    }

    private fun showEmpty() {
        searchArticleAdapter.setEmptyView(R.layout.layout_empty)
    }

    private fun initRefresh() {
        getBinding().refreshLayout.setRefreshHeader(MaterialHeader(this))
        getBinding().refreshLayout.setRefreshFooter(BallPulseFooter(this))
        getBinding().refreshLayout.setOnRefreshListener {
            onRefresh(it)
        }
        getBinding().refreshLayout.setOnLoadMoreListener {
            onLoadMore(it)
        }
    }

    private fun onLoadMore(refreshLayout: RefreshLayout) {
        ++page
        getViewModel().searchList(page, this, getBinding().searchEdit.text.toString())
        refreshLayout.finishLoadMore(500)
    }

    private fun onRefresh(refreshLayout: RefreshLayout) {
        getViewModel().searchList(0, this, getBinding().searchEdit.text.toString())
        refreshLayout.finishRefresh(500)
    }

    override fun createObserve() {
        super.createObserve()
        App.appViewModel.collectEvent.observe(this) {
            for (position in searchArticleAdapter.data.indices) {
                if (searchArticleAdapter.getItem(position).id == it.id) {
                    searchArticleAdapter.getItem(position).collect = it.collect
                    searchArticleAdapter.notifyItemChanged(position)
                    break
                }
            }
        }
    }

    private fun laucherToWebActivity(
        activity: Activity,
        clz: Class<WebViewActivity>,
        articleData: ArticleData
    ) {
        val intent = Intent(activity, clz)
        intent.putExtra("type", 0)
        intent.putExtra("articleData", articleData)
        activity.startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        return true
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_search
    }

    override fun getViewModelClass(): Class<SearchViewModel> {
        return SearchViewModel::class.java
    }
}

