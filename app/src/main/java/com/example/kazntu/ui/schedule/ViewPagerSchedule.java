package com.example.kazntu.ui.schedule;

import androidx.lifecycle.ViewModelProviders;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kazntu.R;
import com.example.kazntu.ui.schedule.exams.ExamsFragment;
import com.example.kazntu.ui.schedule.scheduleFragment.ScheduleFragment;
import com.example.kazntu.ui.viewPager.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class ViewPagerSchedule extends Fragment {

    private CustomViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private TabLayout tabLayout;


    public static ViewPagerSchedule newInstance() {
        return new ViewPagerSchedule();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_pager_schedule_fragment, container, false);

        tabLayout = view.findViewById(R.id.tabLayoutSchedule);
        viewPager = view.findViewById(R.id.viewPagerSchedule);

        return view;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewPager.setPagingEnabled(false);

        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        // TODO: Use the ViewModel
    }


    private void setupViewPager(ViewPager viewPager){
        viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());

        viewPagerAdapter.addFragment(new ScheduleFragment(), getString(R.string.schedule));
        viewPagerAdapter.addFragment(new ExamsFragment(), getString(R.string.exam));

        viewPager.setAdapter(viewPagerAdapter);
    }


}
