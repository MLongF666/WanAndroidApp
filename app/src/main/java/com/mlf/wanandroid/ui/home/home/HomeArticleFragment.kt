package com.mlf.wanandroid.ui.home.home


import android.annotation.SuppressLint
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.mlf.wanandroid.R
import com.mlf.wanandroid.base.App
import com.mlf.wanandroid.base.BaseFragment
import com.mlf.wanandroid.databinding.BannerLayoutBinding
import com.mlf.wanandroid.databinding.FragmentHomeArticleBinding
import com.mlf.wanandroid.filter.showToast
import com.mlf.wanandroid.model.bean.ArticleData
import com.mlf.wanandroid.model.response.Article
import com.mlf.wanandroid.model.response.BannerData
import com.mlf.wanandroid.ui.adapter.ArticleAdapter
import com.mlf.wanandroid.ui.adapter.MyBannerAdapter
import com.mlf.wanandroid.ui.webview.WebViewActivity
import com.scwang.smart.refresh.footer.BallPulseFooter
import com.scwang.smart.refresh.header.MaterialHeader
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.youth.banner.indicator.CircleIndicator
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.launch

class HomeArticleFragment : BaseFragment<FragmentHomeArticleBinding, HomeArticleViewModel>() {
    private val mBannerList=ArrayList<BannerData>()
    private val mBannerAdapter=MyBannerAdapter(mBannerList)
    private val articleList = ArrayList<Article>()
    private var page=0
    private val mAdapter by lazy { ArticleAdapter() }
    private var mBannerBinding:BannerLayoutBinding?=null
    override fun getViewModelClass(): Class<HomeArticleViewModel> {
        return HomeArticleViewModel::class.java
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun initView() {
        initRefresh()
        mBannerBinding = DataBindingUtil.inflate<BannerLayoutBinding>(
            layoutInflater,
            R.layout.banner_layout,
            null,
            false
        ).apply {
            banner.apply {
                setAdapter(mBannerAdapter)
                indicator =CircleIndicator(context)
                addBannerLifecycleObserver(viewLifecycleOwner)
            }
        }

        getBinding().rvArticle.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = mAdapter.apply {
                if (!hasHeaderLayout()){
                    mBannerBinding?.let { addHeaderView(it.root) }
                }
                addChildClickViewIds(R.id.zan, R.id.cardLayout)
                setOnItemChildClickListener {
                    _,view,position->
                    when(view.id){
                        R.id.zan->{
                            if (mAdapter.getItem(position).collect){
                                //取消点赞
                                getViewModel().unCollectArticle(mAdapter.getItem(position).id){
                                    mAdapter.getItem(position).collect=false
                                    mAdapter.notifyItemChanged(position+1)
                                }
                            }else{
                                //点赞
                                getViewModel().collectArticle(mAdapter.getItem(position).id){
                                    mAdapter.getItem(position).collect=true
                                    mAdapter.notifyItemChanged(position+1)
                                }
                            }
                        }
                        R.id.cardLayout->{
                            val article=mAdapter.getItem(position)
                            val articleData = ArticleData(article.id, article.link,article.title, article.collect)
                            laucherToWebActivity(requireActivity(),WebViewActivity::class.java,articleData)
                        }
                        else->{
                            return@setOnItemChildClickListener
                        }
                    }
                }
            }
        }
        Log.d("articleList","$articleList")
        //开始刷新
        getBinding().refreshLayout.autoRefresh()
    }

    @SuppressLint("NotifyDataSetChanged")
	override fun createObserve() {
        super.createObserve()
        App.appViewModel.collectEvent.observe(this){
            for (position in mAdapter.data.indices){
                if (mAdapter.getItem(position).id==it.id){
                    mAdapter.getItem(position).collect=it.collect
                    mAdapter.notifyItemChanged(position+1)
                    break
                }
            }
        }
        getViewModel().apply {
            lifecycleScope.launch {
                bannerListStateFlow.flowWithLifecycle(lifecycle).drop(1).collect {
                    it.let {
                        mBannerList.apply {
                            clear()
                            addAll(it)
                        }
                    }
                    mBannerAdapter.setDatas(mBannerList)
                    mBannerAdapter.notifyDataSetChanged()
                }
            }
        }
        getViewModel().articleList.observe(this, Observer {
            val response = it.getOrNull()
            if (response!= null){
                if (response.errorCode == 0){
                    val datas = response.data.datas
                    mAdapter.addData(datas)
                }else{
                    response.errorMsg.showToast(requireContext())
                }
            }
        })
    }


    private fun initRefresh() {
        getBinding().refreshLayout.setRefreshHeader(MaterialHeader(requireContext()))
        getBinding().refreshLayout.setRefreshFooter(BallPulseFooter(requireContext()))
        getBinding().refreshLayout.setOnRefreshListener {
            onRefresh(it)
        }
        getBinding().refreshLayout.setOnLoadMoreListener {
            onLoadMore(it)
        }
    }
    private fun onLoadMore(layout: RefreshLayout) {
        page++
        getViewModel().loadArticleList(page)
        layout.finishLoadMore(500)
    }

    private fun onRefresh(refreshLayout: RefreshLayout) {
        if (page==0){
            getViewModel().fetchBanners()
        }
        getViewModel().loadArticleList(page)
        refreshLayout.finishRefresh(500)
        page=0
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_home_article
    }

    override fun onDestroy() {
        super.onDestroy()

        Log.d("HomeArticleFragment","onDestroy")
    }

    @SuppressLint("NotifyDataSetChanged")
	override fun onStop() {
        super.onStop()
        Log.d("HomeArticleFragment","onStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mAdapter.removeAllHeaderView()
        Log.d("HomeArticleFragment","onDestroyView")
    }

    override fun onResume() {
        super.onResume()
        Log.d("HomeArticleFragment","onResume")
    }
}