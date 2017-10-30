package controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import model.dto.Board;
import model.dto.Quiz;
import model.dto.Reply;
import model.dto.Student;
import model.dto.StudyRoom;
import model.service.AttendCalculatorService;
import model.service.AttendCalculatorServiceImpl;
import model.service.FileService;
import model.service.FileServiceImpl;
import model.service.FreeBoardService;
import model.service.FreeBoardServiceImpl;
import model.service.QABoardService;
import model.service.QABoardServiceImpl;
import model.service.QuizService;
import model.service.QuizServiceImpl;
import model.service.RoomService;
import model.service.RoomServiceImpl;
import model.service.SearchService;
import model.service.SearchServiceImpl;
import model.service.StudentService;
import model.service.StudentServiceImpl;
import view.FailView;
import view.SuccessView;

public class KOSTAController {
	private static AttendCalculatorService acs = new AttendCalculatorServiceImpl();
	private static RoomService rs = new RoomServiceImpl();
	private static StudentService ss = new StudentServiceImpl();
	private static FreeBoardService fs = new FreeBoardServiceImpl();
	private static QABoardService qs = new QABoardServiceImpl();
	private static SearchService searchService = new SearchServiceImpl();
	private static FileService fileService = new FileServiceImpl();
	private static QuizService quizService = new QuizServiceImpl();

	/**
	 * 로그인 및 회원가입 부분
	 */
	public static Student getId(Student dto) {
		Student sd = null;
		try {
			sd = ss.getId(dto);
			SuccessView.successMessage("로그인 성공");

		} catch (SQLException e) {
			FailView.errorMessage(e.getMessage());
		}
		return sd;
	}

	public static int insertStudent(Student student) {
		int resultNum = 0;
		System.out.println(student.getStudentName());
		try {
			resultNum = ss.insertStudent(student);
			if (resultNum > 0) {
				SuccessView.successMessage("가입 완료");
				return resultNum;
			}
		} catch (Exception e) {
			FailView.errorMessage(e.getMessage());
		}
		return resultNum;
	}

	public static boolean idCheck(String id) {
		boolean result = false;
		try {
			result = ss.idCheck(id);
			SuccessView.successMessage("사용할 수 있는 아이디입니다.");
			return result;
		} catch (Exception e) {
			FailView.errorMessage(e.getMessage());
			return result;
		}

	}

	public static boolean setCurrentUser(Student dto) {
		boolean result = false;
		try {
			result = ss.setCurrentUser(dto);
		} catch (Exception e) {
			FailView.errorMessage(e.getMessage());
		}
		return result;
	}

	/**
	 * 게시판컨트롤러
	 */

	public static boolean isYours(String loginId, int boardNo, int index) {
		boolean re = false;
		try {
			if (index == 0)
				re = fs.isYours(loginId, boardNo);
			else if (index == 1)
				re = qs.isYours(loginId, boardNo);
		} catch (SQLException e) {
			FailView.errorMessage(e.getMessage());
		}
		return re;
	}

	public static List<Vector<Object>> searchAllFreeBoard() {
		List<Vector<Object>> list = null;
		try {
			list = fs.searchAllFreeBoard();
		} catch (SQLException e) {
			FailView.errorMessage(e.getMessage());
		}
		return list;
	}

	public static List<Vector<Object>> searchAllQABoard() {
		List<Vector<Object>> list = null;
		try {
			list = qs.searchAllQABoard();
		} catch (SQLException e) {
			FailView.errorMessage(e.getMessage());
		}
		return list;
	}

	public static List<Vector<Object>> getUserSearchFree(String fieldName, String word) {
		List<Vector<Object>> list = null;
		try {
			list = fs.getUserSearch(fieldName, word);
		} catch (SQLException e) {
			FailView.errorMessage(e.getMessage());
		}
		return list;
	}

	public static List<Vector<Object>> getUserSearchQA(String fieldName, String word) {
		List<Vector<Object>> list = null;
		try {
			list = qs.getUserSearch(fieldName, word);
		} catch (SQLException e) {
			FailView.errorMessage(e.getMessage());
		}
		return list;
	}

	public static int insertBoard(Board board, int index) {
		int re = 0;
		try {
			if (index == 0) {
				re = fs.insertFreeBoard(board);
			} else if (index == 1) {
				re = qs.insertQABoard(board);
			}
			SuccessView.successMessage("게시물 등록 완료");
		} catch (SQLException e) {
			FailView.errorMessage(e.getMessage());
		}

		return re;
	}

	public static String getContent(int boardNo, int index) {
		String re = null;
		try {
			if (index == 0) {
				re = fs.getContent(boardNo);
			} else if (index == 1) {
				re = qs.getContent(boardNo);
			}
		} catch (SQLException e) {
			FailView.errorMessage(e.getMessage());
		}

		return re;

	}

	public static int updateFreeBoard(Board board) {
		int re = 0;
		try {
			re = fs.updateFreeBoard(board);
		} catch (SQLException e) {
			FailView.errorMessage(e.getMessage());
		}
		return re;
	}

