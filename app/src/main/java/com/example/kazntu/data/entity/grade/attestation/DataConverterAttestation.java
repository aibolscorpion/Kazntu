package com.example.kazntu.data.entity.grade.attestation;

import androidx.room.TypeConverter;

import com.example.kazntu.data.entity.academic.DatesItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

public class DataConverterAttestation implements Serializable {
    Gson gson = new Gson();

    @TypeConverter
    public String fromAttestationDetailList(AttestationDetail attestationDetails) {
        if (attestationDetails == null) {
            return (null);
        }
        Type type = new TypeToken<AttestationDetail>() {}.getType();
        String json = gson.toJson(attestationDetails, type);
        return json;
    }

    @TypeConverter
    public AttestationDetail toAttestationDetailList(String attestationDetailsString) {
        if (attestationDetailsString == null) {
            return (null);
        }
        Type type = new TypeToken<AttestationDetail>() {}.getType();
        AttestationDetail attestationDetails = gson.fromJson(attestationDetailsString, type);
        return attestationDetails;
    }
}

