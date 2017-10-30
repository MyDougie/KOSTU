package model.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

import model.dto.Board;
import model.dto.FreeBoard;
import model.dto.Reply;

/**
 * 자유게시판 <-> DB
 */
public interface FreeBoardDAO {
	/**
	 * 글 쓰기
	 * @return true: 정상적으로 작성 완료 / false: 작성 실패
	 * @param fb: 작성될 게시글 DTO
	 */
	int insertFreeBoard(Board freeBoard) throws SQLException;
	
	/**
	 * 글 삭제하기
	 * @return true: 정상적으로 삭제 완료 / false: 삭제 실패
	 * @param fbNumber: 삭제할 게시글 번호
	 */
	int deleteFreeBoard(int boardNo) throws SQLException;
	
	/**
	 * 글 수정하기
	 * @return true: 정상적으로 수정 완료 / false: 수정 실패
	 * @param fb: 수정할 게시글 DTO
	 */
	int updateFreeBoard(Board board) throws SQLException;
	
	/**
	 * 전체 게시글 검색 
	 * @return List<Board>: 게시판 UI를 JTable로 할 경우 List<Vector<Object>>를 return
	 * 						type으로 하는 경우가 편하니 그렇게 수정해주세요~~
	 */
	List<Vector<Object>> searchAllFreeBoard() throws SQLException;
	
	/**
	 * 게시글 번호 내림차순으로 10페이지 단위로 끊어서 검색(알고리즘 생각좀 해야 될듯..?)
	 * @return List<Board>: 게시판 UI를 JTable로 할 경우 List<Vector<Object>>를 return
	 * 						type으로 하는 경우가 편하니 그렇게 수정해주세요~~
	 */
	List<Vector<Object>> searchPageFreeBoard() throws SQLException;
	
	
	
	List<Vector<Object>> getUserSearch(String fieldName, String word) throws SQLException;

	String getContent(int boardNo) throws SQLException;
	
	boolean isYours(String loginId, int boardNo) throws SQLException;

	int insertFreeReply(Reply reply) throws SQLException;

	List<Vector<Object>> searchAllFreeBoardReply(int boardNo) throws SQLException;

	boolean isYourReply(String loginId, int replyNo) throws SQLException;

	int deleteFreeReply(int replyNo)throws SQLException;

	int updateFreeReply(int replyNo, String content)throws SQLException;

	List<Vector<Object>> searchAllFreeBoardByPage(int start, int end) throws SQLException;
}
