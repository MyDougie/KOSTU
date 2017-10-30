package model.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import model.dto.Student;

public interface AttendCalculatorService {
	/**
	 *  정상출석 값 변경
	 */
	int setAttend(Map<String, ArrayList<Integer>> saveAttend, Student student) throws Exception;
	/**
	 *  결석 값 변경
	 */
	int setAbsent(Map<String, ArrayList<Integer>> saveAttend, Student student) throws Exception;
	/**
	 *  조퇴 지각 외출 값 변경
	 */
	int setLate(Map<String, ArrayList<Integer>> saveAttend, Student student) throws Exception;
   /**
    * 출석 수정
    *@return 1: 정상적으로 수정 완료 / 0: 수정 실패
    */
   int attendUpdate(Student student) throws SQLException;
   
   /**
    * 출석 삽입
    * @return 1: 정상적으로 출석 삽입 완료 / 0: 삽입 실패
    */
   int attendInsert(Student student, int startMonth) throws SQLException;
   /**
    * 임금삽입
    */
   int costInsert(String id, int startMonth, int total, int train, int food) throws SQLException;
   /**
    * 식비 계산
    * @return return 값이 0 미만이면 예외 발생
    */
   int foodExpensesCal(Student student)throws SQLException;
   
   /**
    * 교육비 계산
    * @return return 값이 null일경우 예외 발생
    */
   int payForJobTrainingCal(Student student)throws SQLException;
   
   /**
    * 한달 총 월급
    * @return return 값이 null일경우 예외 발생
    */
   int monthlyPayCal(Student student)throws SQLException;
   
   int deleteMonthCost(String id, int month) throws SQLException;
   int deleteMonthAttend(String id, int month) throws SQLException;
   
   List<Vector <Object>> selectAllDon(String id) throws SQLException;
   
}