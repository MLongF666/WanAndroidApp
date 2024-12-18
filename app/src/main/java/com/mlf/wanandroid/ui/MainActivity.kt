package com.mlf.wanandroid.ui

import android.view.KeyEvent
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.mlf.wanandroid.R
import com.mlf.wanandroid.base.BaseActivity
import com.mlf.wanandroid.databinding.ActivityMainBinding
import com.mlf.wanandroid.filter.showToast

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {
    private var clickTime = 0L
    override fun initView() {
        val navView = getBinding().navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_navigation,
                R.id.navigation_official,
                R.id.navigation_project,
                R.id.navigation_mine
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun getViewModelClass(): Class<MainViewModel> {
        return MainViewModel::class.java
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //前后按键不能超过2秒，否则进行友好提示
            if (System.currentTimeMillis() - clickTime > 2000) {
                "再按一次退出！".showToast(this)
                clickTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}