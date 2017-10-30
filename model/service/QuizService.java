package model.service;

import java.sql.SQLException;
import java.util.List;

import model.dto.Quiz;

public interface QuizService {
   /**
    * Question 수정
    * update Question_Table question = ?, answer = ? where no = ?;
    */
   int quizUpdate(Quiz qs)throws SQLException;
   
   /**
    * Question 삭제
    * delete from Question_Table where no = ?
    */
   int quizDelete(int no)throws SQLException;
   
   /**
    * Question 삽입
    * insert into Question_Table(no,question,answer) values(?,?,?)
    */
   int quizInsert(Quiz qs)throws SQLException;
   
   /**
    * Question 전체 정보
    * select *from Question_Table;
    */
   List<Quiz> quizAll()throws SQLException;
   
   /**
    * Question no에 해당하는 답
    * select answer from quizdb where no = ?;
    */
   String quizConfirm(int no)throws SQLException;
   
   /**
    * 랜덤으로 문제하나 뽑기
    * 
    */
   List<Object> quizSelect()throws Exception;
   

}