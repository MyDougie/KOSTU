package model.dto;

import java.util.ArrayList;
import java.util.Map;

public class Student {
	private int kostaNumber;
	private String studentID;
	private String studentPW;
	private String studentName;
	private String sex;
	private int age;
	private String jumin;
	private String studentPhone;
	private String imgLoc;
	private int bad;
	private int absent;
	private int attend;
	private Map<String, ArrayList<Integer>> detailAttend;

	public Student(int kostaNumber, String studentID, String studentPW, String studentName, String sex, int age,
			String jumin, String studentPhone, String imgLoc) {
		this(studentID, studentPW, studentName, studentPhone, kostaNumber);
		this.sex = sex;
		this.age = age;
		this.jumin = jumin;
		this.imgLoc = imgLoc;
	}
	
	public Student(String studentId, String studentPW, String studentName, String studentPhone, int kostaNumber) {
		this(studentId, studentPW);
		this.studentName = studentName;
		this.studentPhone = studentPhone;
		this.kostaNumber = kostaNumber;
	}
	public Student(String studentID, String studentPW) {
		this.studentID = studentID;
		this.studentPW = studentPW;
	}

	public int getKostaNumber() {
		return kostaNumber;
	}

	public String getStudentID() {
		return studentID;
	}

	public String getStudentPW() {
		return studentPW;
	}

	public String getStudentName() {
		return studentName;
	}

	public String getSex() {
		return sex;
	}

	public int getAge() {
		return age;
	}

	public String getJumin() {
		return jumin;
	}

	public String getStudentPhone() {
		return studentPhone;
	}

	public String getImgLoc() {
		return imgLoc;
	}

	public void setKostaNumber(int kostaNumber) {
		this.kostaNumber = kostaNumber;
	}

	public void setStudentID(String studentID) {
		this.studentID = studentID;
	}

	public void setStudentPW(String studentPW) {
		this.studentPW = studentPW;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public void setJumin(String jumin) {
		this.jumin = jumin;
	}

	public void setStudentPhone(String studentPhone) {
		this.studentPhone = studentPhone;
	}

	public void setImgLoc(String imgLoc) {
		this.imgLoc = imgLoc;
	}

	public int getBad() {
		return bad;
	}

	public void setBad(int bad) {
		this.bad = bad;
	}

	public int getAbsent() {
		return absent;
	}

	public void setAbsent(int absent) {
		this.absent = absent;
	}

	public int getAttend() {
		return attend;
	}

	public void setAttend(int attend) {
		this.attend = attend;
	}

	public Map<String, ArrayList<Integer>> getDetailAttend() {
		return detailAttend;
	}

	public void setDetailAttend(Map<String, ArrayList<Integer>> detailAttend) {
		this.detailAttend = detailAttend;
	}

	@Override
	public String toString() {
		return kostaNumber + "|" + studentID + "|" + studentPW + "|" + studentName + "|" + sex + "|" + age + "|" + jumin
				+ "|" + studentPhone + "|" + imgLoc + "|";
	}

	// int kostaNumber, String studentID , String studentPW , String
	// studentName, String sex, String jumin, int studentPhone

}
