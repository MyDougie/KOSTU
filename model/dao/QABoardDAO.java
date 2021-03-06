package model.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

import model.dto.Board;
import model.dto.Reply;

/**
 * 질문/답변 게시판 <-> DB
 */
public interface QABoardDAO {
	/**
	 * 글 쓰기
	 * @return true: 정상적으로 작성 완료 / false: 작성 실패
	 * @param fb: 작성될 게시글 DTO
	 */
	int insertQABoard(Board board) throws SQLException;
	
	/**
	 * 글 삭제하기
	 * @return true: 정상적으로 삭제 완료 / false: 삭제 실패
	 * @param fbNumber: 삭제할 게시글 번호
	 */
	int deleteQABoard(int boardNo) throws SQLException;
	
	/**
	 * 글 수정하기
	 * @return true: 정상적으로 수정 완료 / false: 수정 실패
	 * @param fb: 수정할 게시글 DTO
	 */
	int updateQABoard(Board board) throws SQLException;
	
	/**
	 * 전체 게시글 검색 
	 * @return List<Board>: 게시판 UI를 JTable로 할 경우 List<Vector<Object>>를 return
	 * 						type으로 하는 경우가 편하니 그렇게 수정해주세요~~
	 */
	List<Vector<Object>> searchAllQABoard() throws SQLException;
	
	/**
	 * 게시글 번호 내림차순으로 10페이지 단위로 끊어서 검색(알고리즘 생각좀 해야 될듯..?)
	 * @return List<Board>: 게시판 UI를 JTable로 할 경우 List<Vector<Object>>를 return
	 * 						type으로 하는 경우가 편하니 그렇게 수정해주세요~~
	 */
	List<Vector<Object>> searchPageQABoard() throws SQLException;
	
	/**
	 * 제목에 포함된 키워드로 검색
	 * @return Board: 역시 게시판 UI를 JTable로 할 경우 Vector<Object>로!! 밑에 메소드도 역시!
	 */
	List<Vector<Object>> getUserSearch(String fieldName, String word) throws SQLException;

	String getContent(int boardNo) throws SQLException;
	
	boolean isYours(String loginId, int boardNo) throws SQLException;

	int insertQAReply(Reply reply) throws SQLException;

	List<Vector<Object>> searchAllQABoardReply(int boardNo) throws SQLException;

	boolean isYourReply(String loginId, int replyNo) throws SQLException;

	int deleteQAReply(int replyNo) throws SQLException;

	int updateQAReply(int replyNo, String content) throws SQLException;

	List<Vector<Object>> searchAllQABoardByPage(int start, int end)throws SQLException;
}
