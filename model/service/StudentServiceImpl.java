package model.service;

import java.sql.SQLException;

import model.dao.StudentDAO;
import model.dao.StudentDAOImpl;
import model.dto.Student;
import view.MainView;

public class StudentServiceImpl implements StudentService {
	StudentDAO sd = new StudentDAOImpl();
	
	public Student getId(Student dto) throws SQLException{
		System.out.println("아이디 :"+dto.getStudentID());
		Student sdt = sd.getId(dto.getStudentID());
		System.out.println(sdt);
		if(sdt==null){
			throw new SQLException("회원 데이터가 없습니다. (아이디 확인)");
		}
		if(dto.getStudentID().equals(sdt.getStudentID())){
			System.out.println("아이디 일치");
			if(dto.getStudentPW().equals(sdt.getStudentPW())){
				System.out.println("비밀번호 일치");
				return sdt;
			}else{
				throw new SQLException("비밀번호가 맞지않습니다.");
			}
		}else{
			throw new SQLException("아이디가 일치 하지않습니다.");
		}
	}
	
	public int insertStudent(Student student) throws SQLException{
		System.out.println("서비스의 : "+student.getStudentName());
		int resultNum = sd.insertStudent(student);
		System.out.println("Service :"+resultNum);
		if(resultNum==0){
			throw new SQLException("업데이트 되지 않았습니다.");
		}else{
			return resultNum;
		}
	}
	
	public boolean idCheck(String id) throws SQLException{
		// false면 중복 , true면 안중복
		boolean result = sd.idCheck(id);
		if(result){
			return true; //투루로 가면 사용 가능
		}else{
			throw new SQLException("아이디 중복 입니다.");
		}
	}

	@Override
	public boolean setCurrentUser(Student dto) throws Exception {
		if(dto == null) {
			throw new Exception("유저정보 불러오기 오류!");
		}
		MainView.user.setStudentID(dto.getStudentID());
		MainView.user.setStudentName(dto.getStudentName());
		MainView.user.setStudentPW(dto.getStudentPW());
		MainView.user.setStudentPhone(dto.getStudentPhone());
		MainView.user.setAge(dto.getAge());
		MainView.user.setJumin(dto.getJumin());
		MainView.user.setKostaNumber(dto.getKostaNumber());
		MainView.user.setImgLoc(dto.getImgLoc());
		return true;
	}
	
}


