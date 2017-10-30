package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import model.dto.Board;
import model.dto.FreeBoard;
import model.dto.Reply;
import util.DbUtil;

public class FreeBoardDAOImpl implements FreeBoardDAO {


	@Override
	public int insertFreeBoard(Board freeBoard) throws SQLException {

		Connection con = null;
		PreparedStatement ps = null;
		
		
		String sql = "insert into free_board values(seq_free.nextVal, ?, ?, ?, ?, to_char(sysdate, 'YY-MM-DD HH:MI'), ?)";
		
		try{
			con = DbUtil.getConnection();
			ps = con.prepareStatement(sql);
			
			FreeBoard fb = null;
			if(freeBoard instanceof FreeBoard){
				fb = (FreeBoard) freeBoard;
			}
			
			int category = fb.getCategory();
			
			ps.setString(1, freeBoard.getTitle());
			ps.setString(2, freeBoard.getContent());
			ps.setString(3, freeBoard.getWriter());
			ps.setString(4, freeBoard.getWriterId());
			ps.setInt(5, category);
			
			return ps.executeUpdate();
			
		}finally{
			DbUtil.dbClose(con, ps, null);
		}
		
	}

	@Override
	public int deleteFreeBoard(int boardNo) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		String sql = "delete from free_board where seq = ?";
		
