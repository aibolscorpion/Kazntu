package com.example.kazntu.data.entity.academic;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.List;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

@Entity
public class ResponseJournal implements Serializable {
	@PrimaryKey(autoGenerate = true)
	private int primaryId;

	@SerializedName("courseId")
	private int courseId;

	@SerializedName("courseTitle")
	private String courseTitle;

	@SerializedName("instructorId")
	private int instructorId;

	@SerializedName("instructorFullName")
	private String instructorFullName;

	@SerializedName("missedPercent")
	private int missedPercent;

	@SerializedName("missedPercentFailed")
	private boolean missedPercentFailed;

	@TypeConverters(DataConverterAcademic.class)
	@SerializedName("dates")
	private List<DatesItem> dates;


	public void setCourseId(int courseId){
		this.courseId = courseId;
	}

	public int getCourseId(){
		return courseId;
	}

	public void setCourseTitle(String courseTitle){
		this.courseTitle = courseTitle;
	}

	public String getCourseTitle(){
		return courseTitle;
	}

	public void setInstructorId(int instructorId){
		this.instructorId = instructorId;
	}

	public int getInstructorId(){
		return instructorId;
	}

	public void setInstructorFullName(String instructorFullName){
		this.instructorFullName = instructorFullName;
	}

	public String getInstructorFullName(){
		return instructorFullName;
	}

	public void setMissedPercent(int missedPercent){
		this.missedPercent = missedPercent;
	}

	public int getMissedPercent(){
		return missedPercent;
	}

	public void setMissedPercentFailed(boolean missedPercentFailed){
		this.missedPercentFailed = missedPercentFailed;
	}

	public boolean isMissedPercentFailed(){
		return missedPercentFailed;
	}

	public void setDates(List<DatesItem> dates){
		this.dates = dates;
	}

	public List<DatesItem> getDates(){
		return dates;
	}

	public int getPrimaryId() {
		return primaryId;
	}

	public void setPrimaryId(int primaryId) {
		this.primaryId = primaryId;
	}

	public String getMissedOverride(){
		int missedOverride = 0;
		for(DatesItem datesItem : getDates()){
			if (!datesItem.isAttended()){
				missedOverride++;
			}
		}
		return String.valueOf(missedOverride);
	}

	public Double getGrade(){
		Double totalScore = 0.0;
		for(DatesItem datesItem : getDates()){
			if (datesItem.getGrade() != null){
				totalScore += datesItem.getGrade();
			}
		}
		return totalScore;
	}

}