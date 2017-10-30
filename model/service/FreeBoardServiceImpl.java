package model.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import model.dao.FreeBoardDAO;
import model.dao.FreeBoardDAOImpl;
import model.dto.Board;
import model.dto.Reply;
import util.DbUtil;

public class FreeBoardServiceImpl implements FreeBoardService {

	private FreeBoardDAO freeBoardDao = new FreeBoardDAOImpl();
	
	@Override
	public int insertFreeBoard(Board freeBoard) throws SQLException {
		int re = freeBoardDao.insertFreeBoard(freeBoard);
		if(re==0)
			throw new SQLException("글쓰기 실패");
		
		return re;
	}

	@Override
	public int deleteFreeBoard(int boardNo) throws SQLException {
		int re = freeBoardDao.deleteFreeBoard(boardNo);
		if(re==0)
			throw new SQLException("삭제 실패");
		
		return re;
	}

	@Override
	public int updateFreeBoard(Board freeBoard) throws SQLException {
		int re = freeBoardDao.updateFreeBoard(freeBoard);
		if(re == 0)
			throw new SQLException("수정 실패"); 
		
		return re;
	}

	@Override
	public List<Vector<Object>> searchAllFreeBoard() throws SQLException {
		List<Vector<Object>> list = freeBoardDao.searchAllFreeBoard();
		if(list == null || list.size() == 0)
			throw new SQLException("해당 테이블이 비어있습니다.");
		
		return list;
		
	}

	@Override
	public List<Vector<Object>> searchPageFreeBoard() throws SQLException {
		return null;
	}
	
	@Override
	public List<Vector<Object>> getUserSearch(String fieldName, String word) throws SQLException {
		List<Vector<Object>> list = freeBoardDao.getUserSearch(fieldName, word);
		if(list == null || list.size() == 0)
			throw new SQLException("검색결과가 없습니다.");
		
		return list;
	
	}

	@Override
	public String getContent(int boardNo) throws SQLException {
		String re = freeBoardDao.getContent(boardNo);
		if(re == null)
			throw new SQLException("내용 불러오기 실패");
		
		return re;
	}

	@Override
	public boolean isYours(String loginId, int boardNo) throws SQLException {
		return freeBoardDao.isYours(loginId, boardNo);
	}

	@Override
	public int insertFreeReply(Reply reply) throws SQLException {
		int re = freeBoardDao.insertFreeReply(reply);
		if(re == 0)
			throw new SQLException("댓글 달기 실패"); 
		
		return re;
	}

	@Override
	public List<Vector<Object>> searchAllFreeBoardReply(int boardNo) throws SQLException {
		List<Vector<Object>> list = freeBoardDao.searchAllFreeBoardReply(boardNo);
		if(list == null || list.size() == 0)
			throw new SQLException();
		
		return list;
	}

	@Override
	public boolean isYourReply(String loginId, int replyNo) throws SQLException {
		return freeBoardDao.isYourReply(loginId, replyNo);
	}

	@Override
	public int deleteFreeReply(int replyNo) throws SQLException {
		int re = freeBoardDao.deleteFreeReply(replyNo);
		if(re == 0)
			throw new SQLException("댓글 삭제 실패");
		
		return re;
	}

	@Override
	public int updateFreeReply(int replyNo, String content) throws SQLException {
		int re = freeBoardDao.updateFreeReply(replyNo, content);
		if(re == 0)
			throw new SQLException("댓글 수정 실패");
		
		return re;
	}

	@Override
	public List<Vector<Object>> searchAllFreeBoardByPage(int start, int end) throws SQLException {
		List<Vector<Object>> list = freeBoardDao.searchAllFreeBoardByPage(start, end);
		if(list == null || list.size() == 0)
			throw new SQLException("해당 테이블이 비어있습니다.");
		
		return list;
		
	}
	

}

