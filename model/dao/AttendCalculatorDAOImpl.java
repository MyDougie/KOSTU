package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import model.dto.Student;
import util.DbUtil;

public class AttendCalculatorDAOImpl implements AttendCalculatorDAO {

	@Override
	public int attendInsert(Student student, int startMonth) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		String sql = "insert into student_attend values(?,?,?,?,?)";
		int result = 0;
		try {
			con = DbUtil.getConnection();
			ps = con.prepareStatement(sql);
			ps.setString(1, student.getStudentID());
			ps.setInt(2, startMonth);
			ps.setInt(3, student.getAttend());
			ps.setInt(4, student.getBad());
			ps.setInt(5, student.getAbsent());
			result = ps.executeUpdate();
		} finally {
			DbUtil.dbClose(con, ps, null);
		}

		return result;
	}

	@Override
	public int costInsert(String id, int startMonth, int total, int train, int food) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		String sql = "insert into student_cost values(?,?,?,?,?)";
		int result = 0;
		try {
			con = DbUtil.getConnection();
			ps = con.prepareStatement(sql);
			ps.setString(1, id);
			ps.setInt(2, startMonth);
			ps.setInt(3, total);
			ps.setInt(4, train);
			ps.setInt(5, food);
			result = ps.executeUpdate();
		} finally {
			DbUtil.dbClose(con, ps, null);
		}

		return result;
	}

	@Override
	public boolean duplicateMonthCost(int month, String id) throws SQLException {
		boolean duplicate = false;
		Connection con = null;
		PreparedStatement ps = null;
		String sql = "select month from student_cost where month = ? and id = ?";
		ResultSet result = null;
		try {
			con = DbUtil.getConnection();
			ps = con.prepareStatement(sql);
			ps.setInt(1, month);
			ps.setString(2, id);
			result = ps.executeQuery();
			if (result.next()) {
				duplicate = true;
			}
		} finally {
			DbUtil.dbClose(con, ps, null);
		}

		return duplicate;
	}

	@Override
	public boolean duplicateMonthAttend(int month, String id) throws SQLException {
		boolean duplicate = false;
		Connection con = null;
		PreparedStatement ps = null;
		String sql = "select month from student_attend where month = ? and id = ?";
		ResultSet result = null;
		try {
			con = DbUtil.getConnection();
			ps = con.prepareStatement(sql);
			ps.setInt(1, month);
			ps.setString(2,  id);
			result = ps.executeQuery();
			if (result.next()) {
				duplicate = true;
			}
		} finally {
			DbUtil.dbClose(con, ps, null);
		}

		return duplicate;
	}

	@Override
	public int deleteMonthCost(String id, int month) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		String sql = "delete from student_cost where id = ? and month = ?";
		int result = 0;
		try {
			con = DbUtil.getConnection();
			ps = con.prepareStatement(sql);
			ps.setString(1, id);
			ps.setInt(2, month);
			result = ps.executeUpdate();
		} finally {
			DbUtil.dbClose(con, ps, null);
		}

		return result;
	}

	@Override
	public int deleteMonthAttend(String id, int month) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		String sql = "delete from student_attend where id = ? and month = ?";
		int result = 0;
		try {
			con = DbUtil.getConnection();
			ps = con.prepareStatement(sql);
			ps.setString(1, id);
			ps.setInt(2, month);
			result = ps.executeUpdate();
		} finally {
			DbUtil.dbClose(con, ps, null);
		}

		return result;
	}

	@Override
	public List<Vector<Object>> selectAllDon(String id) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		String sql = "select sa.month, sa.attend, sa.bad, "
				+ "sa.absent, sc.train, sc.food, sc.total "
				+ "from student_attend sa left join student_cost sc "
				+ "on sa.id = sc.id where sa.id = ? and sa.month = sc.month";
		ResultSet rs = null;
		List<Vector<Object>> list = null;
		try {
			list = new ArrayList<>();
			con = DbUtil.getConnection();
			ps = con.prepareStatement(sql);
			ps.setString(1, id);
			rs = ps.executeQuery();
			while (rs.next()) {
				Vector<Object> vector = new Vector<>();
				vector.add(rs.getInt(1));
				vector.add(rs.getInt(2));
				vector.add(rs.getInt(3));
				vector.add(rs.getInt(4));
				vector.add(rs.getInt(5));
				vector.add(rs.getInt(6));
				vector.add(rs.getInt(7));
				list.add(vector);
			}
		} finally {
			DbUtil.dbClose(con, ps, null);
		}

		return list;
	}

}