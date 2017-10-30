package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.dto.Student;
import util.DbUtil;

public class StudentDAOImpl implements StudentDAO {
	
	public Student getId(String id) throws SQLException{
		System.out.println("DAO :"+ id);
		Connection con = null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		Student sd = null;
		String sql = "select * from student where LOWER(id)=LOWER(?)";
		try {
			con= DbUtil.getConnection();
			ps= con.prepareStatement(sql);
			ps.setString(1,id);
			rs=ps.executeQuery();
			while(rs.next()){
				sd=new Student(Integer.parseInt(rs.getString(4)), rs.getString(1), rs.getString(3),
						rs.getString(2), rs.getString(6), rs.getInt(7), rs.getString(8), rs.getString(5),
						rs.getString(9));
			}
			
		} finally {
			DbUtil.dbClose(con, ps, rs);
		}
		return sd;
	}
	
	public boolean idCheck(String id)throws SQLException{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select id from student where lower(id) = lower(?)";
		
		try {
			con=DbUtil.getConnection();
			ps=con.prepareStatement(sql);
			ps.setString(1, id);
			rs = ps.executeQuery();
			if(rs.next()){
				return false;
			}
			return true;
			
		} finally {
			DbUtil.dbClose(con, ps, rs);
		}
		
		
	}
	
	public int insertStudent(Student student) throws SQLException{
		System.out.println("111");
		Connection con = null;
		PreparedStatement ps = null;
		int resultNum=0;
		String sql = "insert into student values(?,?,?,?,?,?,?,?,?)";
		
		try {
			System.out.println("2222");
			con=DbUtil.getConnection();
			ps = con.prepareStatement(sql);
			ps.setString(1, student.getStudentID());
			ps.setString(2, student.getStudentName());
			ps.setString(3, student.getStudentPW());
			ps.setString(4, ""+student.getKostaNumber());
			ps.setString(5, student.getStudentPhone());
			ps.setString(6, student.getSex());
			ps.setInt(7, student.getAge());
			ps.setString(8, student.getJumin());
			ps.setString(9, student.getImgLoc());
			System.out.println("33333333333");
			
			resultNum = ps.executeUpdate();
			System.out.println("결과 값은 "+resultNum);
			
		} finally {
			DbUtil.dbClose(con, ps, null);
		}
		return resultNum;
		
	}
	

}
