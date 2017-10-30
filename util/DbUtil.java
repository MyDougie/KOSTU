package util;
/**
 * 로드, 연결, 닫기를 위한 클래스
 * */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DbUtil{
  /**
   * 로드
   * */
	static{
		try{
		 Class.forName(DbProperties.DRIVER_NAME);
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
  /**
   * 연결
   * */
	public static Connection  getConnection()throws SQLException{
		return DriverManager.getConnection( 
				DbProperties.URL, 
				DbProperties.USER_NAME, 
				DbProperties.USER_PASS);
		
	}
	
  /**
   * 닫기
   * */
   public static void  dbClose(Connection con, Statement st, ResultSet rs){
	  try{
	   if(rs!=null)rs.close();
	   if(st!=null)st.close();
	   if(con!=null)con.close();
	  }catch (SQLException e) {
		e.printStackTrace();
	}
   }
   
  /* public static void main(String[] args) {
	   try{
		   DbUtil.getConnection();
		   System.out.println(111);
	   }catch (Exception e) {
		e.printStackTrace();
	  }
  }
  */
}








