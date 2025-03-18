package com.example.lab_04;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class MyPagerAdapter extends FragmentStateAdapter {

    public MyPagerAdapter(@NonNull MainActivity activity) {
        super(activity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            return new StopwatchesFragment();
        } else {
            return new TimersFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
