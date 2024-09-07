package com.mlf.wanandroid.ui.fragments

import com.mlf.wanandroid.R
import com.mlf.wanandroid.base.BaseFragment
import com.mlf.wanandroid.databinding.FragmentHomeBinding
import com.mlf.wanandroid.viewmodel.HomeViewModel

class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(){
    override fun getViewModelClass(): Class<HomeViewModel> {
        return HomeViewModel::class.java
    }


    override fun initView() {
        getBinding().textHome.text = getViewModel().text.value
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

}