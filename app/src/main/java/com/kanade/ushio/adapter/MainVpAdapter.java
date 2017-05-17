package com.kanade.ushio.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import me.yokeyword.fragmentation.SupportFragment;

public class MainVpAdapter extends FragmentPagerAdapter {
    private List<SupportFragment> list;

    public MainVpAdapter(List<SupportFragment> list, FragmentManager fm) {
        super(fm);
        this.list = list;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }
}
