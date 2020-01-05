package kz.almaty.satbayevuniversity.data.entity.grade.attestation;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class AttestationDetail implements Serializable {

	@SerializedName("grade2Failed")
	private boolean grade2Failed;

	@SerializedName("totalGrade")
	private Double totalGrade;

	@SerializedName("grade1Failed")
	private boolean grade1Failed;

	@SerializedName("missedPercentFailed")
	private boolean missedPercentFailed;

	@SerializedName("totalGradeFailed")
	private boolean totalGradeFailed;

	@SerializedName("missedPercent")
	private int missedPercent;

	@SerializedName("grade1")
	private Double grade1;

	@SerializedName("grade2")
	private Double grade2;

	@SerializedName("examGrade")
	private Double examGrade;

	@SerializedName("examFailed")
	private boolean examFailed;

	@SerializedName("notPassed")
	private boolean notPassed;

	public boolean isGrade2Failed() {
		return grade2Failed;
	}

	public void setGrade2Failed(boolean grade2Failed) {
		this.grade2Failed = grade2Failed;
	}

	public Double getTotalGrade() {
		return totalGrade;
	}

	public void setTotalGrade(Double totalGrade) {
		this.totalGrade = totalGrade;
	}

	public boolean isGrade1Failed() {
		return grade1Failed;
	}

	public void setGrade1Failed(boolean grade1Failed) {
		this.grade1Failed = grade1Failed;
	}

	public boolean isMissedPercentFailed() {
		return missedPercentFailed;
	}

	public void setMissedPercentFailed(boolean missedPercentFailed) {
		this.missedPercentFailed = missedPercentFailed;
	}

	public boolean isTotalGradeFailed() {
		return totalGradeFailed;
	}

	public void setTotalGradeFailed(boolean totalGradeFailed) {
		this.totalGradeFailed = totalGradeFailed;
	}

	public int getMissedPercent() {
		return missedPercent;
	}

	public void setMissedPercent(int missedPercent) {
		this.missedPercent = missedPercent;
	}

	public Double getGrade1() {
		return grade1;
	}

	public void setGrade1(Double grade1) {
		this.grade1 = grade1;
	}

	public Double getGrade2() {
		return grade2;
	}

	public void setGrade2(Double grade2) {
		this.grade2 = grade2;
	}

	public Double getExamGrade() {
		return examGrade;
	}

	public void setExamGrade(Double examGrade) {
		this.examGrade = examGrade;
	}

	public boolean isExamFailed() {
		return examFailed;
	}

	public void setExamFailed(boolean examFailed) {
		this.examFailed = examFailed;
	}

	public boolean isNotPassed() {
		return notPassed;
	}

	public void setNotPassed(boolean notPassed) {
		this.notPassed = notPassed;
	}

	@Override
	public String toString() {
		return "AttestationDetail{" +
				"grade2Failed=" + grade2Failed +
				", totalGrade=" + totalGrade +
				", grade1Failed=" + grade1Failed +
				", missedPercentFailed=" + missedPercentFailed +
				", totalGradeFailed=" + totalGradeFailed +
				", missedPercent=" + missedPercent +
				", grade1=" + grade1 +
				", grade2=" + grade2 +
				", examGrade=" + examGrade +
				", examFailed=" + examFailed +
				", notPassed=" + notPassed +
				'}';
	}
}