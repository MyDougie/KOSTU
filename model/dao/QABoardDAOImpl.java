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

public class QABoardDAOImpl implements QABoardDAO {

	@Override
	public List<Vector<Object>> searchAllQABoard() throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Vector<Object>> list = new ArrayList<>();
		// 최신글이 위로
		String sql = "select seq, title, writer, write_date from qa_board order by seq desc";

		try {
			con = DbUtil.getConnection();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			Vector<Object> v = null;
			while (rs.next()) {
				v = new Vector<>();
				v.add(rs.getInt("seq"));
				v.add("[질문]");
				v.add(rs.getString("title"));
				v.add(rs.getString("writer"));
				v.add(rs.getString("write_date"));

				list.add(v);
			}

			return list;

		} finally {
			DbUtil.dbClose(con, ps, rs);
		}
	}

	@Override
	public List<Vector<Object>> searchPageQABoard() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int insertQABoard(Board board) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		
		
		String sql = "insert into qa_board values(seq_qa.nextVal, ?, ?, ?, ?, to_char(sysdate, 'YY-MM-DD HH:MI'))";
		
		try{
			con = DbUtil.getConnection();
			ps = con.prepareStatement(sql);
			ps.setString(1, board.getTitle());
			ps.setString(2, board.getContent());
			ps.setString(3, board.getWriter());
			ps.setString(4, board.getWriterId());
			
			return ps.executeUpdate();
			
		}finally{
			DbUtil.dbClose(con, ps, null);
		}
		
	}

	@Override
	public int deleteQABoard(int boardNo) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		String sql = "delete from qa_board where seq = ?";
		
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
	public int updateQABoard(Board board) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		String sql = "update qa_board set title = ?, content = ? where writer_id = ? and seq = ?";
		
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
	public List<Vector<Object>> getUserSearch(String fieldName, String word) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Vector<Object>> list = new ArrayList<>();
		String sql = "select seq, title, writer, write_date from qa_board where ";

		try {
			con = DbUtil.getConnection();

			if (fieldName.equals("ALL")) {
				sql += "seq like ? or title like ? or content like ? or writer like ?";
			} else if (fieldName.equals("글 번호")) {
				sql += "seq like ?";
			} else if (fieldName.equals("제목")) {
				sql += "title like ?";
			} else if (fieldName.equals("내용")) {
				sql += "content like ?";
			} else if (fieldName.equals("제목+내용")) {
				sql += "title like ? or content like ?";
			} else if (fieldName.equals("작성자")) {
				sql += "writer like ?";
			}
			ps = con.prepareStatement(sql);

			ps.setString(1, "%" + word.trim() + "%");

			if (fieldName.equals("ALL")) {
				ps.setString(2, "%" + word.trim() + "%");
				ps.setString(3, "%" + word.trim() + "%");
				ps.setString(4, "%" + word.trim() + "%");
			} else if (fieldName.equals("제목+내용")) {
				ps.setString(2, "%" + word.trim() + "%");
			}

			rs = ps.executeQuery();
			Vector<Object> v = null;
			while (rs.next()) {
				v = new Vector<>();
				v.add(rs.getInt("seq"));
				v.add("[질문]");
				v.add(rs.getString("title"));
				v.add(rs.getString("writer"));
				v.add(rs.getString("write_date"));

				list.add(v);
			}

			return list;

		} finally {
			DbUtil.dbClose(con, ps, rs);
		}
	}

	@Override
	public String getContent(int boardNo) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select content from qa_board where seq = ?";

		try {
			con = DbUtil.getConnection();
			ps = con.prepareStatement(sql);
			ps.setInt(1, boardNo);

			rs = ps.executeQuery();
			rs.next();

			return rs.getString(1);

		} finally {
			DbUtil.dbClose(con, ps, rs);
		}

	}

	@Override
	public boolean isYours(String loginId, int boardNo) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select writer_id from qa_board where seq = ?";
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

		} finally {
			DbUtil.dbClose(con, ps, rs);
		}
	}
	
	@Override
	public int insertQAReply(Reply reply) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		String sql = "insert into qa_reply values(qa_reply_seq.nextVal, ?	, ?, ?, ?, to_char(sysdate, 'YY-MM-DD HH:MI'))";
		
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
	public List<Vector<Object>> searchAllQABoardReply(int boardNo) throws SQLException {
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
		String sql = "select reply_no, writer, content, write_date from qa_reply where board_no = ? order by reply_no";
		
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
		String sql = "select writer_id from qa_reply where reply_no = ?";
		try{
			con = DbUtil.getConnection();
			ps = con.prepareStatement(sql);
			ps.setInt(1, replyNo);
			rs = ps.executeQuery();
			if(rs.next()){
				if(rs.getString("writer_id").equals(loginId))
					return true;
			}
			throw new SQLException("댓글 작성자만 가능합니다.");
			
		}finally{
			DbUtil.dbClose(con, ps, rs);
		}
	}

	@Override
	public int deleteQAReply(int replyNo) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		String sql = "delete from qa_reply where reply_no = ?";
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
	public int updateQAReply(int replyNo, String content) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		String sql = "update qa_reply set content = ? where reply_no = ?";
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
	public List<Vector<Object>> searchAllQABoardByPage(int start, int end) throws SQLException {
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
		String sql = "select * from(select rownum r, seq, title, writer, write_date from"
				+ "(select seq, title, writer, write_date from qa_board order by seq desc))"
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
				v.add("[질문]");
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