	public static int updateQABoard(Board board) {
		int re = 0;
		try {
			re = qs.updateQABoard(board);
		} catch (SQLException e) {
			FailView.errorMessage(e.getMessage());
		}
		return re;
	}

	public static int deleteBoard(int boardNo, int index) {
		int re = 0;
		try {
			if (index == 0) {
				re = fs.deleteFreeBoard(boardNo);
			} else if (index == 1)
				re = qs.deleteQABoard(boardNo);
		} catch (SQLException e) {
			FailView.errorMessage(e.getMessage());
		}
		return re;

	}

	public static int insertComment(Reply reply, int index) {
		int re = 0;
		try {
			if (index == 0) {
				re = fs.insertFreeReply(reply);
			} else if (index == 1)
				re = qs.insertQAReply(reply);
			SuccessView.successMessage("댓글 작성 성공!");
		} catch (SQLException e) {
			FailView.errorMessage("댓글은 23자 내로 달아주세요");
		}
		return re;

	}

	public static List<Vector<Object>> searchAllReply(int index, int boardNo) {

		List<Vector<Object>> list = null;
		try {
			if (index == 0)
				list = fs.searchAllFreeBoardReply(boardNo);
			else if (index == 1)
				list = qs.searchAllQABoardReply(boardNo);

		} catch (SQLException e) {
		}
		return list;

	}

	public static boolean isYourReply(String loginId, int replyNo, int index) {
		boolean re = false;
		try {
			if (index == 0)
				re = fs.isYourReply(loginId, replyNo);
			else if (index == 1)
				re = qs.isYourReply(loginId, replyNo);
		} catch (SQLException e) {
			FailView.errorMessage(e.getMessage());
		}
		return re;
	}

	public static int deleteReply(int replyNo, int index) {
		int re = 0;
		try {
			if (index == 0)
				re = fs.deleteFreeReply(replyNo);
			else if (index == 1)
				re = qs.deleteQAReply(replyNo);
		} catch (SQLException e) {
			FailView.errorMessage(e.getMessage());
		}
		return re;

	}

	public static int updateReply(int replyNo, String content, int index) {
		int re = 0;
		try {
			if (index == 0)
				re = fs.updateFreeReply(replyNo, content);
			else if (index == 1)
				re = qs.updateQAReply(replyNo, content);
		} catch (SQLException e) {
			FailView.errorMessage(e.getMessage());
		}
		return re;

	}

	public static List<Vector<Object>> searchBoardByPage(int index, int start, int end) {

		List<Vector<Object>> list = null;
		try {
			if (index == 0)
				list = fs.searchAllFreeBoardByPage(start, end);
			else if (index == 1)
				list = qs.searchAllQABoardByPage(start, end);

		} catch (SQLException e) {
		}
		return list;

	}

	/**
	 * 스터디룸 검색 컨트롤
	 */
	public static List<Vector<Object>> searchForDateRoom(String date, String room) {
		List<Vector<Object>> list = null;
		try {
			list = rs.searchForDateRoom(date, room);

		} catch (Exception e) {
			FailView.errorMessage(e.getMessage());
		}
		return list;
	}

	public static List<Vector<Object>> searchForIDRoom(String ID) {
		List<Vector<Object>> list = null;
		try {
			list = rs.searchForIDRoom(ID);
		} catch (Exception e) {
			FailView.errorMessage(e.getMessage());
		}
		return list;
	}

	/**
	 * 스터디룸 예약 컨트롤
	 */
	public static int reserveRoom(StudyRoom studyRoom) {
		int result = 0;
		try {
			result = rs.reserveRoom(studyRoom);
			SuccessView.successMessage("예약되었습니다.");
		} catch (Exception e) {
			FailView.errorMessage(e.getMessage());
		}
		return result;
	}

	public static int cancleRoom(int reserveNum) {
		int result = 0;
		try {
			result = rs.cancleRoom(reserveNum);
			SuccessView.successMessage("예약이 취소되었습니다.");
		} catch (Exception e) {
			FailView.errorMessage(e.getMessage());
		}
		return result;
	}

	public static void deletePreviousDate(String date) {

		try {
			rs.deletePreviousDate(date);
			// SuccessView.successMessage("삭제완료");
		} catch (Exception e) {
			// FailView.errorMessage(e.getMessage());
		}

	}

	/**
	 * 지출계산컨트롤
	 */
	public static int setAttend(Map<String, ArrayList<Integer>> saveAttend, Student student) {
		int result = 0;
		try {
			result = acs.setAttend(saveAttend, student);
		} catch (Exception e) {
			// FailView.errorMessage(e.getMessage());
		}
		return result;

	}

	public static int setAbsent(Map<String, ArrayList<Integer>> saveAttend, Student student) {
		int result = 0;
		try {
			result = acs.setAbsent(saveAttend, student);
		} catch (Exception e) {
			// FailView.errorMessage(e.getMessage());
		}
		return result;
	}

	public static int setLate(Map<String, ArrayList<Integer>> saveAttend, Student student) {
		int result = 0;
		try {
			result = acs.setLate(saveAttend, student);
		} catch (Exception e) {
			// FailView.errorMessage(e.getMessage());
		}
		return result;
	}

