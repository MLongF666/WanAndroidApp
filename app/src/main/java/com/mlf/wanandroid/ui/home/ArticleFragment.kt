package com.mlf.wanandroid.ui.home


import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.mlf.wanandroid.R
import com.mlf.wanandroid.base.App
import com.mlf.wanandroid.base.BaseFragment
import com.mlf.wanandroid.databinding.BannerLayoutBinding
import com.mlf.wanandroid.databinding.FragmentHomeArticleBinding
import com.mlf.wanandroid.model.bean.ArticleData
import com.mlf.wanandroid.model.bean.PageData
import com.mlf.wanandroid.model.response.Article
import com.mlf.wanandroid.model.response.BannerData
import com.mlf.wanandroid.ui.adapter.ArticleAdapter
import com.mlf.wanandroid.ui.adapter.MyBannerAdapter
import com.mlf.wanandroid.ui.webview.WebViewActivity
import com.mlf.wanandroid.util.CodeModel.TYPE_CODE_HOME
import com.scwang.smart.refresh.footer.BallPulseFooter
import com.scwang.smart.refresh.header.MaterialHeader
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.youth.banner.indicator.CircleIndicator
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.launch

class ArticleFragment : BaseFragment<FragmentHomeArticleBinding, ArticleViewModel>() {
    private val mBannerList=ArrayList<BannerData>()
    private val mBannerAdapter=MyBannerAdapter(mBannerList)
    private val articleList = ArrayList<Article>()
    private var page=0
    private val mAdapter by lazy { ArticleAdapter() }
    private var mBannerBinding:BannerLayoutBinding?=null
    private var type:Int?=null


    companion object{
        fun newInstance(type: Int): ArticleFragment {
            val bundle=Bundle()
            bundle.putInt("type",type)
            val fragment= ArticleFragment().apply { arguments=bundle }
            return fragment
        }
    }
    override fun getViewModelClass(): Class<ArticleViewModel> {
        return ArticleViewModel::class.java
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments!=null){
            type=arguments?.getInt("type")
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun initView() {
        initData()
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
                if (!hasHeaderLayout()&&type==TYPE_CODE_HOME){
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
                                    if (mAdapter.hasHeaderLayout()){
                                        mAdapter.notifyItemChanged(position+1)
                                    }else{
                                        mAdapter.notifyItemChanged(position)
                                    }
                                }
                            }else{
                                //点赞
                                getViewModel().collectArticle(mAdapter.getItem(position).id){
                                    mAdapter.getItem(position).collect=true
                                    if (mAdapter.hasHeaderLayout()){
                                        mAdapter.notifyItemChanged(position+1)
                                    }else{
                                        mAdapter.notifyItemChanged(position)
                                    }
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
        mBannerAdapter.apply {
            setOnItemClickListener(object : MyBannerAdapter.OnItemClickListener {
                override fun onItemClick(position: Int, data: BannerData) {
                    laucherToWebActivity(requireActivity(),WebViewActivity::class.java,data)
                }
            })
        }
        //开始刷新
        getBinding().refreshLayout.autoRefresh()
    }

    private fun initData() {
        PageData()
    }

    @SuppressLint("NotifyDataSetChanged")
	override fun createObserve() {
        super.createObserve()
        App.appViewModel.collectEvent.observe(this){
            for (position in mAdapter.data.indices){
                if (mAdapter.getItem(position).id==it.id){
                    mAdapter.getItem(position).collect=it.collect
                    if (mAdapter.hasHeaderLayout()){
                        mAdapter.notifyItemChanged(position+1)
                    }else{
                        mAdapter.notifyItemChanged(position)
                    }

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
        getViewModel().articleLiveData.observe(this){
            if (it.isNotEmpty()){
                Log.d("ArticleFragment", "articleList$it")
                mAdapter.setList(it)
                mAdapter.notifyDataSetChanged()
            }else{
                showEmpty()
            }
        }
    }

    private fun showEmpty() {
        mAdapter.setEmptyView(R.layout.layout_empty)
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
        getViewModel().loadArticleList(page,type)
        layout.finishLoadMore(500)
    }

    private fun onRefresh(refreshLayout: RefreshLayout) {
        Log.d("onRefresh type:", "$type")
        if (page==0){
            getViewModel().fetchBanners()
        }
        getViewModel().loadArticleList(page,type)
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
        if (mAdapter.hasHeaderLayout()){
            mAdapter.removeAllHeaderView()
        }
        Log.d("HomeArticleFragment","onDestroyView")
    }

    override fun onResume() {
        super.onResume()
        Log.d("HomeArticleFragment","onResume")
    }
}