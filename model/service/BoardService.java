package model.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

import model.dto.Board;

public interface BoardService {
	/**
	 * 글 쓰기
	 * 
	 * @return true: 정상적으로 작성 완료 / false: 작성 실패
	 * @param fb: 작성될 게시글 DTO
	 * DAO로 부터 온 return값이 false일 경우 Exception 발생시킴!
	 */
	boolean writeBoard(Board board) throws SQLException;

	/**
	 * 글 삭제하기
	 * 
	 * @return true: 정상적으로 삭제 완료 / false: 삭제 실패
	 * @param fbNumber: 삭제할 게시글 번호
	 * DAO로 부터 온 return값이 false일 경우 Exception 발생시킴!
	 */
	boolean deleteBoard(int boardNo) throws SQLException;

	/**
	 * 글 수정하기
	 * 
	 * @return true: 정상적으로 수정 완료 / false: 수정 실패
	 * @param fb: 수정할 게시글 DTO
	 * DAO로 부터 온 return값이 false일 경우 Exception 발생시킴!
	 */
	boolean updateBoard(Board board) throws SQLException;

	/**
	 * 전체 게시글 검색
	 * 
	 * @return List<Board>: 게시판 UI를 JTable로 할 경우 List<Vector<Object>>를 return
	 *         type으로 하는 경우가 편하니 그렇게 수정해주세요~~
	 *DAO로 부터 온 return값이 null일 경우 Exception 발생시킴!
	 */
	List<Vector<Object>> searchAllBoard(int index) throws SQLException;

	/**
	 * 게시글 번호 내림차순으로 10페이지 단위로 끊어서 검색(알고리즘 생각좀 해야 될듯..?)
	 * 
	 * @return List<Board>: 게시판 UI를 JTable로 할 경우 List<Vector<Object>>를 return
	 *         type으로 하는 경우가 편하니 그렇게 수정해주세요~~
	 * DAO로 부터 온 return값이 null일 경우 Exception 발생시킴!
	 */
	List<Vector<Object>> searchPageBoard() throws SQLException;

	/**
	 * 제목에 포함된 키워드로 검색
	 * 
	 * @return Board: 역시 게시판 UI를 JTable로 할 경우 Vector<Object>로!! 밑에 메소드도 역시!
	 * DAO로 부터 온 return값이 null일 경우 Exception 발생시킴!
	 */
	Board searchSubjectBoard(String title) throws SQLException;

	/**
	 * 작성자 이름에 포함된 키워드로 검색
	 * DAO로 부터 온 return값이 null일 경우 Exception 발생시킴!
	 */
	Board searchWriterBoard(String writer) throws SQLException;

	/**
	 * 내용에 포함된 키워드로 검색
	 * DAO로 부터 온 return값이 null일 경우 Exception 발생시킴!
	 */
	Board searchContentBoard(String content) throws SQLException;

	/**
	 * 제목 or 내용에 포함된 키워드로 검색
	 * DAO로 부터 온 return값이 null일 경우 Exception 발생시킴!
	 */
	Board searchEntireBoard(String keyword) throws SQLException;

	/**
	 * 글 번호로 검색 (중복 체크 메소드 / 글을 눌렀을때 UI로 뿌려주는 메소드로 활용하세요)
	 * DAO로 부터 온 return값이 null일 경우 Exception 발생시킴!
	 */
	Board searchNumberBoard(int number) throws SQLException;
	
	List<Vector<Object>> getUserSearch(String fieldName, String word) throws SQLException;
}
