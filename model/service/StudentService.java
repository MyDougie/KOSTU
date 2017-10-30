package model.service;

import java.sql.SQLException;

import model.dto.Student;

public interface StudentService{
	/**
	 * ID 중복체크
	 * @return: true => 사용 가능 / false => 아이디가 중복이라 사용 불가능
	 */
	boolean idCheck(String id) throws SQLException;
	
	/**
	 * 회원 정보 DB에 등록
	 * @return: true => 회원가입 완료 / false => 회원가입 실패
	 */
	int insertStudent(Student student) throws SQLException;
	
	
	/**
	 * 회원 탈퇴 / 비밀번호 수정 같은 기능은 부가적인 기능이라고 생각되서
	 * 구현하고싶으면 추가시켜주세요!
	 */
	Student getId(Student dto) throws SQLException;
	
	/**
	 * 접속 유저 상태 변경
	 */
	boolean setCurrentUser(Student dto) throws Exception;
}
