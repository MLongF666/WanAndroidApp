package com.mlf.wanandroid.base;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @description: TODO
 * @author: mlf
 * @date: 2024/9/11 16:26
 * @version: 1.0
 */
public class MyFragmentPagerAdapter extends FragmentStateAdapter {
    ArrayList<Fragment> fragmentList = new ArrayList<>();
    public MyFragmentPagerAdapter(FragmentManager fragmentManager, Lifecycle lifecycle, ArrayList<Fragment> listFragment) {
        super(fragmentManager, lifecycle);
        this.fragmentList = listFragment;

    }
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getItemCount() {
        return fragmentList.size();
    }
}


