package com.mlf.wanandroid.ui.fragments

import com.mlf.wanandroid.R
import com.mlf.wanandroid.base.BaseFragment
import com.mlf.wanandroid.databinding.FragmentMineBinding
import com.mlf.wanandroid.viewmodel.MineViewModel

/**
 * @description: TODO 我的信息
 * @author: mlf
 * @date: 2024/9/6 15:44
 * @version: 1.0
 */
class MineFragment: BaseFragment<FragmentMineBinding, MineViewModel>() {
    override fun getViewModelClass(): Class<MineViewModel> {
        return MineViewModel::class.java
    }

    override fun initView() {
        getBinding().tv.text=getViewModel().text.value
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_mine
    }
}