package model.dto;

public class Quiz {
   /**
    * int no : 문제 번호
    * String answer : 정답
    */
   
   private int no = 0;
   private String answer = null;
   
   public Quiz(){}

   public Quiz(int no, String answer) {
      super();
      this.no = no;
      this.answer = answer;
   }

   public int getNo() {
      return no;
   }

   public void setNo(int no) {
      this.no = no;
   }

   public String getAnswer() {
      return answer;
   }

   public void setAnswer(String answer) {
      this.answer = answer;
   }
   

}