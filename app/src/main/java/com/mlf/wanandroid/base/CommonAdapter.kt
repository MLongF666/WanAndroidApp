package com.mlf.wanandroid.base

import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

/**
 * @description: TODO RecylerView万能适配器
 * @author: mlf
 * @date: 2024/9/6 11:21
 * @version: 1.0
 */
open class CommonAdapter<T : Any> : RecyclerView.Adapter<CommonViewHolder> {
    private var onItemClick: OnItemClick<T>? = null

    interface OnItemClick<U> {
        fun onItemClick(position: Int, view: View, data: U)
    }

    //构造方法 在这里传入 实体类与接口
    constructor(data: List<T>, listener: OnBindDataListener<T>) {
        this.mData = data
        this.onBindDataListener = listener
    }

    //对需要分类加载布局的列表 使用OnMoreBindDataListener 这个方法提供了我们要设置的type
    constructor(data: List<T>, listener: OnMoreBindDataListener<T>) {
        this.mData = data
        this.onBindDataListener = listener
        this.onMoreBindDataListener = listener
    }

    //数据
    private lateinit var mData: List<T>

    //接口
    private var onBindDataListener: OnBindDataListener<T>? = null
    private var onMoreBindDataListener: OnMoreBindDataListener<T>? = null

    //这里是 向viewholder中传入要加载的布局id
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            CommonViewHolder {
        val viewDataBinding: ViewDataBinding? =
            onBindDataListener?.getDataViewBinding(viewType, parent)
        return CommonViewHolder.getViewHolder(viewDataBinding!!)

    }

    fun setOnItemClick(onItemClick: OnItemClick<T>) {
        this.onItemClick = onItemClick
    }

    fun getItem(position: Int): T {
        return mData[position]
    }

    //加载的个数
    override fun getItemCount(): Int {
        return mData.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    //这里是 实体类与布局进行绑定的方法
    override fun onBindViewHolder(holder: CommonViewHolder, position: Int) {
        //这个是自定义的
        if (onItemClick != null) {
            holder.itemView.setOnClickListener {
                onItemClick?.onItemClick(position, holder.itemView, mData[position])
            }
        }
        onBindDataListener?.onBindViewHolder(
            mData[position],
            holder,
            getItemViewType(position),
            position
        )
    }

    override fun onBindViewHolder(
        holder: CommonViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position)
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (onMoreBindDataListener != null) {
            return onMoreBindDataListener!!.getItemViewType(position)
        }
        return 0
    }

    interface OnBindDataListener<T> {
        fun onBindViewHolder(model: T, viewHolder: CommonViewHolder, type: Int, position: Int)
        fun getDataViewBinding(type: Int, parent: ViewGroup): ViewDataBinding
    }

    interface OnMoreBindDataListener<T> : OnBindDataListener<T> {
        fun getItemViewType(position: Int): Int
    }


}