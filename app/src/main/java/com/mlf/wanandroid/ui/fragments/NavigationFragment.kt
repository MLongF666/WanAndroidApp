package com.mlf.wanandroid.ui.fragments

import com.mlf.wanandroid.R
import com.mlf.wanandroid.base.BaseFragment
import com.mlf.wanandroid.databinding.FragmentNavigationBinding
import com.mlf.wanandroid.viewmodel.NavigationViewModel


class NavigationFragment : BaseFragment<FragmentNavigationBinding, NavigationViewModel>() {
    override fun getViewModelClass(): Class<NavigationViewModel> {
        return NavigationViewModel::class.java
    }

    override fun initView() {
        getBinding().textView.text = getViewModel().text.value
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_navigation
    }

}