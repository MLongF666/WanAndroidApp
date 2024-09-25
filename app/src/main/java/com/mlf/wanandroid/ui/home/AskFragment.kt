package com.mlf.wanandroid.ui.home


import androidx.lifecycle.Observer
import com.mlf.wanandroid.R
import com.mlf.wanandroid.base.BaseFragment
import com.mlf.wanandroid.databinding.FragmentAskBinding
import com.mlf.wanandroid.filter.showToast


class AskFragment : BaseFragment<FragmentAskBinding, AskViewModel>() {
    override fun getViewModelClass(): Class<AskViewModel> {
        return AskViewModel::class.java
    }

    override fun initView() {
        getViewModel().text.observe(this, Observer {
            "text".showToast(requireContext())
        })
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_ask
    }

}