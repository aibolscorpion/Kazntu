package com.example.kazntu.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.kazntu.data.entity.AccountEntity;
import com.example.kazntu.data.entity.Language;
import com.example.kazntu.data.entity.academic.ResponseJournal;
import com.example.kazntu.data.entity.grade.attestation.Attestation;
import com.example.kazntu.data.entity.grade.transcript.SemestersItem;
import com.example.kazntu.data.entity.notification.Notification;
import com.example.kazntu.data.entity.schedule.Exam;
import com.example.kazntu.data.entity.schedule.Schedule;
import com.example.kazntu.data.entity.umkd.Umkd;

@Database(entities = {AccountEntity.class, ResponseJournal.class, Schedule.class, Exam.class, Attestation.class, SemestersItem.class, Umkd.class, Language.class, Notification.class}, version = 9, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract AccountDao accountDao();
}