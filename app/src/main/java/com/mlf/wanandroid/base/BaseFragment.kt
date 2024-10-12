package com.mlf.wanandroid.base

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mlf.wanandroid.BR
import com.mlf.wanandroid.model.bean.ArticleData
import com.mlf.wanandroid.model.bean.CollectData
import com.mlf.wanandroid.model.response.BannerData


/**
 * @description: TODO 父类Fragment
 * @author: mlf
 * @date: 2024/9/5 21:09
 * @version: 1.0
 */
abstract class BaseFragment<V: ViewDataBinding, VM: ViewModel>: Fragment() {
    private var _binding: V?=null
    private lateinit var mViewModel: VM
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= DataBindingUtil.inflate(inflater,getLayoutId(),container,false)
        mViewModel= ViewModelProvider(this)[getViewModelClass()]
        Log.d("BaseFragment","onCreateView")

        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("BaseFragment","onViewCreated")
        initView()
        setupDataBinding()
        createObserve()
    }
    fun laucherToWebActivity(activity: Activity, clazz: Class<*>, articleData: ArticleData) {
        val intent = Intent(activity, clazz)
        intent.putExtra("articleData", articleData)
        intent.putExtra("type",0)
        activity.startActivity(intent)
    }
    fun laucherToWebActivity(activity: Activity, clazz: Class<*>, bannerData: BannerData) {
        val intent = Intent(activity, clazz)
        intent.putExtra("type",1)
        intent.putExtra("bannerData", bannerData)
        activity.startActivity(intent)
    }
    override fun onStart() {
        super.onStart()
        Log.d("BaseFragment","onStart")
    }

    open fun createObserve() {

    }

    fun getBinding(): V{
        return _binding!!
    }
    fun getViewModel(): VM{
        return mViewModel
    }
    abstract fun getViewModelClass(): Class<VM>
    override fun onDestroy() {
        super.onDestroy()
//        _binding?.unbind()
    }

    override fun onStop() {
        super.onStop()
        _binding?.unbind()
        Log.d("BaseFragment","onStop")
    }
    /** DataBinding相关设置 */
    private fun setupDataBinding() {
        _binding?.apply {
            // 需绑定lifecycleOwner到Fragment,xml绑定的数据才会随着liveData数据源的改变而改变
            lifecycleOwner = viewLifecycleOwner
            setVariable(BR.viewModel, mViewModel)
        }
    }
    abstract fun initView()

    abstract fun getLayoutId(): Int
    //===================工具类==================

}