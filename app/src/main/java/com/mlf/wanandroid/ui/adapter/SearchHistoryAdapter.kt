package com.mlf.wanandroid.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.mlf.wanandroid.R
import com.mlf.wanandroid.databinding.HistoryItemBinding
import com.mlf.wanandroid.room.entity.HistorySearch

class SearchHistoryAdapter :
    BaseQuickAdapter<HistorySearch, BaseDataBindingHolder<HistoryItemBinding>>(R.layout.history_item) {
    init {
        setAnimationWithDefault(BaseQuickAdapter.AnimationType.ScaleIn)
    }

    override fun convert(holder: BaseDataBindingHolder<HistoryItemBinding>, item: HistorySearch) {
        holder.dataBinding?.apply {
            key = item
        }
    }

}
