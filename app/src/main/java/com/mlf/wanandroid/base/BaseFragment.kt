package com.mlf.wanandroid.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


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
        initView()
        return _binding?.root
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
        _binding?.unbind()
    }

    abstract fun initView()

    abstract fun getLayoutId(): Int

}