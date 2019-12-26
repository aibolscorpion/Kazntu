package kz.almaty.satbayevuniversity.ui.notification;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import kz.almaty.satbayevuniversity.R;
import kz.almaty.satbayevuniversity.databinding.NotificationFragmentBinding;

public class NotificationFragment extends Fragment {

    private NotificationViewModel mViewModel;
    private NotificationFragmentBinding notificationFragmentBinding;
    private NotificationAdapter notificationAdapter;
    public static final String NOTIFICATION_TAG = "NotificationFragment";

    public static NotificationFragment newInstance() {
        return new NotificationFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        notificationFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.notification_fragment, container, false);
        View view = notificationFragmentBinding.getRoot();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(NotificationViewModel.class);
        notificationFragmentBinding.setNotification(mViewModel);
        mViewModel.getNotification();

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.notifications);

        notificationFragmentBinding.notificationRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        notificationFragmentBinding.notificationRecyclerView.setHasFixedSize(true);

        notificationAdapter = new NotificationAdapter(getActivity());
        notificationFragmentBinding.notificationRecyclerView.setAdapter(notificationAdapter);
        getNotification();

        mViewModel.getHandleTimeout().observe(this, aBoolean -> {
            if (aBoolean) {
                Toast.makeText(getActivity(), R.string.internetConnection, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getNotification() {
        mViewModel.getNotificationMutableLiveData().observe(this, notifications -> {
            notificationAdapter.setResponseNotificationList(notifications);
        });
    }


}
