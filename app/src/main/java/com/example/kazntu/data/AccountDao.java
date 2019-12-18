package com.example.kazntu.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.kazntu.data.entity.AccountEntity;
import com.example.kazntu.data.entity.Language;
import com.example.kazntu.data.entity.academic.ResponseJournal;
import com.example.kazntu.data.entity.grade.attestation.Attestation;
import com.example.kazntu.data.entity.grade.transcript.SemestersItem;
import com.example.kazntu.data.entity.notification.Notification;
import com.example.kazntu.data.entity.schedule.Exam;
import com.example.kazntu.data.entity.schedule.Schedule;
import com.example.kazntu.data.entity.umkd.Umkd;

import java.util.List;

@Dao
public interface AccountDao {

    //AccountEntity
    @Query("SELECT * FROM accountentity")
    LiveData<AccountEntity> getAll();

    @Insert
    void insert(AccountEntity employee);

    @Query("DELETE FROM AccountEntity")
    void delete();

    // JournalEntity
    @Query("SELECT * FROM ResponseJournal")
    List<ResponseJournal> getResponseJournal();

    @Update
    void updateJournal(List<ResponseJournal> responseJournal);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertResponseJournal(List<ResponseJournal> responseJournal);

    @Query("DELETE FROM ResponseJournal")
    void deleteResponseJournal();

    //Schedule
    @Query("SELECT * FROM Schedule")
    List<Schedule> getSchedule();

    @Insert
    void insertSchedule(List<Schedule> scheduleList);

    @Query("DELETE FROM Schedule")
    void deleteSchedule();

    @Update
    void updateSchedule(List<Schedule> scheduleList);

    //Exams
    @Query("SELECT * FROM Exam")
    List<Exam> getExam();

    @Insert
    void insertExam(List<Exam> examList);

    @Query("DELETE FROM Exam")
    void deleteExam();

    @Update
    void updateExam(List<Exam> examList);

    //Attestation
    @Query("SELECT * FROM Attestation")
    List<Attestation> getAttestation();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAttestation(List<Attestation> attestationList);

    @Query("DELETE FROM Attestation")
    void deleteAttestation();

    @Update
    void updateAttestation(List<Attestation> attestationList);

    //Transcript
    @Query("SELECT * FROM SemestersItem")
    List<SemestersItem> getSemestersItem();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSemestersItem(List<SemestersItem> semestersItemList);

    @Query("DELETE FROM SemestersItem")
    void deleteSemestersItem();

    @Update
    void updateSemestersItem(List<SemestersItem> semestersItemList);

    //Umkd
    @Query("SELECT * FROM Umkd")
    List<Umkd> getUmkd();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUmkd(List<Umkd> umkdList);

    @Query("DELETE FROM Umkd")
    void deleteUmkd();

    //Language
    @Query("SELECT * FROM Language")
     Language getLanguage();

    @Insert
    void insertLanguage(Language language);

    @Query("DELETE FROM Language")
    void deleteLanguage();

    //News
    @Query("SELECT * FROM Notification")
    List<Notification> getNews();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNews(List<Notification> notifications);

    @Query("DELETE FROM Notification")
    void deleteNotification();

    @Update
    void updateNews(List<Notification> notifications);
}
