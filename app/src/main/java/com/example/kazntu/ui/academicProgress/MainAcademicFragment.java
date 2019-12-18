package com.example.kazntu.ui.academicProgress;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.kazntu.R;
import com.example.kazntu.data.entity.notification.Notification;
import com.example.kazntu.ui.HomeActivity;
import com.example.kazntu.ui.grade.attestation.GradeViewModel;
import com.example.kazntu.ui.grade.ViewPagerFragment;
import com.example.kazntu.ui.grade.transcriptFragment.TranscriptViewModel;
import com.example.kazntu.ui.notification.NotificationFragment;
import com.example.kazntu.ui.notification.NotificationViewModel;
import com.example.kazntu.ui.schedule.ViewPagerSchedule;
import com.example.kazntu.ui.schedule.exams.ExamsViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import static com.example.kazntu.ui.HomeActivity.FRAGMENT_FIRST;
import static com.example.kazntu.ui.HomeActivity.FRAGMENT_SECOND;
import static com.example.kazntu.ui.HomeActivity.FRAGMENT_THIRD;
import static com.example.kazntu.ui.notification.NotificationFragment.NOTIFICATION_TAG;

public class MainAcademicFragment extends Fragment {

    private AcademicFragment academicFragment = new AcademicFragment();
    private BottomNavigationView navigation;
    private static final String TAG = "MainAcademicFragment";
    public Toolbar toolbar;
    public ImageView imageView;
    private AcademicViewModel academicViewModel = new AcademicViewModel();
    private GradeViewModel gradeViewModel = new GradeViewModel();
    private TranscriptViewModel transcriptViewModel = new TranscriptViewModel();
    private NotificationViewModel notificationViewModel = new NotificationViewModel();
    private ExamsViewModel examsViewModel = new ExamsViewModel();

    public static MainAcademicFragment newInstance() {
        return new MainAcademicFragment();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
        switch (item.getItemId()) {
            case R.id.academicProgressFragment:
                replaceFragment(AcademicFragment.newInstance(),FRAGMENT_FIRST, R.id.main_academic_fragment_container);
                return true;
            case R.id.schedule:
                replaceFragment(ViewPagerSchedule.newInstance(),FRAGMENT_SECOND, R.id.main_academic_fragment_container);
                return true;
            case R.id.grade:
                replaceFragment(ViewPagerFragment.newInstance(),FRAGMENT_THIRD, R.id.main_academic_fragment_container);
                return true;
            case R.id.notifications:
                replaceFragment(NotificationFragment.newInstance(),NOTIFICATION_TAG, R.id.main_academic_fragment_container);
        return true;
        }
        return false;
    };
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.main_academic_fragment, container, false);
        toolbar = view.findViewById(R.id.mainToolbar);
        navigation = view.findViewById(R.id.bottomNavigation);
        imageView = view.findViewById(R.id.updateData);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.academicProgressFragment);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        getToolbar();
        // TODO: Use the ViewModel
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.journal);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(v -> {
            ((HomeActivity)getActivity()).OpenToggleNavMenu();
        });

        ConnectivityManager connManager =
                (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = connManager.getActiveNetworkInfo();

        imageView.setOnClickListener(v -> {

            if( connManager.getActiveNetworkInfo() != null && connManager.getActiveNetworkInfo().isAvailable() && activeNetwork.isConnected()) {

                if(toolbar.getTitle().toString().equalsIgnoreCase(getString(R.string.journal))) {
                    replaceFragmentBackStack(AcademicFragment.newInstance());
                }else if(toolbar.getTitle().toString().equalsIgnoreCase(getString(R.string.schedule))){
                    replaceFragmentBackStack(ViewPagerSchedule.newInstance());
                }else if(toolbar.getTitle().toString().equalsIgnoreCase(getString(R.string.grade))){
                    replaceFragmentBackStack(ViewPagerFragment.newInstance());
                }else if(toolbar.getTitle().toString().equalsIgnoreCase(getString(R.string.notifications))){
                    replaceFragmentBackStack(NotificationFragment.newInstance());
                }

            } else Toast.makeText(getActivity(), R.string.internetConnection, Toast.LENGTH_SHORT).show();

        });

    }

    private void replaceFragment(Fragment newFragment, String tag, int container) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(container, newFragment, tag)
                .addToBackStack(tag)
                .commit();
    }
    private void replaceFragmentBackStack(Fragment newFragment) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.main_academic_fragment_container, newFragment)
                .commit();
    }
}
