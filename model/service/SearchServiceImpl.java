package model.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

import model.dao.SearchDAO;
import model.dao.SearchDAOImpl;

public class SearchServiceImpl implements SearchService {
	SearchDAO sd = new SearchDAOImpl();
	@Override
	public List<String> getGisuList() throws SQLException {
		List<String> list = sd.getGisuList();
		if(list == null || list.size() == 0) {
			throw new SQLException("검색된 기수가 없습니다.");
		}
		return list;
	}
	
	@Override
	public List<Vector<Object>> getIdNameFromGisu(String gisu) throws SQLException {
		List<Vector<Object>> list = sd.getIdNameFromGisu(gisu);
		if(list == null || list.size() == 0) {
			throw new SQLException("검색된 학생이 없습니다.");
		}
		return list;
	}

}
