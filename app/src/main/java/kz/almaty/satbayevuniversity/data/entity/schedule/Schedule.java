package kz.almaty.satbayevuniversity.data.entity.schedule;


import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.Comparator;

@Entity
public class Schedule implements Serializable{

	@PrimaryKey(autoGenerate = true)
	private int primaryId;

	@SerializedName("durationHours")
	private int durationHours;

	@SerializedName("endTimeId")
	private int endTimeId;

	@SerializedName("instructorName")
	private String instructorName;

	@SerializedName("courseCode")
	private String courseCode;

	@SerializedName("dayOfWeekId")
	private int dayOfWeekId;

	@SerializedName("groupNumber")
	private int groupNumber;

	@SerializedName("roomId")
	private String roomId;

	@SerializedName("courseTitle")
	private String courseTitle;

	@SerializedName("classId")
	private int classId;

	@SerializedName("startTimeId")
	private int startTimeId;

	@SerializedName("startTime")
	private String startTime;

	@SerializedName("endTime")
	private String endTime;

	@SerializedName("roomTitle")
	private String roomTitle;

	@SerializedName("classType")
	private int classType;

	@SerializedName("instructorId")
	private int instructorId;

	@Ignore
	public Schedule(String startTime, String endTime, int startTimeId) {
		this.startTime = startTime;
		this.endTime = endTime;
		this.startTimeId = startTimeId;
	}

	public Schedule() {}
	public static Schedule copy( Schedule other ) {
		Schedule newSchedule = new Schedule();
		newSchedule.durationHours = other.durationHours;
		newSchedule.endTimeId = other.endTimeId;
		newSchedule.instructorName = other.instructorName;
		newSchedule.courseCode = other.courseCode;
		newSchedule.dayOfWeekId = other.dayOfWeekId;
		newSchedule.groupNumber = other.groupNumber;
		newSchedule.roomId = other.roomId;
		newSchedule.courseTitle = other.courseTitle;
		newSchedule.classId = other.classId;
		newSchedule.startTimeId = other.startTimeId;
		newSchedule.startTime = other.startTime;
		newSchedule.endTime = other.endTime;
		newSchedule.roomTitle = other.roomTitle;
		newSchedule.classType = other.classType;
		newSchedule.instructorId = other.instructorId;
		newSchedule.primaryId = other.primaryId;
		return newSchedule;
	}
	@Ignore
	public Schedule(int primaryId, int durationHours, int endTimeId, String instructorName, String courseCode, int dayOfWeekId, int groupNumber, String roomId, String courseTitle, int classId, int startTimeId, String startTime, String endTime, String roomTitle, int classType, int instructorId) {
		this.durationHours = durationHours;
		this.endTimeId = endTimeId;
		this.instructorName = instructorName;
		this.courseCode = courseCode;
		this.dayOfWeekId = dayOfWeekId;
		this.groupNumber = groupNumber;
		this.roomId = roomId;
		this.courseTitle = courseTitle;
		this.classId = classId;
		this.startTimeId = startTimeId;
		this.startTime = startTime;
		this.endTime = endTime;
		this.roomTitle = roomTitle;
		this.classType = classType;
		this.instructorId = instructorId;
		this.primaryId = primaryId;
	}

	public int getStartTimeId() {
		return startTimeId;
	}

	public void setStartTimeId(int startTimeId) {
		this.startTimeId = startTimeId;
	}

	public void setDurationHours(int durationHours){
		this.durationHours = durationHours;
	}

	public int getDurationHours(){
		return durationHours;
	}

	public void setEndTimeId(int endTimeId){
		this.endTimeId = endTimeId;
	}

	public int getEndTimeId(){
		return endTimeId;
	}

	public void setInstructorName(String instructorName){
		this.instructorName = instructorName;
	}

	public String getInstructorName(){
		return instructorName;
	}

	public void setCourseCode(String courseCode){
		this.courseCode = courseCode;
	}

	public String getCourseCode(){
		return courseCode;
	}

	public void setDayOfWeekId(int dayOfWeekId){
		this.dayOfWeekId = dayOfWeekId;
	}

	public int getDayOfWeekId(){
		return dayOfWeekId;
	}

	public void setGroupNumber(int groupNumber){
		this.groupNumber = groupNumber;
	}


	public int getPrimaryId() {
		return primaryId;
	}

	public void setPrimaryId(int primaryId) {
		this.primaryId = primaryId;
	}

	public int getGroupNumber(){
		return groupNumber;
	}

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public void setCourseTitle(String courseTitle){
		this.courseTitle = courseTitle;
	}

	public String getCourseTitle(){
		return courseTitle;
	}

	public void setClassId(int classId){
		this.classId = classId;
	}

	public int getClassId(){
		return classId;
	}

	public void setStartTime(String startTime){
		this.startTime = startTime;
	}

	public String getStartTime(){
		return startTime;
	}

	public void setEndTime(String endTime){
		this.endTime = endTime;
	}

	public String getEndTime(){
		return endTime;
	}

	public void setRoomTitle(String roomTitle){
		this.roomTitle = roomTitle;
	}

	public String getRoomTitle(){
		return roomTitle;
	}

	public void setClassType(int classType){
		this.classType = classType;
	}

	public int getClassType(){
		return classType;
	}

	public void setInstructorId(int instructorId){
		this.instructorId = instructorId;
	}

	public int getInstructorId(){
		return instructorId;
	}

	@Override
 	public String toString(){
		return " [ " + dayOfWeekId + " ] " + startTime +" - " + endTime + " - "+courseTitle+" - "+ startTimeId;
		}

	public static Comparator<Schedule> CompareId = (s1, s2) -> {

		int Id1 = s1.getStartTimeId();
		int Id2 = s2.getStartTimeId();

		/*For ascending order*/
		return Id1-Id2;

	};
}