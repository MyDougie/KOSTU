package model.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

import model.dto.StudyRoom;

public interface RoomService{
	/**
	 * 이름으로 예약정보 조회
	 */
	List<Vector<Object>> searchForIDRoom(String ID) throws SQLException;
	/**
	 * 날짜별로 DB로부터 사용 가능 스터디룸 조회
	 */
	List<Vector<Object>> searchForDateRoom(String date, String room) throws SQLException;

	/**
	 * 스터디룸 예약하기(insert를 통해서)
	 */
	int reserveRoom(StudyRoom sr) throws SQLException;

	/**
	 * 예약 취소하기 (delete를 통해)
	 */
	int cancleRoom(int reserveNum) throws SQLException;
	
	/**
	 * 이전의 데이터 삭제
	 */
	int deletePreviousDate(String date) throws SQLException;
}
