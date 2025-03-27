package com.example.lab_06;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.lab_06.fragments.NumbersFragment;
import com.example.lab_06.fragments.CurrencyFragment;
import com.example.lab_06.fragments.UnitsFragment;

public class MyPagerAdapter extends FragmentStateAdapter {

    public MyPagerAdapter(@NonNull MainActivity activity) {
        super(activity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0: return new NumbersFragment();
            case 1: return new CurrencyFragment();
            case 2: return new UnitsFragment();
        }
        return new NumbersFragment();
    }

    @Override
    public int getItemCount() {
        return 3; // 3 zakładki
    }
}
