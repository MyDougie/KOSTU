package view;

import javax.swing.JOptionPane;
/**
 * 실패 메세지를 출력하기 위한 뷰
 */
public class FailView {
   public static void errorMessage(String message){
	   JOptionPane.showMessageDialog(null, message);
   }
}
