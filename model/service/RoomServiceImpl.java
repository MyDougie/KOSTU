package model.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

import model.dao.RoomDAO;
import model.dao.RoomDAOImpl;
import model.dto.StudyRoom;

public class RoomServiceImpl implements RoomService {
	RoomDAO roomDAO = new RoomDAOImpl();
	@Override
	public List<Vector<Object>> searchForDateRoom(String date, String room) throws SQLException{
		List<Vector<Object>> list = roomDAO.searchForDateRoom(date, room);
		if((list == null) || (list.size() == 0)) {
			throw new SQLException("해당 날짜는 모두 비어있습니다. 예약하세요!");
		}
		return list;
	}

	@Override
	public int reserveRoom(StudyRoom sr) throws SQLException{
		int result = roomDAO.reserveRoom(sr);
		if(result == 0) {
			throw new SQLException("예약에 실패하였습니다.");
		}
		return result;
	}

	@Override
	public int cancleRoom(int reserveNum) throws SQLException{
		int result = roomDAO.cancleRoom(reserveNum);
		if(result == 0) {
			throw new SQLException("예약취소에 실패하였습니다.");
		}
		return result;
	}

	@Override
	public List<Vector<Object>> searchForIDRoom(String ID) throws SQLException {
		List<Vector<Object>> list = roomDAO.searchForIDRoom(ID);
		if(list == null || list.size() == 0) {
			throw new SQLException(ID + " ID로 예약된 정보가 없습니다.");
		}
		return list;
	}

	@Override
	public int deletePreviousDate(String date) throws SQLException {
		int result = roomDAO.deletePreviousDate(date);
		if(result == 0) {
			throw new SQLException("예전 데이터 삭제 실패!");
		}
		return result;
	}

}
