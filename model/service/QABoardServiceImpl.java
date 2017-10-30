package model.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

import model.dao.QABoardDAO;
import model.dao.QABoardDAOImpl;
import model.dto.Board;
import model.dto.Reply;

public class QABoardServiceImpl implements QABoardService {

	private QABoardDAO QABoardDao = new QABoardDAOImpl();
	
	@Override
	public int insertQABoard(Board board) throws SQLException {
		int re = QABoardDao.insertQABoard(board);
		if(re==0)
			throw new SQLException("글쓰기 실패");
		
		return re;
	}

	@Override
	public int deleteQABoard(int boardNo) throws SQLException {
		int re = QABoardDao.deleteQABoard(boardNo);
		if(re==0)
			throw new SQLException("삭제 실패");
		
		return re;
	}

	@Override
	public int updateQABoard(Board board) throws SQLException {
		int re = QABoardDao.updateQABoard(board);
		if(re == 0)
			throw new SQLException("수정 실패"); 
		
		return re;
	}

	@Override
	public List<Vector<Object>> searchAllQABoard() throws SQLException {
		List<Vector<Object>> list = QABoardDao.searchAllQABoard();
		if(list == null || list.size() == 0)
			throw new SQLException("해당 테이블이 비어있습니다.");
		
		return list;
	}

	@Override
	public List<Vector<Object>> searchPageQABoard() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Vector<Object>> getUserSearch(String fieldName, String word) throws SQLException {
		List<Vector<Object>> list = QABoardDao.getUserSearch(fieldName, word);
		if(list == null || list.size() == 0)
			throw new SQLException("검색결과가 없습니다.");
		
		return list;
	}

	@Override
	public String getContent(int boardNo) throws SQLException {
		String re = QABoardDao.getContent(boardNo);
		if(re == null)
			throw new SQLException("내용 불러오기 실패");
		
		return re;
	}

	@Override
	public boolean isYours(String loginId, int boardNo) throws SQLException {
		return QABoardDao.isYours(loginId, boardNo);
	}

	@Override
	public int insertQAReply(Reply reply) throws SQLException {
		int re = QABoardDao.insertQAReply(reply);
		if(re == 0)
			throw new SQLException("댓글 달기 실패"); 
		
		return re;
	}

	@Override
	public List<Vector<Object>> searchAllQABoardReply(int boardNo) throws SQLException {
		List<Vector<Object>> list = QABoardDao.searchAllQABoardReply(boardNo);
		if(list == null || list.size() == 0)
			throw new SQLException("댓글 결과가 없습니다.");
		
		return list;
	}

	@Override
	public boolean isYourReply(String loginId, int replyNo) throws SQLException {
		return QABoardDao.isYourReply(loginId, replyNo);
	}

	@Override
	public int deleteQAReply(int replyNo) throws SQLException {
		int re = QABoardDao.deleteQAReply(replyNo);
		if(re == 0)
			throw new SQLException("댓글 삭제 실패");
		return re;
	}

	@Override
	public int updateQAReply(int replyNo, String content) throws SQLException {
		int re = QABoardDao.updateQAReply(replyNo, content);
		if(re == 0)
			throw new SQLException("댓글 수정 실패");
		return re;
	}

	@Override
	public List<Vector<Object>> searchAllQABoardByPage(int start, int end) throws SQLException {
		List<Vector<Object>> list = QABoardDao.searchAllQABoardByPage(start, end);
		if(list == null || list.size() == 0)
			throw new SQLException("해당 테이블이 비어있습니다.");
		
		return list;
		
	}

}
