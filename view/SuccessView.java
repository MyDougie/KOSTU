package view;

import javax.swing.JOptionPane;
/**
 * 성공 메세지를 출력하기 위한 뷰
 */
public class SuccessView {
	public static void successMessage(String message){
		   JOptionPane.showMessageDialog(null, message);
	   }
}
