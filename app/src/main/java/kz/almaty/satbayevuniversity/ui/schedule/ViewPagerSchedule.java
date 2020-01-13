package kz.almaty.satbayevuniversity.ui.schedule;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kz.almaty.satbayevuniversity.R;
import kz.almaty.satbayevuniversity.ui.schedule.exams.ExamsFragment;
import kz.almaty.satbayevuniversity.ui.schedule.scheduleFragment.ScheduleFragment;
import kz.almaty.satbayevuniversity.ui.viewPager.ViewPagerAdapter;
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

        Bundle bundle = this.getArguments();
        if(bundle !=null){
            if(bundle.getBoolean(getString(R.string.only_server))){
                setupViewPager(viewPager,true);
            }
        }else{
            setupViewPager(viewPager,false);
        }
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


    private void setupViewPager(ViewPager viewPager,boolean onlyServer){
        viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        ScheduleFragment scheduleFragment = ScheduleFragment.newInstance();
        ExamsFragment examsFragment = ExamsFragment.newInstance();

        Bundle bundle = new Bundle();
        bundle.putBoolean(getString(R.string.only_server),true);

        if(onlyServer){
            scheduleFragment.setArguments(bundle);
            examsFragment.setArguments(bundle);
        }
        viewPagerAdapter.addFragment(scheduleFragment, getString(R.string.schedule));
        viewPagerAdapter.addFragment(examsFragment, getString(R.string.exam));

        viewPager.setAdapter(viewPagerAdapter);
    }

}
