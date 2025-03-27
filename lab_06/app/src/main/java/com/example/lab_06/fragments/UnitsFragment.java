package com.example.lab_06.fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lab_06.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class UnitsFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private UnitsPagerAdapter pagerAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_units, container, false);

        tabLayout = view.findViewById(R.id.unitsTabLayout);
        viewPager = view.findViewById(R.id.unitsViewPager);

        pagerAdapter = new UnitsPagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);

        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> {
                    switch (position) {
                        case 0: tab.setText("Długości"); break;
                        case 1: tab.setText("Powierzchnia"); break;
                    }
                }
        ).attach();

        return view;
    }
}
