package com.mlf.wanandroid.ui.adapter

import android.util.Log
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.mlf.wanandroid.ext.load
import com.mlf.wanandroid.model.response.BannerData
import com.youth.banner.adapter.BannerAdapter

class MyBannerAdapter(dataList:ArrayList<BannerData>):
BannerAdapter<BannerData,MyBannerAdapter.BannerViewHolder>(dataList){
	private var onItemClickListener:OnItemClickListener?=null
	override fun onCreateHolder(parent: ViewGroup?, viewType: Int): BannerViewHolder {
		Log.d("MyBannerAdapter", "onCreateHolder")
		val imageView = ImageView(parent?.context)
		imageView.layoutParams=ViewGroup.LayoutParams(
			ViewGroup.LayoutParams.MATCH_PARENT,
			ViewGroup.LayoutParams.MATCH_PARENT
		)
		imageView.scaleType=ImageView.ScaleType.CENTER_CROP
		return BannerViewHolder(imageView)
	}
	fun setOnItemClickListener(listener: OnItemClickListener){
		this.onItemClickListener=listener
	}

	override fun onBindView(
		holder: BannerViewHolder?,
		data: BannerData?,
		position: Int,
		size: Int
	) {
		holder?.imageView?.apply {
			load(data?.imagePath!!)
			setOnClickListener {
				onItemClickListener?.onItemClick(position,data)
			}
		}
	}
	inner class BannerViewHolder(var imageView: ImageView):RecyclerView.ViewHolder(imageView) {

	}
	interface OnItemClickListener{
		fun onItemClick(position: Int, data: BannerData)
	}
}