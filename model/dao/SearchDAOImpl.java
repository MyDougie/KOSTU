package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import util.DbUtil;

public class SearchDAOImpl implements SearchDAO {

	@Override
	public List<String> getGisuList() throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs =null;
		List<String> list = new ArrayList<>();
		String query = "select distinct gisu from student";
		try {
			con = DbUtil.getConnection();
			ps = con.prepareStatement(query);
			rs = ps.executeQuery();
			while(rs.next()) {
				list.add(rs.getString(1));
			}
		}finally {
			DbUtil.dbClose(con, ps, rs);
		}
		return list;
	}

	@Override
	public List<Vector<Object>> getIdNameFromGisu(String gisu) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs =null;
		List<Vector<Object>> list = new ArrayList<>();
		String query = "select id, name, image from student where gisu = ?";
		try {
			con = DbUtil.getConnection();
			ps = con.prepareStatement(query);
			ps.setString(1, gisu);
			rs = ps.executeQuery();
			while(rs.next()) {
				Vector<Object> vector = new Vector<>();
				vector.add(rs.getString(1));
				vector.add(rs.getString(2));
				vector.add(rs.getString(3));
				list.add(vector);
			}
		}finally {
			DbUtil.dbClose(con, ps, rs);
		}
		return list;
	}

}
