package model.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import model.dao.QuizDAO;
import model.dao.QuizDAOImpl;
import model.dto.Quiz;
import model.dto.QuizProperties;
import util.PropertiesLoad;

public class QuizServiceImpl implements QuizService {
   QuizDAO qd = new QuizDAOImpl();

   @Override
   public int quizUpdate(Quiz qs) throws SQLException {
      int result = 0;
      result = qd.quizUpdate(qs);
      if(result == 0){
         throw new SQLException("수정 실패");
      }
      return result;
   }

   @Override
   public int quizDelete(int no) throws SQLException {
      int result = 0;
      result = qd.quizDelete(no);
      if(result == 0){
         throw new SQLException("삭제 실패");
      }
      return result;
   }

   @Override
   public int quizInsert(Quiz qs) throws SQLException {
      int result = 0;
      result = qd.quizInsert(qs);
      if(result == 0){
         throw new SQLException("삽입 실패");
      }
      return result;
   }

   @Override
   public List<Quiz> quizAll() throws SQLException {
      List<Quiz> list = new ArrayList<>();
      list = qd.quizAll();
      return list;
   }
   
   @Override
   public String quizConfirm(int no) throws SQLException{
      String answer = null;
      answer = qd.quizConfirm(no);
      if(answer == null){
         throw new SQLException("문제에 해당하는 정답이 없습니다.");
      }
      return answer;

   }
   
   @Override
   public List<Object> quizSelect()throws Exception{
      PropertiesLoad pl = new PropertiesLoad();
      Map<String, QuizProperties> quizMap = new TreeMap<>();
      QuizProperties qp = new QuizProperties();
      
      quizMap = pl.load();
      int key = (int)(Math.random() * quizMap.size()) + 1;
      qp = quizMap.get(key+"");
      
      String quiz = null;
      int no = 0;
      List<Object>list = new ArrayList<>();
      quiz = qp.getQuiz();
      no = qp.getNo();
      
      list.add(no);
      list.add(quiz);
      if(quiz == null){
         throw new Exception("퀴즈가 없습니다.");
      }
      return list;
   }

}