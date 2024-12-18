package com.mlf.wanandroid.ui.officia

import com.mlf.wanandroid.R
import com.mlf.wanandroid.base.BaseFragment
import com.mlf.wanandroid.databinding.FragmentOfficialBinding

class OfficialFragment : BaseFragment<FragmentOfficialBinding, OfficialViewModel>() {
    override fun getViewModelClass(): Class<OfficialViewModel> {
        return OfficialViewModel::class.java
    }

    override fun initView() {
        getBinding().text.text = getViewModel().text.value
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_official
    }


}