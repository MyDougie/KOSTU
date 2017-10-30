package model.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

public interface SearchDAO {
	List<String> getGisuList() throws SQLException;

	List<Vector<Object>> getIdNameFromGisu(String gisu) throws SQLException;
}
