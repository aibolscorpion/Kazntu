package com.example.kazntu.utils;

import com.example.kazntu.data.entity.academic.ResponseJournal;
import com.example.kazntu.data.entity.grade.transcript.SemestersItem;

public class Storage {

    private static Storage storage;

    public static Storage getInstance(){
        if( storage == null){
            storage = new Storage();
        }
        return storage;
    }

    private Storage(){
    }

    private String token;
    private String accountFullName;
    private SemestersItem semestersItem;
    private ResponseJournal responseJournal;
    private String courseCode;
    private String instructorID;
    private String courseId;
    private String fileName;
    private String username;
    private int singleCheck;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getSingleCheck() {
        return singleCheck;
    }

    public void setSingleCheck(int singleCheck) {
        this.singleCheck = singleCheck;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getInstructorID() {
        return instructorID;
    }

    public void setInstructorID(String instructorID) {
        this.instructorID = instructorID;
    }

    public ResponseJournal getResponseJournal() {
        return responseJournal;
    }

    public void setResponseJournal(ResponseJournal responseJournal) {
        this.responseJournal = responseJournal;
    }

    public SemestersItem getSemestersItem() {
        return semestersItem;
    }

    public void setSemestersItem(SemestersItem semestersItem) {
        this.semestersItem = semestersItem;
    }

    public String getAccountFullName() {
        return accountFullName;
    }

    public void setAccountFullName(String accountFullName) {
        this.accountFullName = accountFullName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