		try{
			con = DbUtil.getConnection();
			ps = con.prepareStatement(sql);
			ps.setInt(1, boardNo);
			
			return ps.executeUpdate();
			
		} finally{
			DbUtil.dbClose(con, ps, null);
		}
	}

	@Override
	public int updateFreeBoard(Board board) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		String sql = "update free_board set title = ?, content = ? where writer_id = ? and seq = ?";
		
		try{
			con = DbUtil.getConnection();
			ps = con.prepareStatement(sql);
			ps.setString(1, board.getTitle());
			ps.setString(2, board.getContent());
			ps.setString(3, board.getWriterId());
			ps.setInt(4, board.getNumber());
			
			return ps.executeUpdate();
			
		} finally{
			DbUtil.dbClose(con, ps, null);
		}
		
	}		

	@Override
	public List<Vector<Object>> searchAllFreeBoard() throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Vector<Object>> list = new ArrayList<>();
		/**
		 * category - 0 : 공지글
		 * 			  1 : 일반글
		 * 공지글 위, 일반글 아래
		 * 날짜역순(최신작성날짜순)
		 * */
		String sql = "select seq, category, title, writer, write_date from free_board order by category, seq desc";
		
		try{
			con = DbUtil.getConnection();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			Vector<Object> v = null;
			while(rs.next()){
				v = new Vector<>();
				v.add(rs.getInt("seq"));
				int category = rs.getInt("category");
				if(category == 0)
					v.add("[공지]");
				else if(category == 1)
					v.add("[일반]");
				
				v.add(rs.getString("title"));
				v.add(rs.getString("writer"));
				v.add(rs.getString("write_date"));
				
				list.add(v);
			}
			
			return list;
			
		}finally{
			DbUtil.dbClose(con, ps, rs);
		}
	}

	@Override
	public List<Vector<Object>> searchPageFreeBoard() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<Vector<Object>> getUserSearch(String fieldName, String word) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Vector<Object>> list = new ArrayList<>();
		String sql = "select seq, category, title, writer, write_date from free_board where ";
		
		try{
			con = DbUtil.getConnection();
			
			if(fieldName.equals("ALL")){
				sql += "seq like ? or title like ? or content like ? or writer like ?";
			}else if(fieldName.equals("글 번호")){
				sql += "seq like ?";
			}else if(fieldName.equals("제목")){
				sql += "title like ?";
			}else if(fieldName.equals("내용")){
				sql += "content like ?";
			}else if(fieldName.equals("제목+내용")){
				sql += "title like ? or content like ?";
			}else if(fieldName.equals("작성자")){
				sql += "writer like ?";
			}
			
			ps = con.prepareStatement(sql);
			
			ps.setString(1, "%"+word.trim()+"%");
			
			if(fieldName.equals("ALL")){
				ps.setString(2, "%"+word.trim()+"%");
				ps.setString(3, "%"+word.trim()+"%");
				ps.setString(4, "%"+word.trim()+"%");
			}else if(fieldName.equals("제목+내용")){
				ps.setString(2, "%"+word.trim()+"%");
			}
			
			rs = ps.executeQuery();
			Vector<Object> v = null;
			while(rs.next()){
				v = new Vector<>();
				v.add(rs.getInt("seq"));
				int category = rs.getInt("category");
				if(category == 0)
					v.add("[공지]");
				else if(category == 1)
					v.add("[일반]");
				v.add(rs.getString("title"));
				v.add(rs.getString("writer"));
				v.add(rs.getString("write_date"));
				
				list.add(v);
			}
			
			return list;
			
		}finally{
			DbUtil.dbClose(con, ps, rs);
		}
	}

	@Override
	public String getContent(int boardNo)throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select content from free_board where seq = ?";
		
		try{
			con = DbUtil.getConnection();
			ps = con.prepareStatement(sql);
			ps.setInt(1, boardNo);
			
			rs = ps.executeQuery();
			rs.next();
			
			return rs.getString(1);
			
			
		}finally{
			DbUtil.dbClose(con, ps, rs);
		}
		
	}
	
	public boolean isYours(String loginId, int boardNo) throws SQLException{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select writer_id from free_board where seq = ?";
		try{
			con = DbUtil.getConnection();
			ps = con.prepareStatement(sql);
			ps.setInt(1, boardNo);
			rs = ps.executeQuery();
			
			if(rs.next()){
				if(loginId.equals(rs.getString("writer_id")))
						return true;
			}
			throw new SQLException("게시물 작성자만 가능합니다.");
			
		}finally{
			DbUtil.dbClose(con, ps, rs);
		}
		
	}

	@Override
	public int insertFreeReply(Reply reply) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		String sql = "insert into free_reply values(free_reply_seq.nextVal, ?, ?, ?, ?, to_char(sysdate, 'YY-MM-DD HH:MI'))";
		
		try{
			con = DbUtil.getConnection();
			ps = con.prepareStatement(sql);
			
			ps.setInt(1, reply.getBoardNo());
			ps.setString(2, reply.getWriter());
			ps.setString(3, reply.getWriterId());
			ps.setString(4, reply.getContent());
			
			return ps.executeUpdate();
			
		}finally{
			DbUtil.dbClose(con, ps, null);
		}
		
		
	}

	@Override
	public List<Vector<Object>> searchAllFreeBoardReply(int boardNo) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Vector<Object>> list = new ArrayList<>();
		/**
		 * category - 0 : 공지글
		 * 			  1 : 일반글
		 * 공지글 위, 일반글 아래
		 * 날짜역순(최신작성날짜순)
		 * */
		String sql = "select reply_no, writer, content, write_date from free_reply where board_no = ? order by reply_no";
		
		try{
			con = DbUtil.getConnection();
			ps = con.prepareStatement(sql);
			ps.setInt(1, boardNo);
			rs = ps.executeQuery();
			Vector<Object> v = null;
			while(rs.next()){
				v = new Vector<>();
				v.add(rs.getInt("reply_no"));
				v.add(rs.getString("writer"));
				v.add(rs.getString("content"));
				v.add(rs.getString("write_date"));
				
				list.add(v);
			}
			
			return list;
			
		}finally{
			DbUtil.dbClose(con, ps, rs);
		}
	}

	@Override
	public boolean isYourReply(String loginId, int replyNo) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select writer_id from free_reply where reply_no = ?";
		try{
			con = DbUtil.getConnection();
			ps = con.prepareStatement(sql);
			ps.setInt(1, replyNo);
			rs = ps.executeQuery();
			if(rs.next()){
				if(rs.getString("writer_id").equals(loginId)){
					return true;
				}
			}
			throw new SQLException("댓글 작성자만 가능합니다.");
			
		}finally{
			DbUtil.dbClose(con, ps, rs);
		}
	}

	@Override
	public int deleteFreeReply(int replyNo) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		String sql = "delete from free_reply where reply_no = ?";
		try{
			con = DbUtil.getConnection();
			ps = con.prepareStatement(sql);
			ps.setInt(1, replyNo);
			return ps.executeUpdate();
			
		}finally{
			DbUtil.dbClose(con, ps, null);
		}
	}

	@Override
	public int updateFreeReply(int replyNo, String content) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		String sql = "update free_reply set content = ? where reply_no = ?";
		try{
			con = DbUtil.getConnection();
			ps = con.prepareStatement(sql);
			ps.setString(1, content);
			ps.setInt(2, replyNo);
			return ps.executeUpdate();
			
		}finally{
			DbUtil.dbClose(con, ps, null);
		}
	}

	@Override
	public List<Vector<Object>> searchAllFreeBoardByPage(int start, int end) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Vector<Object>> list = new ArrayList<>();
		/**
		 * category - 0 : 공지글
		 * 			  1 : 일반글
		 * 공지글 위, 일반글 아래
		 * 날짜역순(최신작성날짜순)
		 * */
		String sql = "select * from(select rownum r, seq, category, title, writer, write_date from"
				+ "(select seq, category, title, writer, write_date from free_board order by category, seq desc))"
				+ "where r between ? and ?";
		
		try{
			con = DbUtil.getConnection();
			ps = con.prepareStatement(sql);
			ps.setInt(1, start);
			ps.setInt(2, end);
			rs = ps.executeQuery();
			Vector<Object> v = null;
			while(rs.next()){
				v = new Vector<>();
				v.add(rs.getInt("seq"));
				int category = rs.getInt("category");
				if(category == 0)
					v.add("[공지]");
				else if(category == 1)
					v.add("[일반]");
				
				v.add(rs.getString("title"));
				v.add(rs.getString("writer"));
				v.add(rs.getString("write_date"));
				
				list.add(v);
			}
			
			return list;
			
		}finally{
			DbUtil.dbClose(con, ps, rs);
		}
	}
	
	

}
