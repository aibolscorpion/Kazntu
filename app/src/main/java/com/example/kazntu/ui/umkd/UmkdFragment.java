package com.example.kazntu.ui.umkd;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kazntu.R;
import com.example.kazntu.databinding.UmkdFragmentBinding;
import com.example.kazntu.ui.HomeActivity;


public class UmkdFragment extends Fragment {

    private UmkdViewModel mViewModel;
    private UmkdFragmentBinding umkdFragmentBinding;
    private UmkdAdapter umkdAdapter;
    private View view;
    public Toolbar toolbar;

    private static final String TAG = "UmkdFragment";

    public static UmkdFragment newInstance() {
        return new UmkdFragment();
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "Fragment2 onCreateView");

        umkdFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.umkd_fragment, container, false);
        view = umkdFragmentBinding.getRoot();

        toolbar = view.findViewById(R.id.umkd_toolbar);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "Fragment2 onActivityCreated");
        mViewModel = ViewModelProviders.of(this).get(UmkdViewModel.class);
        umkdFragmentBinding.setUmkdViewModel(mViewModel);

        umkdFragmentBinding.umkdRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        umkdFragmentBinding.umkdRecyclerView.setHasFixedSize(true);

        umkdAdapter = new UmkdAdapter(getActivity());
        umkdFragmentBinding.umkdRecyclerView.setAdapter(umkdAdapter);

        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("УМКД");

        toolbar.setNavigationOnClickListener(v -> {
            ((HomeActivity)getActivity()).OpenToggleNavMenu();
        });
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mViewModel.getUmkd();

        mViewModel.getUmkdMutableLiveData().observe(this, umkds -> {
            umkdAdapter.setResponseUmkdList(umkds);
        });
    }
}
