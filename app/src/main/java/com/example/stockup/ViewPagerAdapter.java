package com.example.stockup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

class ViewPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> mfragmentList;

    public ViewPagerAdapter(@NonNull FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        this.mfragmentList=fragmentList;
    }

    @Override
    public Fragment getItem(int postition) {
        return mfragmentList.get(postition);
    }

    @Override
    public int getCount() {
        return mfragmentList.size();
    }
}
