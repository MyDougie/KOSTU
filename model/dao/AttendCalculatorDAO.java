package model.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Vector;
import model.dto.Student;

/**
 * 출석으로 인한 한달 비용계산 <-> DB 
 */

public interface AttendCalculatorDAO {
	
   /**
    * 출석 추가(오늘)
    * insert into AttendCalculator(earlyLeave, goOut, absence, lateness, month_Day) values(?,?,?,?,?);
    */
   int attendInsert(Student student, int startMonth)throws SQLException;
   
   int costInsert(String id, int startMonth, int total, int train, int food) throws SQLException;
   
   boolean duplicateMonthCost(int month, String id) throws SQLException;
   boolean duplicateMonthAttend(int month, String id) throws SQLException;
   
   int deleteMonthCost(String id, int month) throws SQLException;
   int deleteMonthAttend(String id, int month) throws SQLException;
   
   List<Vector <Object>> selectAllDon(String id) throws SQLException;

}