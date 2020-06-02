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
                Book4friendFragment book4friendFragment = new Book4friendFragment();
                return book4friendFragment;
            case 1:
                Book_LaterFragment book_LaterFragment = new Book_LaterFragment();
                return book_LaterFragment;
            case 2:
                FreeRidesFragment freeRidesFragment = new FreeRidesFragment();
                return freeRidesFragment;
            case 3:
                MyTripsFragment myTripsFragment = new MyTripsFragment();
                return myTripsFragment;

            case 4:
                PaymentFragment paymentFragment = new PaymentFragment();
                return paymentFragment;

            case 5:
                EmergencyFragment emergencyFragment = new EmergencyFragment();
                return emergencyFragment;

            case 6:
                PromoCodesFragment promoCodesFragment = new PromoCodesFragment();
                return promoCodesFragment;

            case 7:
                AboutFragment aboutFragment = new AboutFragment();
                return aboutFragment;

            case 8:
                SettingFragment settingFragment = new SettingFragment();
                return settingFragment;


            default:
                return null;


        }
    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}
