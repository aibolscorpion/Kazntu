package kz.almaty.satbayevuniversity.data.entity.grade.transcript;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class CoursesItem implements Serializable {

	@SerializedName("code")
	private String code;

	@SerializedName("title")
	private String title;

	@SerializedName("semester")
	private int semester;

	@SerializedName("credits")
	private int credits;

	@SerializedName("ects")
	private int ects;

	@SerializedName("letter")
	private String letter;

	@SerializedName("score")
	private Double score;

	@SerializedName("exam")
	private Double exam;

	@SerializedName("gpaGrade")
	private Double gpaGrade;

	@SerializedName("linkCode")
	private String linkCode;

	@SerializedName("isReplaced")
	private boolean isReplaced;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getSemester() {
		return semester;
	}

	public void setSemester(int semester) {
		this.semester = semester;
	}

	public int getCredits() {
		return credits;
	}

	public void setCredits(int credits) {
		this.credits = credits;
	}

	public int getEcts() {
		return ects;
	}

	public void setEcts(int ects) {
		this.ects = ects;
	}

	public String getLetter() {
		return letter;
	}

	public void setLetter(String letter) {
		this.letter = letter;
	}

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public Double getExam() {
		return exam;
	}

	public void setExam(Double exam) {
		this.exam = exam;
	}

	public Double getGpaGrade() {
		return gpaGrade;
	}

	public void setGpaGrade(Double gpaGrade) {
		this.gpaGrade = gpaGrade;
	}

	public String getLinkCode() {
		return linkCode;
	}

	public void setLinkCode(String linkCode) {
		this.linkCode = linkCode;
	}

	public boolean isReplaced() {
		return isReplaced;
	}

	public void setReplaced(boolean replaced) {
		isReplaced = replaced;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof CoursesItem) {
			CoursesItem coursesItem = (CoursesItem) obj;
			if(this.getTitle()!=null) {
				if (!this.getTitle().equals(coursesItem.getTitle()))
					return false;
			}
			if(this.getCode()!=null) {
				if (!this.getCode().equals(coursesItem.getCode()))
					return false;
			}
			if(this.getLetter()!=null) {
				if (!this.getLetter().equals(coursesItem.getLetter()))
					return false;
			}
			if(this.getLinkCode()!=null) {
				if (!this.getLinkCode().equals(coursesItem.getLinkCode()))
					return false;
			}
			if(this.getScore()!=null) {
				if (!this.getScore().equals(coursesItem.getScore()))
					return false;
			}
			if(this.getExam()!=null) {
				if (!this.getExam().equals(coursesItem.getExam()))
					return false;
			}
			if(this.getGpaGrade()!=null) {
				if (!this.getGpaGrade().equals(coursesItem.getGpaGrade()))
					return false;
			}
			if(this.getSemester() != coursesItem.getSemester())
				return false;
			if(this.getCredits() != coursesItem.getCredits())
				return false;
			if(this.getEcts() != coursesItem.getEcts())
				return false;
			if(this.isReplaced() != coursesItem.isReplaced())
				return false;
			return true;
		}
		return false;
	}
}