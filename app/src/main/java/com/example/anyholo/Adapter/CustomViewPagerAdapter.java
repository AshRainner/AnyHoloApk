package com.example.anyholo.Adapter;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

public class CustomViewPagerAdapter extends FragmentStateAdapter {
    private List<Fragment> fragments = new ArrayList<>();
    public CustomViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }
    public void addFragment(Fragment fragment){
        fragments.add(fragment);
    }
    @NonNull
    @Override
    public Fragment createFragment(int position) {return fragments.get(position);
    }

    @Override
    public int getItemCount() {
        return fragments.size();
    }
}
