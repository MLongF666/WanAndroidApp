package com.mlf.wanandroid.ui.webview

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Build
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.mlf.wanandroid.R
import com.mlf.wanandroid.base.App
import com.mlf.wanandroid.base.BaseActivity
import com.mlf.wanandroid.databinding.ActivityWebViewBinding
import com.mlf.wanandroid.filter.showToast
import com.mlf.wanandroid.model.bean.ArticleData
import com.mlf.wanandroid.model.bean.CollectData
import com.mlf.wanandroid.model.response.BannerData

class WebViewActivity : BaseActivity<ActivityWebViewBinding, WebViewViewModel>() {
    companion object {
        const val ARTICLEDATA = "articleData"
        const val BANNERDATA = "bannerData"
    }

    private var article: ArticleData? = null
    private var mUrl: String? = null
    private var mTitle: String? = null
    private var mIsCollect: Boolean = false
    private var mArticleId: Int = 0
    private var isArticle = false

    @SuppressLint("SetJavaScriptEnabled")
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun initView() {
        getData()
        initActionBar()
        isHideTitle(false)
        getBinding().webView.apply {
            settings.apply {
                javaScriptEnabled = true
                domStorageEnabled = true
                loadWithOverviewMode = true
            }
            webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    getBinding().progressBar.visibility = View.GONE
                }

                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    super.onPageStarted(view, url, favicon)
                    getBinding().progressBar.visibility = View.VISIBLE
                }
            }
            webChromeClient = object : WebChromeClient() {
                override fun onProgressChanged(view: WebView?, newProgress: Int) {
                    super.onProgressChanged(view, newProgress)
                    getBinding().progressBar.progress = newProgress
                }
            }
        }
        mUrl?.let { getBinding().webView.loadUrl(it) }
    }

    private fun initActionBar() {
        //设置透明背景
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            mTitle?.let {
                title = it
                //设置颜色
                setDisplayShowTitleEnabled(true)
            }
            //设置icon点击事件
            setHomeButtonEnabled(true)
            setIcon(R.drawable.baseline_more_vert_24)
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun getData() {
        intent.apply {
            val i = getIntExtra("type", 0)
            Log.d("WebViewActivity", "type:$i")
            if (i == 0) {
                article = getParcelableExtra<ArticleData>(ARTICLEDATA)
                if (article != null) {
                    mUrl = article!!.link
                    mTitle = article!!.title
                    mIsCollect = article!!.collect
                    mArticleId = article!!.id
                    isArticle = true
                }
            } else {
                val data = getParcelableExtra<BannerData>(BANNERDATA)
                isArticle = false
                if (data != null) {
                    mUrl = data.url
                    mTitle = data.title
                }
            }

        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_web_view, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        val collectItem = menu?.findItem(R.id.collect)
        if (isArticle) {
            collectItem?.icon = ContextCompat.getDrawable(
                this,
                if (mIsCollect) R.drawable.like_select else R.drawable.like_fill
            )
        } else {
            collectItem?.isVisible = false
        }
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.collect -> {
                if (mIsCollect) {
                    getViewModel().unCollectArticle(mArticleId) {
                        "取消点赞".showToast(this)
                        item.setIcon(R.drawable.like_fill)
                        App.appViewModel.collectEvent.setValue(
                            CollectData(mArticleId, mUrl!!, collect = false)
                        )
                        mIsCollect = !mIsCollect
                        invalidateOptionsMenu()
                    }
                } else {
                    getViewModel().collectArticle(mArticleId) {
                        "点赞".showToast(this)
                        item.setIcon(R.drawable.like_select)
                        App.appViewModel.collectEvent.setValue(
                            CollectData(mArticleId, mUrl!!, collect = true)
                        )
                        mIsCollect = !mIsCollect
                        invalidateOptionsMenu()
                    }
                }
//				mIsCollect = !mIsCollect
//				invalidateOptionsMenu()
                //网络请求收藏
                true
            }

            R.id.web_select -> {
                "选择".showToast(this)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_web_view
    }

    override fun getViewModelClass(): Class<WebViewViewModel> {
        return WebViewViewModel::class.java
    }
}