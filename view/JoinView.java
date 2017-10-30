package view;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Container;
import java.awt.FileDialog;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import controller.KOSTAController;
import model.dto.Student;

public class JoinView extends JDialog implements ActionListener {
	
	String abFile;
	PrintWriter pw;
	boolean idb, nameb, juminb, pwdb, ageb, phoneb;
	BufferedInputStream bis = null;
	BufferedOutputStream bos = null;
	// 패널 준비
	JPanel p1 = new JPanel();
	JPanel p2 = new JPanel();
	JPanel p3 = new JPanel();
	JPanel p4 = new JPanel();
	JPanel p5 = new JPanel();
	JPanel sel = new JPanel();
	JPanel picSel = new JPanel();
	JPanel allhalf = new JPanel();
	JPanel idcheck = new JPanel();
	JPanel pwcheck = new JPanel();

	// Label 준비
	JLabel text = new JLabel();
	JLabel gisu = new JLabel("기수 :");
	JLabel id = new JLabel("ID : ");
	JLabel pwd = new JLabel("Password : ");
	JLabel pwdch = new JLabel("Password 확인 : ");
	JLabel name = new JLabel("이름 :");
	JLabel sex = new JLabel("성별 :");
	JLabel age = new JLabel("나이 :");
	JLabel jumin = new JLabel("주민등록번호 :");
	JLabel phone = new JLabel("핸드폰번호 :");
	// Text Field 준비
	JTextField gisuField = new JTextField();
	JTextField idField = new JTextField();
	JTextField pwdField = new JTextField();
	JTextField pwdchField = new JTextField();
	JTextField nameField = new JTextField();
	JTextField sexField = new JTextField();
	JTextField ageField = new JTextField();
	JTextField juminField = new JTextField();
	JTextField phoneField = new JTextField();
	// Button 준비
	JButton pic = new JButton("사진 첨부");
	JButton confirms = new JButton("가입 하기");
	JButton cancel = new JButton("가입 취소");
	JButton idCheckBtn = new JButton("중복 확인");
	JButton pwCheckBtn = new JButton("비밀번호 확인");

	Image image;
	// ImageIcon
	// 경로 : image\fire.jpg
	static String location = "image/fire.jpg";
	static ImageIcon icon = new ImageIcon(location);
	// JLabel me = new JLabel("",icon,JLabel.CENTER);
	DrawCanvas canvas = new DrawCanvas();
	String dname;
	// Scroll & GroupButton
	Integer[] giarr = new Integer[20];
	{
		for (int i = 0; i < 20; i++) {
			giarr[i] = i + 145;
		}
	}
	JComboBox<Integer> gilist = new JComboBox<Integer>(giarr);

	JRadioButton man = new JRadioButton("남자");
	JRadioButton woman = new JRadioButton("여자");
	String selSex;

	// File Dialog
	FileDialog fileDialog = new FileDialog(this, "불러오기", FileDialog.LOAD);

