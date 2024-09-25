package com.mlf.wanandroid.ui.home


import com.mlf.wanandroid.R
import com.mlf.wanandroid.base.BaseFragment
import com.mlf.wanandroid.databinding.FragmentSquareBinding


class SquareFragment : BaseFragment<FragmentSquareBinding, SquareViewModel>() {
    override fun getViewModelClass(): Class<SquareViewModel> {
        return SquareViewModel::class.java
    }

    override fun initView() {

    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_square
    }

}