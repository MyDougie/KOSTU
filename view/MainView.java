package view;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import model.dto.Student;

public class MainView extends JFrame {
	JPanel buttonPanel = new JPanel();
	JPanel centerPanel = new JPanel();
	CardLayout card = new CardLayout();
	Container con = getContentPane();
	JScrollPane mainScroll = new JScrollPane(new OriginalView());
	public static Student user = new Student("비어있음","1234", "비어있음", "비어있음", 150);
	/**
	 * North에 위치하는 버튼들
	 */
	JButton mainBtn = new JButton("메인화면");
	JButton expenseBtn = new JButton("지출계산");
	JButton studyBtn = new JButton("스터디룸예약");
	JButton boardBtn = new JButton("게시판");
	JButton examBtn = new JButton("오늘의 문제");
	JButton searchBtn = new JButton("학생 검색");

	public MainView() {
		super("KOSTA Student Program");
//		LogInView logInView = new LogInView(this, "로그인", true);
//		logInView.setVisible(true);
		locate();
		init();
		

		
		/**
		 * 버튼들에 Action부여
		 */
		mainBtn.addActionListener(new ButtonAction());
		expenseBtn.addActionListener(new ButtonAction());
		studyBtn.addActionListener(new ButtonAction());
		boardBtn.addActionListener(new ButtonAction());
		examBtn.addActionListener(new ButtonAction());
		searchBtn.addActionListener(new ButtonAction());
	}

	class ButtonAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Object obj = e.getSource();
			if (obj == mainBtn) {
				card.show(centerPanel, "OriginalView");
			} else if (obj == expenseBtn) {
				card.show(centerPanel, "ExpenseView");
			} else if (obj == examBtn) {
				card.show(centerPanel, "ExamView");
			} else if (obj == studyBtn) {
				card.show(centerPanel, "StudyView");
			} else if (obj == boardBtn) {
				card.show(centerPanel, "BoardView");
			}else if(obj == searchBtn) {
				card.show(centerPanel, "SearchView");
			}
		}

	}
	
	/**
	 * 화면 배치 구성
	 */
	public void locate() {
		buttonPanel.setBackground(Color.DARK_GRAY);
		centerPanel.setBackground(Color.black);
		centerPanel.setLayout(card);
		centerPanel.add("OriginalView", mainScroll);
		centerPanel.add("ExpenseView", new ExpenseView(this));
		centerPanel.add("ExamView", new ExamView());
		centerPanel.add("StudyView", new StudyView(this));
		centerPanel.add("BoardView", new BoardView());
		centerPanel.add("SearchView", new SearchView(this));
		con.add(buttonPanel, "North");
		con.add(centerPanel, "Center");
		buttonPanel.add(mainBtn);
		buttonPanel.add(expenseBtn);
		buttonPanel.add(studyBtn);
		buttonPanel.add(boardBtn);
		buttonPanel.add(examBtn);
		buttonPanel.add(searchBtn);
	}

	public void init() {
		
		con.setBackground(Color.black);
		setSize(700, 650);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		setResizable(false);
	}

//	public static void main(String[] args) {
//		new MainView();
//	}
}
