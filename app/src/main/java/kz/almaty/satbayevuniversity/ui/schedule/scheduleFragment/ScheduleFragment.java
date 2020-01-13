package kz.almaty.satbayevuniversity.ui.schedule.scheduleFragment;

import android.annotation.SuppressLint;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import kz.almaty.satbayevuniversity.R;
import kz.almaty.satbayevuniversity.data.AccountDao;
import kz.almaty.satbayevuniversity.data.App;
import kz.almaty.satbayevuniversity.data.AppDatabase;
import kz.almaty.satbayevuniversity.data.entity.schedule.Schedule;
import kz.almaty.satbayevuniversity.databinding.ScheduleFragmentBinding;
import kz.almaty.satbayevuniversity.ui.LoginActivity;
import kz.almaty.satbayevuniversity.utils.OnSwipeTouchListener;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class ScheduleFragment extends Fragment implements Cloneable{

    ArrayList<Schedule> localScheduleList;
    private ScheduleViewModel mViewModel;
    private ScheduleAdapter scheduleAdapter;
    private DateTime currentDay= new DateTime();
    private AppDatabase db = App.getInstance().getDatabase();
    private AccountDao accountDao = db.accountDao();
    private RecyclerView recyclerView;
    private ConstraintLayout emptyConstraint;
    ScheduleFragmentBinding scheduleFragmentBinding;
    private int i=0;

    public ScheduleFragment() {
    }

    public static ScheduleFragment newInstance() {
        return new ScheduleFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        scheduleFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.schedule_fragment, container, false);
        View view = scheduleFragmentBinding.getRoot();
        emptyConstraint = view.findViewById(R.id.emptyConstraint);
        return view;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ScheduleViewModel.class);
        scheduleFragmentBinding.setSchedule(mViewModel);

        Bundle bundle = this.getArguments();
        if(bundle !=null){
            if(bundle.getBoolean(getString(R.string.only_server))){
                mViewModel.getSchedule(true);
            }
        }
        else{
                mViewModel.getSchedule(false);
            }

        scheduleFragmentBinding.scheduleRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        scheduleFragmentBinding.scheduleRecyclerView.setHasFixedSize(true);

        scheduleAdapter = new ScheduleAdapter(getActivity());
        scheduleFragmentBinding.scheduleRecyclerView.setAdapter(scheduleAdapter);
        scheduleFragmentBinding.scheduleRecyclerView.setItemAnimator(null);


        setDateSchedule(currentDay);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.schedule);
        scheduleFragmentBinding.scheduleRecyclerView.setNestedScrollingEnabled(false);
        scheduleFragmentBinding.weekCalendar.reset();

        //swipe weekCalendar
        scheduleFragmentBinding.scheduleRecyclerView.setOnTouchListener(new OnSwipeTouchListener(getContext()) {

            public void onSwipeRight() {
                scheduleFragmentBinding.weekCalendar.moveToPrevious();
                currentDay = currentDay.minusDays(1);
                setDateSchedule(currentDay);
            }
            public void onSwipeLeft() {
                scheduleFragmentBinding.weekCalendar.moveToNext();
                currentDay = currentDay.plusDays(1);
                setDateSchedule(currentDay);
            }
        });

        scheduleFragmentBinding.weekCalendar.setOnDateClickListener(dateTime -> {
            setDateSchedule(dateTime);
            currentDay = dateTime;
        });

        mViewModel.getHandleTimeout().observe(this, integer -> {
            if (integer == 1) {
                Toast.makeText(getActivity(), R.string.internetConnection, Toast.LENGTH_SHORT).show();
            }
        });

        mViewModel.getHandleError().observe(this, integer -> {
            if (integer == 401) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

    }

    private void setDateSchedule(DateTime dateTime) {
        mViewModel.getScheduleLiveData().observe(this, scheduleList -> {

            ArrayList<Schedule> result = new ArrayList<>();
            localScheduleList = new ArrayList<>(Arrays.asList(
                    new Schedule("7:50", "8:40", 1),
                    new Schedule("8:55", "9:45", 3),
                    new Schedule("10:00", "10:50", 5),
                    new Schedule("11:05", "11:55", 7),
                    new Schedule("12:10", "13:00", 9),
                    new Schedule("13:15", "14:05", 11),
                    new Schedule("14:20", "15:10", 13),
                    new Schedule("15:25", "16:15", 15),
                    new Schedule("16:30", "17:20", 17),
                    new Schedule("17:30", "18:20", 19),
                    new Schedule("18:30", "19:20", 21),
                    new Schedule("19:30", "20:20", 23),
                    new Schedule("20:30", "21:20", 25),
                    new Schedule("21:30", "22:20", 27)));

            for (Schedule schedule : scheduleList) {
                if (schedule.getDayOfWeekId() == dateTime.dayOfWeek().get()) {
                    result.add(Schedule.copy(schedule));
                }
            }
            f:
            for (int i=0;i<localScheduleList.size();i++) {
                for (int j=0;j<result.size();j++) {
                    if (localScheduleList.get(i).getStartTimeId() == result.get(j).getStartTimeId() ) {
                        if (localScheduleList.get(i+1).getEndTime().contains(result.get(j).getEndTime())) {
                                Schedule schedule1 = Schedule.copy(result.get(j));
                                schedule1.setStartTime(localScheduleList.get(i + 1).getStartTime());
                                schedule1.setStartTimeId(result.get(j).getStartTimeId() + 2);
                                result.add(schedule1);
                                result.get(j).setEndTime(localScheduleList.get(i).getEndTime());
                        }
                        continue f;
                    }

                }

                result.add(localScheduleList.get(i));
            }

            Collections.sort(result, Schedule.CompareId);

            for (Schedule schedule : result) {
                if (schedule.getDayOfWeekId() == 0){
                    i++;
                }
                System.out.println(schedule);
            }
            if(i==14){
                emptyConstraint.setVisibility(View.VISIBLE);
            } else{
                emptyConstraint.setVisibility(View.INVISIBLE);
            }
            scheduleAdapter.setScheduleList(result);
            i = 0;

        });

    }
}