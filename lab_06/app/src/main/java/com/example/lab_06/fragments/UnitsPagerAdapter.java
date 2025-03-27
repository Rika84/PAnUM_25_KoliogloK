package com.example.lab_06.fragments;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class UnitsPagerAdapter extends FragmentStateAdapter {

    public UnitsPagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0: return new LengthFragment();
            case 1: return new AreaFragment();
        }
        return new LengthFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
