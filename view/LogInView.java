package view;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import controller.KOSTAController;
import model.dto.Student;

public class LogInView extends JDialog implements ActionListener {
	Container con = getContentPane();
	JPanel btnPanel = new JPanel();
	JPanel centerPanel = new JPanel();
	JPanel imagePanel;
	JPanel idPanel = new JPanel();
	JPanel pwPanel = new JPanel();
	JLabel idLabel = new JLabel("　아이디");
	JLabel pwLabel = new JLabel("비밀번호");
	JTextField idField = new JTextField(20);
	JTextField pwField = new JPasswordField(20);
	JButton logInBtn = new JButton("로그인");
	JButton joinBtn = new JButton("회원가입");
	JButton exitBtn = new JButton("종료");
	URL urlImg = getClass().getClassLoader().getResource("kosta.png");
	Image img = getToolkit().getImage(urlImg);

	public LogInView(JFrame jf, String title, boolean modale) {
		super(jf, title, modale);

		locate();
		init();
		logInBtn.addActionListener(this);
		joinBtn.addActionListener(this);
		exitBtn.addActionListener(this);
		setVisible(true);
	}

	public void locate() {
	
		con.setBackground(Color.BLACK);
		con.add(btnPanel, "South");
		con.add(centerPanel, "Center");

		imagePanel = new JPanel() {
			public void paint(Graphics g) {
				g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
			}
		};

		btnPanel.setBackground(Color.BLACK);
		btnPanel.add(logInBtn);
		btnPanel.add(joinBtn);
		btnPanel.add(exitBtn);
		centerPanel.add(imagePanel);
		centerPanel.setBackground(Color.BLACK);
		centerPanel.setLayout(new GridLayout(3, 0, 5, 15));

		centerPanel.add(idPanel);
		centerPanel.add(pwPanel);
		idPanel.setBackground(Color.BLACK);
		pwPanel.setBackground(Color.BLACK);
		// idPanel.setLayout(new BorderLayout());
		// pwPanel.setLayout(new BorderLayout());
		idLabel.setForeground(Color.WHITE);
		pwLabel.setForeground(Color.WHITE);
		idPanel.add(idLabel);
		pwPanel.add(pwLabel);
		idPanel.add(idField);
		pwPanel.add(pwField);
	}

	public void init() {

		setSize(300, 300);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setResizable(false);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object eventTarget = e.getSource();
		if (eventTarget == logInBtn) {
			Student data = new Student(idField.getText().trim(), pwField.getText().trim());
			Student dto = KOSTAController.getId(data);
			if (dto != null) {
				if (!KOSTAController.setCurrentUser(dto)) {
					return;
				}
				KOSTAController.saveFile("properties", "quiz_table", "properties");
				new MainView();
				dispose();
			}
		} else if (eventTarget == joinBtn) {
			new JoinView(this, "회원 가입", true).setVisible(true);

		} else {
			System.exit(0);
		}

	}

	public static void main(String[] args) {
		new LogInView(null, "로그인창", true);
	}

}
