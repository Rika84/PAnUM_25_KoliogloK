package com.example.lab_07.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.lab_07.fragments.GameFragment;
import com.example.lab_07.fragments.SummaryFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new GameFragment();
            case 1:
                return new SummaryFragment();
            default:
                return new GameFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
