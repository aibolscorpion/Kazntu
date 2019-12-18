package com.example.kazntu.ui.grade.attestation;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kazntu.R;
import com.example.kazntu.data.entity.grade.attestation.Attestation;
import com.example.kazntu.databinding.AttestationItemBinding;

import java.util.ArrayList;
import java.util.List;

public class AttestationAdapter extends RecyclerView.Adapter<AttestationAdapter.ViewHolder> {

    private List<Attestation> attestationList = new ArrayList<>();

    public AttestationAdapter() {
    }

    @NonNull
    @Override
    public AttestationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AttestationItemBinding attestationItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.attestation_item, parent, false);
        return new AttestationAdapter.ViewHolder(attestationItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull AttestationAdapter.ViewHolder holder, int position) {
        Attestation currentAttestation = attestationList.get(position);
        holder.attestationItemBinding.setAttestation(currentAttestation);
    }

    @Override
    public int getItemCount() {
        return (attestationList == null) ? 0 : attestationList.size();
    }

    public void setAttestationList(List<Attestation> attestationList) {
        this.attestationList = attestationList;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private AttestationItemBinding attestationItemBinding;
        public ViewHolder(@NonNull AttestationItemBinding attestationItemBinding) {
            super(attestationItemBinding.getRoot());
            this.attestationItemBinding = attestationItemBinding;
        }

    }
}
