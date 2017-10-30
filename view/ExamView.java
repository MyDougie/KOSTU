package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import controller.KOSTAController;

public class ExamView extends JPanel{
   
   JTextArea tquestion = new JTextArea(10,50);
   JTextField tanswer = new JTextField("정답을 입력하는 칸입니다.",13);
   JLabel labelQuiz = new JLabel("Q: ");
   
   JLabel labelAnswer = new JLabel("A: ");
   JScrollPane scroll = new JScrollPane(tquestion);
   
   KOSTAController kc = new KOSTAController();
   
   JButton yes = new JButton("정답 확인");
   JButton init = new JButton("새로운 문제 받기");
   
   String quiz = null;
   int no = 0; //문제 번호
   List<Object> list = new ArrayList<>();
   
   
   
   JPanel panel = new JPanel(new GridLayout(2,1));
   
   JPanel panel1 = new JPanel(new GridLayout(2,1));
   JPanel panel2 = new JPanel(new FlowLayout());
   JPanel panelUp = new JPanel(new BorderLayout());
   JPanel panel3 = new JPanel(new FlowLayout());
   
   
   public ExamView(){
      tquestion.setEditable(false);
      
      panel.add(labelQuiz);
      
      tquestion.setLineWrap(true);
      scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
      
      panel1.add(scroll);
      
      panel3.add(labelAnswer);
      panel3.add(tanswer);
      panel1.add(panel3);
      
      panel2.add(yes);
      panel2.add(init);
      
      panelUp.add(panel,"West");
      panelUp.add(panel1, "Center");
      panelUp.add(panel2, "South");
      add(panelUp);
      setVisible(true);
      
        try {
         
         list = kc.quizSelect();
         quiz = (String) list.get(1);
         no = (int)list.get(0);
         
      } catch (Exception e) {
         e.printStackTrace();
      }
      tquestion.setText(quiz);
      scroll.getVerticalScrollBar().setValue(0);
      yes.addActionListener(new ActionListener() {
         
         @Override
         public void actionPerformed(ActionEvent e) {
            String answer = null;
            answer = tanswer.getText().trim();
            String dab = null;
            try {
               dab = kc.quizConfirm(no).trim();
               String reDab = dab.replace(" ", "");
        	   String reAnswer = answer.replace(" ", "");
               if(reDab.equals(reAnswer)){
            	   
                  //tf2 = new test_Frame2("정답입니다!");
                  SuccessView.successMessage("정답입니다^.~");
                  try {
                     
                     list = kc.quizSelect();
                     quiz = (String) list.get(1);
                     no = (int)list.get(0);
                     scroll.getVerticalScrollBar().setValue(0);
                  } catch (Exception e1) {
                     e1.printStackTrace();
                  }
                  tquestion.setText(quiz);
                  tanswer.setText("");
               }
               else FailView.errorMessage("틀렸습니다ㅠㅠ");
            } catch (SQLException e1) {
               e1.printStackTrace();
            }
            
         }
      });
      
      init.addActionListener(new ActionListener() {
         
         @Override
         public void actionPerformed(ActionEvent e) {
            try {
               
               list = kc.quizSelect();
               quiz = (String) list.get(1);
               no = (int)list.get(0);
               
            } catch (Exception e1) {
               e1.printStackTrace();
            }
            tquestion.setText(quiz);
            
         }
      });
      
   }

}