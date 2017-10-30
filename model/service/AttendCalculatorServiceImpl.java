package model.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import model.dao.AttendCalculatorDAO;
import model.dao.AttendCalculatorDAOImpl;
import model.dto.Student;

public class AttendCalculatorServiceImpl implements AttendCalculatorService {
	AttendCalculatorDAO attend = new AttendCalculatorDAOImpl();
	Map<String, ArrayList<Integer>> attendInfo;

	@Override
	public int attendUpdate(Student student) throws SQLException {
		int result = 0;
		// result = attend.attendUpdate(student);
		if (result == 0)
			throw new SQLException("수정 실패");
		return result;
	}

	@Override
	public int attendInsert(Student student, int startMonth) throws SQLException {
		int result = 0;
		if (attend.duplicateMonthAttend(startMonth, student.getStudentID())) {
			if(attend.deleteMonthAttend(student.getStudentID(), startMonth) == 0) {
				throw new SQLException("삭제 오류");
			}
		}
		result = attend.attendInsert(student, startMonth);
		if (result == 0)
			throw new SQLException("삽입 실패");
		return result;
	}

	@Override
	public int foodExpensesCal(Student student) throws SQLException {
		int foodExpenses = (20 - student.getAbsent()) * 5000;
		if (foodExpenses < 0)
			throw new SQLException("식비가 0미만 오류");
		return foodExpenses;
	}

	@Override
	public int payForJobTrainingCal(Student student) throws SQLException {
		int bad = student.getBad();
		int absent = student.getAbsent();
		while (bad >= 3) {
			bad -= 3;
			absent++;
		}
		if (bad == 0 && absent == 0)
			return 200000;
		else if (absent >= 2)
			return 0;
		else {
			int result = 200000 - (100000 * absent) - (50000 * bad);
			if (result < 0) {
				return 0;
			} else {
				return result;
			}
		}
	}

	@Override
	public int monthlyPayCal(Student student) throws SQLException {
		int monthlyPay = 0;
		monthlyPay = foodExpensesCal(student) + payForJobTrainingCal(student);
		if (monthlyPay < 0)
			throw new SQLException("총 월급 0 미만으로 오류");
		return monthlyPay;
	}

	@Override
	public int setAttend(Map<String, ArrayList<Integer>> saveAttend, Student student) throws Exception {
		Iterator<String> it = saveAttend.keySet().iterator();
		int attend = 0;
		while (it.hasNext()) {
			String key = it.next();
			ArrayList<Integer> list = saveAttend.get(key);
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i) == 1) {
					attend++;
				}
			}
		}
		if (attend == 0) {
			throw new Exception("출석정보 저장 실패");
		}
		student.setAttend(attend);
		return attend;
	}

	@Override
	public int setAbsent(Map<String, ArrayList<Integer>> saveAttend, Student student) throws Exception {
		Iterator<String> it = saveAttend.keySet().iterator();
		int absent = 0;
		while (it.hasNext()) {
			String key = it.next();
			ArrayList<Integer> list = saveAttend.get(key);
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i) == 3) {
					absent++;
				}
			}
		}
		student.setAbsent(absent);
		return absent;
	}

	@Override
	public int setLate(Map<String, ArrayList<Integer>> saveAttend, Student student) throws Exception {
		Iterator<String> it = saveAttend.keySet().iterator();
		int bad = 0;
		while (it.hasNext()) {
			String key = it.next();
			ArrayList<Integer> list = saveAttend.get(key);
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i) == 2) {
					bad++;
				}
			}
		}
		student.setBad(bad);
		return bad;
	}

	@Override
	public int costInsert(String id, int startMonth, int total, int train, int food) throws SQLException {
		int result = 0;
		if (attend.duplicateMonthCost(startMonth, id)) {
			if(attend.deleteMonthCost(id, startMonth) == 0) {
				throw new SQLException("삭제 오류");
			}
		}
		result = attend.costInsert(id, startMonth, total, train, food);
		if (result == 0)
			throw new SQLException("임금삽입 실패");
		return result;
	}

	@Override
	public List<Vector<Object>> selectAllDon(String id) throws SQLException {
		List<Vector<Object>> list = attend.selectAllDon(id);
		if(list == null) {
			throw new SQLException("검색 실패!");
		}
		System.out.println(list);
		return list;
	}

	@Override
	public int deleteMonthCost(String id, int month) throws SQLException {
		int result = attend.deleteMonthCost(id, month);
		if(result == 0) {
			throw new SQLException("삭제 실패");
		}
		return result;
	}

	@Override
	public int deleteMonthAttend(String id, int month) throws SQLException {
		int result = attend.deleteMonthAttend(id, month);
		if(result == 0) {
			throw new SQLException("삭제 실패");
		}
		return result;
	}

}