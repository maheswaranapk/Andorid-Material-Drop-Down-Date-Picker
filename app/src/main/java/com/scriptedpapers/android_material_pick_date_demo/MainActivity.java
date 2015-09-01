package com.scriptedpapers.android_material_pick_date_demo;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.scriptedpapers.pickadate.CalendarFragment;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private CalendarFragment calendarFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calendarFragment = new CalendarFragment();

        Bundle args = new Bundle();
        Calendar cal = Calendar.getInstance();
        args.putInt(CalendarFragment.MONTH, cal.get(Calendar.MONTH) + 1);
        args.putInt(CalendarFragment.YEAR, cal.get(Calendar.YEAR));
//        args.putString(CalendarFragment.SELECTED_DATES, "2015-08-02");

        calendarFragment.setArguments(args);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.calendar1, calendarFragment);
        fragmentTransaction.commit();
    }
}
