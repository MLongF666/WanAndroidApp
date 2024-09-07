package com.mlf.wanandroid.ui.fragments

import com.mlf.wanandroid.R
import com.mlf.wanandroid.base.BaseFragment
import com.mlf.wanandroid.databinding.FragmentProjectBinding
import com.mlf.wanandroid.viewmodel.ProjectViewModel

class ProjectFragment : BaseFragment<FragmentProjectBinding, ProjectViewModel>() {
    override fun getViewModelClass(): Class<ProjectViewModel> {
        return ProjectViewModel::class.java
    }

    override fun initView() {
        getBinding().textView.text=getViewModel().text.value
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_project
    }

}