package com.mlf.wanandroid.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.mlf.wanandroid.R
import com.mlf.wanandroid.databinding.ArticleItemBinding
import com.mlf.wanandroid.model.response.Article

class SearchArticleAdapter :
    BaseQuickAdapter<Article, BaseDataBindingHolder<ArticleItemBinding>>(R.layout.article_item),
    LoadMoreModule {
    override fun convert(holder: BaseDataBindingHolder<ArticleItemBinding>, item: Article) {
        holder.dataBinding?.apply {
            article = item
        }
    }
}