package kz.almaty.satbayevuniversity.ui.umkd.filefragment;

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
import androidx.appcompat.widget.Toolbar;
import kz.almaty.satbayevuniversity.R;
import kz.almaty.satbayevuniversity.databinding.FileFragmentBinding;


public class FileFragment extends Fragment {
    private FileViewModel mViewModel;
    private FileFragmentBinding fileFragmentBinding;
    private FileAdapter fileAdapter;
    private Toolbar toolbar;
    public static FileFragment newInstance() {
        return new FileFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        fileFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.file_fragment, container, false);
        View view = fileFragmentBinding.getRoot();
        toolbar = view.findViewById(R.id.toolbarFile);
        getToolbar();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(FileViewModel.class);
        fileFragmentBinding.setFile(mViewModel);
        mViewModel.getFile();
        fileFragmentBinding.fileRecyclerView.setHasFixedSize(true);
        fileFragmentBinding.fileRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        fileAdapter = new FileAdapter(getActivity());
        fileFragmentBinding.fileRecyclerView.setAdapter(fileAdapter);

        toolbar.setNavigationOnClickListener(v -> getFragmentManager().popBackStackImmediate());

        // TODO: Use the ViewModel
        updateFile();
    }

    private void updateFile() {
        mViewModel.getFileMutableLiveData().observe(this, files -> {
            fileAdapter.setFilelList(files);
            System.out.println("#####files: " + files);
        });
    }

    private void getToolbar() {
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.close_file_icon);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.files);
    }

    @Override
    public void onDestroyView() {
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
        super.onDestroyView();
    }
}
