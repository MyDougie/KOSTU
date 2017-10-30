
package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import controller.KOSTAController;

class ExpenseView extends JPanel implements ActionListener {
	JFrame jf;
	int startYear, endYear;
	int startMonth, endMonth;
	int startDay, endDay;
	int selYear, selMonth;
	int trainCal, foodCal, totalCal;
	Map<String, ArrayList<Integer>> saveAttend;
	Calendar cal = Calendar.getInstance();
	JButton clearBtn = new JButton("시작일 초기화");
	JButton saveBtn = new JButton("출석 저장");
	JButton calBtn = new JButton("계산하기");
	JButton checkBtn = new JButton("내 출석부 보기");
	JTextField trainField = new JTextField(5);
	JTextField foodField = new JTextField(5);
	JTextField totalField = new JTextField(5);
	JRadioButton startBtn = new JRadioButton("시작일설정");
	JRadioButton attendBtn = new JRadioButton("출석");
	JRadioButton lateBtn = new JRadioButton("지각&조퇴&외출");
	JRadioButton absentBtn = new JRadioButton("결석");
	ButtonGroup group = new ButtonGroup();

	boolean firstSelect = false;
	int year, month, day;
	JComboBox<Integer> yearCombo = new JComboBox<>();
	JComboBox<Integer> monthCombo = new JComboBox<>();

	JButton selectBtn = new JButton("선택");
	JPanel centerPanel = new JPanel();
	JPanel calenderPanel = new JPanel();
//	JPanel northPanel = new JPanel();
	JPanel eastPanel = new JPanel();
	JPanel yoilAndMonthPanel = new JPanel();
	JPanel yoilPanel = new JPanel();
	JPanel monthPanel = new JPanel();
	JButton[] dayBtn = new JButton[42];
	JButton a = new JButton("TEST");
	String[] yoil = { "SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT" };
	JLabel[] yoilLabel = new JLabel[7];

	public ExpenseView(JFrame jf) {
		this.jf = jf;
		setLayout(new BorderLayout());
//		add(northPanel, "North");
		add(centerPanel, "Center");
		add(eastPanel, "East");

		centerPanel.setLayout(new BorderLayout());
		centerPanel.add(calenderPanel, "Center");
		centerPanel.add(yoilAndMonthPanel, "North");
		yoilAndMonthPanel.setLayout(new GridLayout(2, 0));
		yoilAndMonthPanel.add(monthPanel);
		yoilAndMonthPanel.add(yoilPanel);

		radioInit();
		eastInit();
		monthInit();
		yoilInit();
		selectBtn.addActionListener(this);
		saveBtn.addActionListener(this);
		clearBtn.addActionListener(this);
		calBtn.addActionListener(this);
		checkBtn.addActionListener(this);

	}

	private void radioInit() {
		group.add(attendBtn);
		group.add(absentBtn);
		group.add(lateBtn);
		group.add(startBtn);
		attendBtn.setBackground(Color.ORANGE);
		absentBtn.setBackground(Color.ORANGE);
		lateBtn.setBackground(Color.ORANGE);
		startBtn.setBackground(Color.ORANGE);
	}

	private void calendarInit() {
		calenderPanel.setLayout(new GridLayout(0, 7));
		for (int i = 0; i < dayBtn.length; i++) {
			dayBtn[i] = new JButton("");
			dayBtn[i].addActionListener(this);
			dayBtn[i].setBackground(Color.WHITE);
			calenderPanel.add(dayBtn[i]);
		}
	}

	private void monthInit() {
		monthPanel.setBackground(Color.GREEN);
		year = cal.get(Calendar.YEAR);
		month = cal.get(Calendar.MONTH) + 1;
		for (int i = -1; i < 2; i++) {
			yearCombo.addItem(year + i);
		}
		for (int i = 1; i < 13; i++) {
			monthCombo.addItem(i);
		}
		monthPanel.add(yearCombo);
		monthPanel.add(new JLabel("년"));
		monthPanel.add(monthCombo);
		monthPanel.add(new JLabel("월"));
		monthPanel.add(selectBtn);
	}

	private void yoilInit() {
		yoilPanel.setLayout(new GridLayout(0, 7));
		yoilPanel.setBackground(Color.PINK);
		for (int i = 0; i < 7; i++) {
			yoilLabel[i] = new JLabel(yoil[i]);
			yoilLabel[i].setHorizontalAlignment(JLabel.CENTER);
			yoilPanel.add(yoilLabel[i]);
		}
	}

