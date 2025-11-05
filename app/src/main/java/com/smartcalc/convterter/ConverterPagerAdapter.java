package com.smartcalc.convterter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ConverterPagerAdapter extends FragmentStateAdapter {

    public ConverterPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0: return new CurrencyFragment();
            case 1: return new LengthFragment();
            default: return new TemperatureFragment();
        }
    }

    @Override public int getItemCount() { return 3; }
}
