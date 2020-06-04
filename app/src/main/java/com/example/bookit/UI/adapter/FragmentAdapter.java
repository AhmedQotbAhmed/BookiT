package com.example.bookit.UI.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.bookit.fragment.AboutFragment;
import com.example.bookit.fragment.Book4friendFragment;
import com.example.bookit.fragment.Book_LaterFragment;
import com.example.bookit.fragment.EmergencyFragment;
import com.example.bookit.fragment.FreeRidesFragment;
import com.example.bookit.fragment.MyTripsFragment;
import com.example.bookit.fragment.PaymentFragment;
import com.example.bookit.fragment.PromoCodesFragment;
import com.example.bookit.fragment.SettingFragment;

public class FragmentAdapter extends FragmentPagerAdapter {

    private Context myContext;
    private int totalTabs;

    public FragmentAdapter(Context context, @NonNull FragmentManager fm, int totalTabs) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.myContext = context;
        this.totalTabs = totalTabs;

    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new Book4friendFragment();

            case 1:
                return new Book_LaterFragment();

            case 2:
                return new FreeRidesFragment();

            case 3:
                return new MyTripsFragment();


            case 4:

                return new PaymentFragment();


            case 5:
                return new EmergencyFragment();


            case 6:
                return new PromoCodesFragment();


            case 7:
                return new AboutFragment();


            case 8:
                return new SettingFragment();



            default:
                return null;


        }
    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}
