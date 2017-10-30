package model.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

public interface SearchService {
	/**
	 * 기수를 받아오는 메소드
	 */
	List<String> getGisuList() throws SQLException;

	List<Vector<Object>> getIdNameFromGisu(String gisu) throws SQLException;
}
