package com.mlf.wanandroid.ui.adapter


import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.mlf.wanandroid.R
import com.mlf.wanandroid.databinding.ArticleItemBinding

import com.mlf.wanandroid.model.response.Article

class ArticleAdapter:BaseQuickAdapter<Article,BaseDataBindingHolder<ArticleItemBinding>>(R.layout.article_item),LoadMoreModule {

	init {
		setAnimationWithDefault(AnimationType.AlphaIn)
	}
	override fun convert(holder: BaseDataBindingHolder<ArticleItemBinding>, item: Article) {
		holder.dataBinding?.apply {
			if (item.author=="" || item.author=="null"){
				item.author=item.shareUser
			}
			article=item
			executePendingBindings()
		}
	}
}