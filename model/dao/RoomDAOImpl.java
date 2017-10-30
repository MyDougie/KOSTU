package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import model.dto.StudyRoom;
import util.DbUtil;

public class RoomDAOImpl implements RoomDAO {

	@Override
	public List<Vector<Object>> searchForDateRoom(String date, String room) throws SQLException{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Vector<Object>> list = new ArrayList<>();
		String query = "select to_char(reserve_date, 'HH:MI') as reserve_date, student_id, table_num "
				+ "from reserve where substr(reserve_date, 4, 5) = ? and room_kind = ?";
		try {
			con = DbUtil.getConnection();
			ps = con.prepareStatement(query);
			ps.setString(1, date);
			ps.setString(2, room);
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

	@Override
	public int reserveRoom(StudyRoom sr) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		int result = 0;
		String query = "insert into reserve values (reserve_seq_no.nextval, ?, to_date(?, ?), ?, ?)";
		try {
			con = DbUtil.getConnection();
			ps = con.prepareStatement(query);
			ps.setString(1, sr.getStudentID());
			ps.setString(2, sr.getDate());
			ps.setString(3, "MM/DD HH:MI");
			ps.setString(4, sr.getKind());
			ps.setString(5, sr.getTable());
			result = ps.executeUpdate();
		}finally{
			DbUtil.dbClose(con, ps, null);
		}
		return result;
	}

	@Override
	public int cancleRoom(int reserveNum) throws SQLException{
		Connection con = null;
		PreparedStatement ps = null;
		int result = 0;
		String query = "delete from reserve where reserve_num = ?";
		try {
			con = DbUtil.getConnection();
			ps = con.prepareStatement(query);
			ps.setInt(1, reserveNum);
			result = ps.executeUpdate();
		}finally{
			DbUtil.dbClose(con, ps, null);
		}
		return result;
	}

	@Override
	public List<Vector<Object>> searchForIDRoom(String ID) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Vector<Object>> list = new ArrayList<>();
		String query = "select * from reserve where student_id = ?";
		try {
			con = DbUtil.getConnection();
			ps = con.prepareStatement(query);
			ps.setString(1, ID);
			rs = ps.executeQuery();
			while(rs.next()) {
				Vector<Object> vector = new Vector<>();
				vector.add(rs.getString(1));
				vector.add(rs.getString(2));
				vector.add(rs.getString(3));
				vector.add(rs.getString(4));
				vector.add(rs.getString(5));
				list.add(vector);
			}
		}finally {
			DbUtil.dbClose(con, ps, rs);
		}
		return list;
	}

	@Override
	public int deletePreviousDate(String date) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		int result = 0;

		String query = "delete from reserve where reserve_date < ?";
		try {
			con = DbUtil.getConnection();
			ps = con.prepareStatement(query);
			ps.setString(1, date);
			result = ps.executeUpdate();
		}finally {
			DbUtil.dbClose(con, ps, null);
		}
		return result;
	}

}
