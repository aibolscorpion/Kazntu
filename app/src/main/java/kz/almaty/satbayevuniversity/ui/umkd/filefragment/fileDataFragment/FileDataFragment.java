package kz.almaty.satbayevuniversity.ui.umkd.filefragment.fileDataFragment;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import kz.almaty.satbayevuniversity.R;
import kz.almaty.satbayevuniversity.data.entity.umkd.Course;
import kz.almaty.satbayevuniversity.databinding.FileDataFragmentBinding;

public class FileDataFragment extends Fragment {

    private FileDataViewModel mViewModel;
    private FileDataFragmentBinding fileDataFragmentBinding;
    private FileDataAdapter fileAdapter;
    private List<Course> courseList = new ArrayList<>();
    private Toolbar toolbar;

    public static FileDataFragment newInstance() {
        return new FileDataFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        fileDataFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.file_data_fragment, container, false);
        View view = fileDataFragmentBinding.getRoot();
        toolbar = view.findViewById(R.id.toolbarFileData);
        getToolbar();

        return view;
    }

    @Override
    public void onResume(){
        super.onResume();

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener((view, i, keyEvent) -> {
            if(i == KeyEvent.KEYCODE_BACK && keyEvent.getAction() == KeyEvent.ACTION_UP){
                if (getFragmentManager().getBackStackEntryCount() > 0) {
                    getFragmentManager().popBackStackImmediate();
                }else{
                    return false;
                }
                return true;
            }
            return false;
        });
    }
    private void getToolbar() {
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.files);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(FileDataViewModel.class);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            courseList = (List<Course>) bundle.getSerializable("FileDataFragment");
        }

        fileDataFragmentBinding.recyclerFileDataFragment.setHasFixedSize(true);
        fileDataFragmentBinding.recyclerFileDataFragment.setLayoutManager(new LinearLayoutManager(getActivity()));

        toolbar.setNavigationOnClickListener(v -> getFragmentManager().popBackStackImmediate());

        fileAdapter = new FileDataAdapter(getActivity());
        fileDataFragmentBinding.recyclerFileDataFragment.setAdapter(fileAdapter);
        System.out.println("#####courseList: " + courseList);
        fileAdapter.setFileDataList(courseList);
    }
}