	private void eastInit() {
		eastPanel.setBackground(Color.ORANGE);
		eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.Y_AXIS));
		eastPanel.add(saveBtn);
		eastPanel.add(clearBtn);
		eastPanel.add(startBtn);
		eastPanel.add(attendBtn);
		eastPanel.add(lateBtn);
		eastPanel.add(absentBtn);
		eastPanel.add(new JLabel("훈련비 계산"));
		trainField.setFocusable(false);
		eastPanel.add(trainField);
		eastPanel.add(new JLabel("식비 계산"));
		foodField.setFocusable(false);
		eastPanel.add(foodField);
		eastPanel.add(new JLabel("총 계산"));
		totalField.setFocusable(false);
		eastPanel.add(totalField);
		eastPanel.add(calBtn);
		eastPanel.add(checkBtn);
	}

	private void buttonWhite() {
		for (int i = 0; i < 42; i++) {
			dayBtn[i].setBackground(Color.WHITE);
		}
	}

	/**
	 * 한달 색칠하는 메서드
	 */
	private void buttonColor() {
		if (saveAttend != null) {
			ArrayList<Integer> list = saveAttend.get(selYear + "/" + selMonth);
			if (list != null && list.size() != 0) {
				for (int i = 0; i < 42; i++) {
					if (dayBtn[i].getText().equals("")) {
						continue;
					} else {
						int colorDay = Integer.parseInt(dayBtn[i].getText());
						int attendColor = list.get(colorDay - 1);
						if (attendColor == 0) {
							continue;
						} else if (attendColor == 1) {
							dayBtn[i].setBackground(Color.CYAN);
						} else if (attendColor == 2) {
							dayBtn[i].setBackground(Color.YELLOW);
						} else if (attendColor == 3) {
							dayBtn[i].setBackground(Color.RED);
						}
					}
				}
			}
		}
	}

	private void initStartEnd() {
		startMonth = 0;
		endMonth = 0;
		startYear = 0;
		endYear = 0;
		endDay = 0;
		startDay = 0;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj == selectBtn) {
			if (!firstSelect) {
				calendarInit();
				firstSelect = true;
			}
			selYear = Integer.parseInt(yearCombo.getSelectedItem().toString());
			selMonth = Integer.parseInt(monthCombo.getSelectedItem().toString());
			cal.set(Calendar.YEAR, (Integer) yearCombo.getSelectedItem());
			cal.set(Calendar.MONTH, (Integer) monthCombo.getSelectedItem() - 1);
			cal.set(Calendar.DATE, 1);
			int dayNum = cal.get(Calendar.DAY_OF_WEEK);
			int maxDay = cal.getActualMaximum(Calendar.DATE);
			for (int i = 0; i < dayNum - 1; i++) {
				dayBtn[i].setText("");
			}
			// 5, 35
			for (int i = dayNum - 1; i <= maxDay + dayNum - 1; i++) {
				dayBtn[i].setText("" + (i - dayNum + 2));
			}
			for (int i = maxDay + dayNum - 1; i < 42; i++) {
				dayBtn[i].setText("");
			}
			buttonWhite();
			buttonColor();

		} else if (obj == saveBtn) {
			// System.out.println(saveAttend);
			int result = 0;
			if (saveAttend == null || saveAttend.size() == 0) {
				FailView.errorMessage("출석정보를 설정하세요");
				return;
			}
			KOSTAController.setAttend(saveAttend, MainView.user);
			KOSTAController.setAbsent(saveAttend, MainView.user);
			KOSTAController.setLate(saveAttend, MainView.user);
			trainCal = KOSTAController.payForJobTrainingCal(MainView.user);
			foodCal = KOSTAController.foodExpensesCal(MainView.user);
			totalCal = trainCal + foodCal;
			result += KOSTAController.attendInsert(MainView.user, startMonth);
			result += KOSTAController.costInsert(MainView.user.getStudentID(), startMonth, totalCal, trainCal, foodCal);
			if(result == 2) {
				SuccessView.successMessage("저장 완료");
			}
		} else if (obj == clearBtn) {
			if (firstSelect) {
				saveAttend = new HashMap<>();
				buttonWhite();
				initStartEnd();
			}
		} else if (obj == calBtn) {
			if (saveAttend == null || saveAttend.size() == 0) {
				FailView.errorMessage("출석정보를 설정하세요");
				return;
			}
			KOSTAController.setAttend(saveAttend, MainView.user);
			KOSTAController.setAbsent(saveAttend, MainView.user);
			KOSTAController.setLate(saveAttend, MainView.user);
			trainCal = KOSTAController.payForJobTrainingCal(MainView.user);
			foodCal = KOSTAController.foodExpensesCal(MainView.user);
			totalCal = trainCal + foodCal;
			foodField.setText("" + foodCal);
			trainField.setText("" + trainCal);
			totalField.setText("" + totalCal);
		} else if (obj == checkBtn) {
			new ExpenseHistoryView(jf).setVisible(true);
		} else {
			for (int i = 0; i < dayBtn.length; i++) {
				if (obj == dayBtn[i]) {
					if (dayBtn[i].getText().equals("")) {
						FailView.errorMessage("날짜를 선택하세요");
						return;
					}
					if (startBtn.isSelected()) { // 시작일 설정 라디오버튼
						saveAttend = new HashMap<>();
						initStartEnd();
						int day = Integer.parseInt(dayBtn[i].getText());
						Calendar attendCal = new GregorianCalendar(Locale.KOREA);
						attendCal.set(Calendar.YEAR, selYear);
						attendCal.set(Calendar.MONTH, selMonth - 1);
						attendCal.set(Calendar.DATE, day);
						startYear = selYear;
						startMonth = selMonth;
						startDay = day;
						attendCal.add(Calendar.DATE, 30);
						endYear = attendCal.get(Calendar.YEAR);
						endMonth = attendCal.get(Calendar.MONTH) + 1;
						endDay = attendCal.get(Calendar.DATE);
//						System.out.println(startYear + " " + startMonth + " " + startDay);
//						System.out.println(endYear + " " + endMonth + " " + endDay);
						if (startMonth == endMonth) {
							ArrayList<Integer> days = new ArrayList<>();
							for (int j = 0; j < 32; j++) {
								days.add(1);
							}
							saveAttend.put(startYear + "/" + startMonth, days);
						} else {
							ArrayList<Integer> startDays = new ArrayList<>();
							ArrayList<Integer> endDays = new ArrayList<>();
							for (int j = 0; j < startDay - 1; j++) {
								startDays.add(0);
							}
							for (int j = startDay; j < 32; j++) {
								startDays.add(1);
							}
							for (int j = 0; j < endDay - 1; j++) {
								endDays.add(1);
							}
							for (int j = endDay; j < 32; j++) {
								endDays.add(0);
							}
							saveAttend.put(startYear + "/" + startMonth, startDays);
							saveAttend.put(endYear + "/" + endMonth, endDays);
						}
						// System.out.println(saveAttend);
						buttonWhite();
						buttonColor();

					} // 시작일 라디오버튼 끝
					else if (lateBtn.isSelected()) { // 지각
						if (saveAttend == null || saveAttend.size() == 0) {
							FailView.errorMessage("시작일을 설정해주세요");
							return;
						}
						ArrayList<Integer> list = saveAttend.get(selYear + "/" + selMonth);
						if (list == null || list.size() == 0) {
							FailView.errorMessage("범위를 벗어났습니다");
							return;
						}
						int selDay = Integer.parseInt(dayBtn[i].getText());
						if (list.get(selDay - 1) == 0) {
							FailView.errorMessage("범위를 벗어났습니다");
							return;
						} else {
							list.set(selDay - 1, 2);
						}
						buttonWhite();
						buttonColor();
					} // 지각 끝
					else if (absentBtn.isSelected()) { // 결석
						if (saveAttend == null || saveAttend.size() == 0) {
							FailView.errorMessage("시작일을 설정해주세요");
							return;
						}
						ArrayList<Integer> list = saveAttend.get(selYear + "/" + selMonth);
						if (list == null || list.size() == 0) {
							FailView.errorMessage("범위를 벗어났습니다");
							return;
						}
						int selDay = Integer.parseInt(dayBtn[i].getText());
						if (list.get(selDay - 1) == 0) {
							FailView.errorMessage("범위를 벗어났습니다");
							return;
						} else {
							list.set(selDay - 1, 3);
						}
						buttonWhite();
						buttonColor();
					} // 결석 끝
					else if (attendBtn.isSelected()) { // 출석
						if (saveAttend == null || saveAttend.size() == 0) {
							FailView.errorMessage("시작일을 설정해주세요");
							return;
						}
						ArrayList<Integer> list = saveAttend.get(selYear + "/" + selMonth);
						if (list == null || list.size() == 0) {
							FailView.errorMessage("범위를 벗어났습니다");
							return;
						}
						int selDay = Integer.parseInt(dayBtn[i].getText());
						if (list.get(selDay - 1) == 0) {
							FailView.errorMessage("범위를 벗어났습니다");
							return;
						} else {
							list.set(selDay - 1, 1);
						}
						buttonWhite();
						buttonColor();
					} // 출석끝
				}

			}
		}
	}
}