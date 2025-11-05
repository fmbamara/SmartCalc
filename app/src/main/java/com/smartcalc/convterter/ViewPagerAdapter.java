public class ViewPagerAdapter {
    package com.smartcalc.converter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPagerAdapter extends FragmentStateAdapter {

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new CalculatorFragment();
            case 1:
                return new ConverterFragment();
            case 2:
                return new ReportsFragment();
            default:
                return new CalculatorFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}

}