	public JoinView(LogInView logInView, String string, boolean b) {
		super(logInView, string, true);
		setSize(600, 500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		ButtonGroup group = new ButtonGroup();
		group.add(man);
		group.add(woman);
		Container container = getContentPane();
		container.setLayout(new BorderLayout());
		allhalf.setLayout(new GridLayout(1, 2));
		container.add(allhalf, "Center");
		container.add(p3, "South");
		allhalf.add(p1);
		allhalf.add(p2);

		p1.setLayout(new BorderLayout());
		p1.add(p4, "West");
		p1.add(p5, "Center");

		p2.setLayout(new BorderLayout());
		p2.add(canvas, "Center");
		p2.add(pic, "South");

		p3.setLayout(new BorderLayout());
		p3.add(confirms, "West");
		p3.add(cancel, "East");

		p4.setLayout(new GridLayout(9, 1));
		p5.setLayout(new GridLayout(9, 1));

		p4.add(gisu);
		p4.add(id);
		p4.add(pwd);
		p4.add(pwdch);
		p4.add(name);
		p4.add(sex);
		p4.add(age);
		p4.add(jumin);
		p4.add(phone);

		p5.add(picSel);
		picSel.setLayout(new BorderLayout());
		picSel.add(gilist, "West");
		p5.add(idcheck);
		idcheck.setLayout(new BorderLayout());
		idcheck.add(idField, "Center");
		idcheck.add(idCheckBtn, "East");
		p5.add(pwdField);
		p5.add(pwcheck);
		pwcheck.setLayout(new BorderLayout());
		pwcheck.add(pwdchField, "Center");
		pwcheck.add(pwCheckBtn, "East");
		p5.add(nameField);
		p5.add(sel);
		sel.setLayout(new BorderLayout());
		sel.add(man, "West");
		sel.add(woman, "East");
		p5.add(ageField);
		p5.add(juminField);
		p5.add(phoneField);

		pic.addActionListener(this);
		confirms.addActionListener(this);
		idCheckBtn.addActionListener(this);
		pwCheckBtn.addActionListener(this);
		cancel.addActionListener(this);

	}

	class DrawCanvas extends Canvas {
		@Override
		public void paint(Graphics g) {
			// System.out.println("D");
			// System.out.println(icon.getImage());
			g.clearRect((p2.getWidth() - 200) / 2, (p2.getHeight() - 300) / 2, 200, 300);
			g.drawImage(image, (p2.getWidth() - 200) / 2, (p2.getHeight() - 300) / 2, 200, 300, this);
		}

		@Override
		public void update(Graphics g) {
			// System.out.println("E");
			paint(g);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object eventTarget = e.getSource();
		if (eventTarget == pic) {
			fileDialog.setVisible(true);
			String fileStr = fileDialog.getFile();
			if (fileStr == null) {
				return;
			}
			abFile = fileDialog.getDirectory() + fileDialog.getFile();
			dname = fileStr.substring(fileStr.indexOf(".") + 1, fileStr.length());
			if(!((dname.equals("jpg")) || (dname.equals("png")))) {
				FailView.errorMessage("jpg 혹은 png확장자를 사용하세요");
				return;
			}
			System.out.println("dname :" + dname);
			image = getToolkit().getImage(abFile);
			canvas.repaint();

		} else if (eventTarget == idCheckBtn) {
			if(idField.getText().trim().equals("")){
	               FailView.errorMessage("아이디 값을 입력해 주세요");
	               return;
	            }else{
	            if (KOSTAController.idCheck(idField.getText().trim())) {
	               idField.setEnabled(false);
	               idb = true;
	            } else {
	               idField.requestFocus();
	            }
	         }

		} else if (eventTarget == pwCheckBtn) {
			if (!idb) {
				// System.out.println("새로운 아이디 중복여부");
				FailView.errorMessage("ID 중복여부 확인");
				return;
			}
			// System.out.println("비밀번호 확인");
			if (pwdField.getText().equals(pwdchField.getText())) {
				SuccessView.successMessage("비밀번호가 일치 합니다.");
				pwdField.setEnabled(false);
				pwdchField.setEnabled(false);
				pwdb = true;
			} else {
				FailView.errorMessage("비밀번호가 일치 하지 않습니다.");
				return;
			}
	
		} else if (eventTarget == confirms) {
			// System.out.println(nameb);
			String name = nameField.getText();
			int nameLen = name.length();
			// System.out.println(nameLen);
			for (int i = 0; i < nameLen; i++) {
				// System.out.println("몇바퀴 :" + i);
				String s = "" + name.charAt(i);
				// System.out.println(s);
				if (!s.trim().matches("[가-힣]+")) {
					FailView.errorMessage("한글만 입력해 주세요");
					nameField.setText("");
					nameField.requestFocus();

					return;
				}
			}
			nameb = true;

			// 성별 유무 확인
			if (man.isSelected()) {
				selSex = man.getText();
			} else if (woman.isSelected()) {
				selSex = woman.getText();
			} else {
				FailView.errorMessage("성별을 선택해 주세요");
				return;
			}
			int ageLen = ageField.getText().length();
			String age = ageField.getText();
			for (int i = 0; i < ageLen; i++) {
				String s = "" + age.charAt(i);
				if (!s.trim().matches("[0-9]+")) {
					FailView.errorMessage("숫자만 입력해 주세요");
					ageField.requestFocus();
					return;
				}
			}
			if (Integer.parseInt(age) < 100 && Integer.parseInt(age) > 10 && ageField.getText() != null) {
				ageb = true;
			} else {
				FailView.errorMessage("10세에서 100세 이하로 나이를 입력해 주세요");
				ageField.setText(" ");
				ageField.requestFocus();
				return;
			}

			int juminLen = juminField.getText().length();
			String jumin = juminField.getText();
			for (int i = 0; i < juminLen; i++) {
				String s = "" + jumin.charAt(i);
				if (!s.trim().matches("[0-9]+")) {
					FailView.errorMessage("주민등록 번호를확인해 주세요");
					juminField.setText("[-]제외하고 입력해주세요");
					juminField.requestFocus();
					return;
				}
			}
			juminb = true;
			int phoneLen = phoneField.getText().length();
			String phone = phoneField.getText();
			for (int i = 0; i < phoneLen; i++) {
				String s = "" + jumin.charAt(i);
				if (!s.trim().matches("[0-9]+")) {
					FailView.errorMessage("핸드폰번호를 확인해 주세요");
					phoneField.setText(" ");
					phoneField.requestFocus();
					return;
				}
			}
			phoneb = true;
			// System.out.println(dname);
			if (phoneb) {
				Student student = new Student((int) gilist.getSelectedItem(), idField.getText(), pwdField.getText(),
						nameField.getText(), selSex, Integer.parseInt(ageField.getText()), juminField.getText(),
						phoneField.getText(), dname);
				int a = KOSTAController.insertStudent(student);
				if (a > 0) {
					// 이미지를 전송하자.
					if (dname != null) {
						KOSTAController.transFile(gilist.getSelectedItem().toString(), idField.getText() +"."+ dname, abFile);
					}
					dispose();
				}
			}

		} else if (eventTarget == cancel) {
			this.dispose();
		}

	}

	public boolean check() {
		boolean sel = false;
		return sel;

	}

}
