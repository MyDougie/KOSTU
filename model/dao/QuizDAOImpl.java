package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.dto.Quiz;
import util.DbUtil;

public class QuizDAOImpl implements QuizDAO {

   @Override
   public int quizUpdate(Quiz qs) throws SQLException {
      Connection con = null;
      PreparedStatement ps = null;
      String sql = "update quizdb answer = ? where no = ?";
      int result = 0;
      try {
         con = DbUtil.getConnection();
         ps = con.prepareStatement(sql);         
         ps.setString(1, qs.getAnswer());
         ps.setInt(2, qs.getNo());
         result = ps.executeUpdate();
      } finally {
         DbUtil.dbClose(con, ps, null);
      }
      return result;
   }

   @Override
   public int quizDelete(int no) throws SQLException {
      Connection con = null;
      PreparedStatement ps = null;
      String sql = "delete from quizdb where no = ?";
      int result = 0;
      try {
         con = DbUtil.getConnection();
         ps = con.prepareStatement(sql);
         
         result = ps.executeUpdate();
      } finally {
         DbUtil.dbClose(con, ps, null);
      }
      
      return result;
   }

   @Override
   public int quizInsert(Quiz qs) throws SQLException {
      Connection con = null;
      PreparedStatement ps = null;
      String sql = "insert into quizdb(no, answer) values(?,?)";
      int result = 0;
      try {
         con = DbUtil.getConnection();
         ps = con.prepareStatement(sql);
         
         result = ps.executeUpdate();
      } finally {
         DbUtil.dbClose(con, ps, null);
      }
      return result;
   }

   @Override
   public List<Quiz> quizAll() throws SQLException {
      Connection con = null;
      PreparedStatement ps = null;
      ResultSet rs = null;
      String sql = "select *from quizdb";
      Quiz qs = null;
      List<Quiz> list = new ArrayList<>();
      try {
         con = DbUtil.getConnection();
         ps = con.prepareStatement(sql);
         rs = ps.executeQuery();
         while(rs.next()){
            qs = new Quiz(rs.getInt(1), rs.getString(2));
            list.add(qs);
         }
      } finally {
         DbUtil.dbClose(con, ps, rs);
      }
      return list;
   }
   
   @Override
   public String quizConfirm(int no) throws SQLException{
      Connection con = null;
      PreparedStatement ps = null;
      ResultSet rs = null;
      String sql = "select answer from quizdb where no = ?";
      String answer = null;
      try {
         con = DbUtil.getConnection();
         ps = con.prepareStatement(sql);
         ps.setInt(1, no);
         rs = ps.executeQuery();
         
         while(rs.next()){
            answer = rs.getString(1);
         }
      } finally {
         DbUtil.dbClose(con, ps, rs);
            
      }
      return answer;
   }

}