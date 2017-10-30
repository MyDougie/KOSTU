package model.dto;

public class StudyRoom {
	private String date;
	private String studentID;
	private String kind;
	private String table;
	public StudyRoom(String date, String studentID, String kind, String table) {
		super();
		this.date = date;
		this.studentID = studentID;
		this.kind = kind;
		this.table = table;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getStudentID() {
		return studentID;
	}
	public void setStudentName(String studentID) {
		this.studentID = studentID;
	}
	public String getKind() {
		return kind;
	}
	public void setKind(String kind) {
		this.kind = kind;
	}
	public String getTable() {
		return table;
	}
	public void setTable(String table) {
		this.table = table;
	}
	
	
	
	
}
