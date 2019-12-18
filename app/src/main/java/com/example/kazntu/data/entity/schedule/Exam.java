package com.example.kazntu.data.entity.schedule;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Entity
public class Exam implements Serializable {

	@PrimaryKey(autoGenerate = true)
	private int primaryId;

	@SerializedName("endTimeId")
	private int endTimeId;

	@SerializedName("examDate")
	private String examDate;

	@SerializedName("courseCode")
	private String courseCode;

	@SerializedName("start")
	private String start;

	@SerializedName("examNumber")
	private int examNumber;

	@SerializedName("roomId")
	private int roomId;

	@SerializedName("courseTitle")
	private String courseTitle;

	@SerializedName("studentsCount")
	private int studentsCount;

	@SerializedName("cellCount")
	private int cellCount;

	@SerializedName("startTimeId")
	private int startTimeId;

	@SerializedName("examId")
	private int examId;

	@SerializedName("examinatorName")
	private String examinatorName;

	@SerializedName("end")
	private String end;

	@SerializedName("proctorNames")
	private String proctorNames;

	@SerializedName("roomTitle")
	private String roomTitle;

	public void setEndTimeId(int endTimeId){
		this.endTimeId = endTimeId;
	}

	public int getEndTimeId(){
		return endTimeId;
	}

	public void setExamDate(String examDate){
		this.examDate = examDate;
	}

	public String getExamDate(){
		return examDate;
	}

	public void setCourseCode(String courseCode){
		this.courseCode = courseCode;
	}

	public String getCourseCode(){
		return courseCode;
	}

	public void setStart(String start){
		this.start = start;
	}

	public String getStart(){
		return start;
	}

	public void setExamNumber(int examNumber){
		this.examNumber = examNumber;
	}

	public int getExamNumber(){
		return examNumber;
	}

	public void setRoomId(int roomId){
		this.roomId = roomId;
	}

	public int getRoomId(){
		return roomId;
	}

	public void setCourseTitle(String courseTitle){
		this.courseTitle = courseTitle;
	}

	public String getCourseTitle(){
		return courseTitle;
	}

	public void setStudentsCount(int studentsCount){
		this.studentsCount = studentsCount;
	}

	public int getStudentsCount(){
		return studentsCount;
	}

	public void setCellCount(int cellCount){
		this.cellCount = cellCount;
	}

	public int getCellCount(){
		return cellCount;
	}

	public void setStartTimeId(int startTimeId){
		this.startTimeId = startTimeId;
	}

	public int getStartTimeId(){
		return startTimeId;
	}

	public void setExamId(int examId){
		this.examId = examId;
	}

	public int getExamId(){
		return examId;
	}

	public void setExaminatorName(String examinatorName){
		this.examinatorName = examinatorName;
	}

	public String getExaminatorName(){
		return examinatorName;
	}

	public void setEnd(String end){
		this.end = end;
	}

	public String getEnd(){
		return end;
	}

	public void setProctorNames(String proctorNames){
		this.proctorNames = proctorNames;
	}

	public String getProctorNames(){
		return proctorNames;
	}

	public void setRoomTitle(String roomTitle){
		this.roomTitle = roomTitle;
	}

	public String getRoomTitle(){
		return roomTitle;
	}

	public int getPrimaryId() {
		return primaryId;
	}

	public void setPrimaryId(int primaryId) {
		this.primaryId = primaryId;
	}

	@Override
 	public String toString(){
		return 
			"Exam{" + 
			"endTimeId = '" + endTimeId + '\'' + 
			",examDate = '" + examDate + '\'' + 
			",courseCode = '" + courseCode + '\'' + 
			",start = '" + start + '\'' + 
			",examNumber = '" + examNumber + '\'' + 
			",roomId = '" + roomId + '\'' + 
			",courseTitle = '" + courseTitle + '\'' + 
			",studentsCount = '" + studentsCount + '\'' + 
			",cellCount = '" + cellCount + '\'' + 
			",startTimeId = '" + startTimeId + '\'' + 
			",examId = '" + examId + '\'' + 
			",examinatorName = '" + examinatorName + '\'' + 
			",end = '" + end + '\'' + 
			",proctorNames = '" + proctorNames + '\'' + 
			",roomTitle = '" + roomTitle + '\'' + 
			"}";
		}

	public String getDateFormatted(){
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.forLanguageTag("ru"));
		Date myDate = null;
		try {
			myDate = formatter.parse(examDate);

		} catch (ParseException e) {
			e.printStackTrace();
		}
		SimpleDateFormat timeFormat = new SimpleDateFormat("dd MMM", Locale.forLanguageTag("ru"));
		timeFormat.format(myDate);
		return timeFormat.format(myDate);
	}

}