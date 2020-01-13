package kz.almaty.satbayevuniversity.ui.grade.attestation;

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
import kz.almaty.satbayevuniversity.databinding.GradeFragmentBinding;

public class GradeFragment extends Fragment {

    private GradeViewModel mViewModel;

    private GradeFragmentBinding gradeFragmentBinding;
    private AttestationAdapter attestationAdapter;
    public static GradeFragment newInstance() {
        return new GradeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        gradeFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.grade_fragment, container, false);
        View view = gradeFragmentBinding.getRoot();
        gradeFragmentBinding.emptyImage.setVisibility(View.INVISIBLE);
        gradeFragmentBinding.emptyTextView.setVisibility(View.INVISIBLE);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(GradeViewModel.class);

        gradeFragmentBinding.setGrade(mViewModel);

        Bundle bundle = this.getArguments();
        if(bundle !=null){
            if(bundle.getBoolean(getString(R.string.only_server))){
                mViewModel.getAttestation(true);
            }
        }else{
            mViewModel.getAttestation(false);
        }
        gradeFragmentBinding.gradeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        gradeFragmentBinding.gradeRecyclerView.setHasFixedSize(true);

        attestationAdapter = new AttestationAdapter();
        gradeFragmentBinding.gradeRecyclerView.setAdapter(attestationAdapter);

        mViewModel.getAttestationLiveDate().observe(this, attestations -> {
            attestationAdapter.setAttestationList(attestations);
            System.out.println("###### "+attestations.size());
         });
        mViewModel.getHandleTimeout().observe(this, aBoolean -> {
            if (aBoolean) {
                Toast.makeText(getActivity(), R.string.internetConnection, Toast.LENGTH_SHORT).show();
            }
        });
    }

}
