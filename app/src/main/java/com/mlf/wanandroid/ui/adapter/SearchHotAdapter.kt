package com.mlf.wanandroid.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.mlf.wanandroid.R
import com.mlf.wanandroid.databinding.HotItemBinding
import com.mlf.wanandroid.model.response.Hotkey

class SearchHotAdapter() :
    BaseQuickAdapter<Hotkey, BaseDataBindingHolder<HotItemBinding>>(R.layout.hot_item) {
    private var onItemClickListener: OnItemClickListener? = null

    init {
        setAnimationWithDefault(BaseQuickAdapter.AnimationType.ScaleIn)
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int, data: Hotkey)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.onItemClickListener = listener
    }

    override fun convert(holder: BaseDataBindingHolder<HotItemBinding>, item: Hotkey) {
        holder.dataBinding?.apply {
            key = item
            executePendingBindings()
        }
        holder.itemView.setOnClickListener {
            onItemClickListener?.onItemClick(holder.bindingAdapterPosition, item)
        }
    }

}
