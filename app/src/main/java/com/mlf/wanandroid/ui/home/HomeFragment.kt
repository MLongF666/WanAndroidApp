package com.mlf.wanandroid.ui.home

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.mlf.wanandroid.R
import com.mlf.wanandroid.base.BaseFragment
import com.mlf.wanandroid.base.MyFragmentPagerAdapter
import com.mlf.wanandroid.databinding.FragmentHomeBinding
import com.mlf.wanandroid.ui.home.home.HomeArticleFragment

class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(){
    private lateinit var tabLayout: TabLayout
    private lateinit var listFragment: ArrayList<Fragment>
    override fun getViewModelClass(): Class<HomeViewModel> {
        return HomeViewModel::class.java
    }
    override fun initView() {
        listFragment = ArrayList()
        listFragment.add(HomeArticleFragment())
        listFragment.add(AskFragment())
        listFragment.add(SquareFragment())
        tabLayout = getBinding().tabLayout
        tabLayout.addTab(tabLayout.newTab().setText("首页"))
        tabLayout.addTab(tabLayout.newTab().setText("问答"))
        tabLayout.addTab(tabLayout.newTab().setText("广场"))
        val viewPager = getBinding().viewPager
        val myFragmentPagerAdapter =
            MyFragmentPagerAdapter(childFragmentManager, lifecycle, listFragment)
        viewPager.adapter = myFragmentPagerAdapter
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                    viewPager.currentItem = tab!!.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                tabLayout.selectTab(tabLayout.getTabAt(position))
            }
        })
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun onStop() {
        super.onStop()
        Log.d("HomeFragment","onStop")
    }

}