	public static int attendInsert(Student student, int startMonth) {
		int result = 0;
		try {
			result = acs.attendInsert(student, startMonth);
		} catch (Exception e) {
			e.printStackTrace();
			FailView.errorMessage(e.getMessage());
		}
		return result;

	}

	public static int foodExpensesCal(Student user) {
		int result = 0;
		try {
			result = acs.foodExpensesCal(user);
		} catch (Exception e) {
			FailView.errorMessage(e.getMessage());
		}
		return result;
	}

	public static int payForJobTrainingCal(Student user) {
		int result = 0;
		try {
			result = acs.payForJobTrainingCal(user);
		} catch (Exception e) {
			FailView.errorMessage(e.getMessage());
		}
		return result;
	}

	public static int costInsert(String studentID, int startMonth, int totalCal, int trainCal, int foodCal) {
		int result = 0;
		try {
			result = acs.costInsert(studentID, startMonth, totalCal, trainCal, foodCal);
		} catch (Exception e) {
			FailView.errorMessage(e.getMessage());
		}
		return result;
	}

	public static List<Vector<Object>> selectAllDon(String studentID) {
		List<Vector<Object>> list = null;
		try {
			list = acs.selectAllDon(studentID);
		} catch (Exception e) {
			FailView.errorMessage(e.getMessage());
		}
		return list;
	}

	public static int deleteMonthCost(String id, int month) {
		int result = 0;
		try {
			result = acs.deleteMonthCost(id, month);
		} catch (Exception e) {
			FailView.errorMessage(e.getMessage());
		}
		return result;
	}

	public static int deleteMonthAttend(String id, int month) {
		int result = 0;
		try {
			result = acs.deleteMonthAttend(id, month);
		} catch (Exception e) {
			e.printStackTrace();
			FailView.errorMessage(e.getMessage());
		}
		return result;
	}

	/**
	 * 학생 검색 컨트롤러
	 */
	public static List<String> getGisuList() {
		List<String> list = null;
		try {
			list = searchService.getGisuList();
		} catch (Exception e) {
			FailView.errorMessage(e.getMessage());
		}
		return list;
	}

	public static List<Vector<Object>> getIdNameFromGisu(String gisu) {
		List<Vector<Object>> list = null;
		try {
			list = searchService.getIdNameFromGisu(gisu);
		} catch (Exception e) {
			FailView.errorMessage(e.getMessage());
		}
		return list;
	}

	/**
	 * 파일 관리 부분
	 */
	public static boolean transFile(String gisu, String id, String abFile) {
		boolean result = false;
		try {

			fileService.transFile(gisu, id, abFile);
		} catch (Exception e) {
			e.printStackTrace();
			FailView.errorMessage(e.getMessage());
		}
		return result;
	}

	public static boolean saveFile(String gisu, String id, String imgLoc) {
		boolean result = false;
		try {
			fileService.saveFile(gisu, id, imgLoc);
		} catch (Exception e) {
			e.printStackTrace();
			FailView.errorMessage(e.getMessage());
		}
		return result;
	}

	/**
	 * 퀴즈부분
	 */

	public int quizUpdate(Quiz qs) throws SQLException {
		int result = 0;
		try {
			result = quizService.quizUpdate(qs);
			SuccessView.successMessage("수정 성공");
		} catch (SQLException e) {
			FailView.errorMessage(e.getMessage());
		}
		return result;
	}

	public int quizDelete(int no) throws SQLException {
		int result = 0;
		try {
			result = quizService.quizDelete(no);
			SuccessView.successMessage("삭제 성공");
		} catch (SQLException e) {
			FailView.errorMessage(e.getMessage());
		}
		return result;
	}

	public int quizInsert(Quiz qs) throws SQLException {
		int result = 0;
		try {
			result = quizService.quizInsert(qs);
			SuccessView.successMessage("삽입 성공");
		} catch (SQLException e) {
			FailView.errorMessage(e.getMessage());
		}
		return result;
	}

	public List<Quiz> quizAll() throws SQLException {
		List<Quiz> quiz = new ArrayList<>();
		try {
			quiz = quizService.quizAll();
			SuccessView.successMessage("전체 가져오기 성공");
		} catch (SQLException e) {
			FailView.errorMessage(e.getMessage());
		}
		return quiz;
	}

	public String quizConfirm(int no) throws SQLException {
		String confirm = null;
		try {
			confirm = quizService.quizConfirm(no);
			// SuccessView.successMessage("정답 확인");
		} catch (Exception e) {
			FailView.errorMessage(e.getMessage());
		}
		return confirm;
	}

	public List<Object> quizSelect() throws Exception {
		String quiz = null;
		List<Object> list = null;
		try {
			list = new ArrayList<>();
			list = quizService.quizSelect();
			// quiz = (String) list.get(1);
			// SuccessView.successMessage("문제 불러오기성공");
		} catch (Exception e) {
			FailView.errorMessage(e.getMessage());
		}
		return list;
	}
}
