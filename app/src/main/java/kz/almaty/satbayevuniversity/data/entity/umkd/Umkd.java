package kz.almaty.satbayevuniversity.data.entity.umkd;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

@Entity
public class Umkd implements Serializable {

	@PrimaryKey(autoGenerate = true)
	private int primaryId;

	@SerializedName("courseTitle")
	private String courseTitle;

	@SerializedName("instructorName")
	private String instructorName;

	@SerializedName("instructorEmail")
	private String instructorEmail;

	@SerializedName("courseCode")
	private String courseCode;

	@SerializedName("instructorPersonalEmail")
	private String instructorPersonalEmail;

	@SerializedName("instructorId")
	private int instructorId;

	public void setCourseTitle(String courseTitle){
		this.courseTitle = courseTitle;
	}

	public String getCourseTitle(){
		return courseTitle;
	}

	public void setInstructorName(String instructorName){
		this.instructorName = instructorName;
	}

	public String getInstructorName(){
		return instructorName;
	}

	public void setInstructorEmail(String instructorEmail){
		this.instructorEmail = instructorEmail;
	}

	public String getInstructorEmail(){
		return instructorEmail;
	}

	public void setCourseCode(String courseCode){
		this.courseCode = courseCode;
	}

	public String getCourseCode(){
		return courseCode;
	}

	public void setInstructorPersonalEmail(String instructorPersonalEmail){
		this.instructorPersonalEmail = instructorPersonalEmail;
	}

	public String getInstructorPersonalEmail(){
		return instructorPersonalEmail;
	}

	public void setInstructorId(int instructorId){
		this.instructorId = instructorId;
	}

	public int getInstructorId(){
		return instructorId;
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
			"UmkdActivity{" +
			"courseTitle = '" + courseTitle + '\'' + 
			",instructorName = '" + instructorName + '\'' + 
			",instructorEmail = '" + instructorEmail + '\'' + 
			",courseCode = '" + courseCode + '\'' + 
			",instructorPersonalEmail = '" + instructorPersonalEmail + '\'' + 
			",instructorId = '" + instructorId + '\'' + 
			"}";
		}
}