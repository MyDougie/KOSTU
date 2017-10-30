package model.dto;

public class QuizProperties {
   int no;
   String quiz;
   
   public QuizProperties(){}
   public QuizProperties(int no, String quiz) {
      super();
      this.no = no;
      this.quiz = quiz;
   }
   
   public int getNo() {
      return no;
   }
   public void setNo(int no) {
      this.no = no;
   }
   public String getQuiz() {
      return quiz;
   }
   public void setQuiz(String quiz) {
      this.quiz = quiz;
   }
   
   

}