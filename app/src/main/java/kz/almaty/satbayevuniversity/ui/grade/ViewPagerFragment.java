package kz.almaty.satbayevuniversity.ui.grade;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kz.almaty.satbayevuniversity.R;
import kz.almaty.satbayevuniversity.ui.grade.attestation.GradeFragment;
import kz.almaty.satbayevuniversity.ui.grade.transcriptFragment.TranscriptFragment;
import kz.almaty.satbayevuniversity.ui.viewPager.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class ViewPagerFragment extends Fragment {


    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private TabLayout tabLayout;

    public static ViewPagerFragment newInstance() {
        return new ViewPagerFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_pager_fragment, container, false);

        tabLayout = view.findViewById(R.id.tabLayoutGrade);
        viewPager = view.findViewById(R.id.viewPager);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.grade);

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

        viewPagerAdapter.addFragment(new GradeFragment(), "Аттестация");
        viewPagerAdapter.addFragment(new TranscriptFragment(), "Транскрипт");

        viewPager.setAdapter(viewPagerAdapter);
    }
}