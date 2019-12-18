package com.example.kazntu.data.network;

import com.example.kazntu.data.entity.AccountEntity;
import com.example.kazntu.data.entity.academic.ResponseJournal;
import com.example.kazntu.data.entity.grade.attestation.Attestation;
import com.example.kazntu.data.entity.grade.transcript.ResponseTranscript;
import com.example.kazntu.data.entity.notification.Notification;
import com.example.kazntu.data.entity.schedule.Exam;
import com.example.kazntu.data.entity.schedule.Schedule;
import com.example.kazntu.data.entity.umkd.File;
import com.example.kazntu.data.entity.umkd.Umkd;

import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface MyApi {
    @POST("token")
    Call<AccountEntity> onLogin(@Body RequestBody requestBody);

    @GET("api/User/Photo")
    Call<ResponseBody> updatePhoto();

    @GET("api/Journal")
    Call<List<ResponseJournal>> updateJournal();

    @GET("api/Schedule")
    Call<List<Schedule>> updateSchedule();

    @GET("api/Attestation")
    Call<List<Attestation>> updateAttestation();

    @GET("api/Transcript")
    Call<ResponseTranscript> updateTranscript();

    @GET("api/ExamSchedule")
    Call<List<Exam>> updateExam();

    @GET("api/News/Short")
    Call<List<Notification>> updateNotification();

    @GET("api/Instructor")
    Call<List<Umkd>> updateInstructor();

    @GET("api/File/Course")
    Call<List<File>> updateFileCourse(@Query("courseCode") String courseCode, @Query("instructorID") String instructorID);

    @GET("/api/File/Download")
    Call<ResponseBody> downloadFileCourse(@Query("fileID") String courseCode);

    @POST("/api/Complaint/Save")
    Call<ResponseBody> sendComplaint(@Body Map<String, String> body);
}