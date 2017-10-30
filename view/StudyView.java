package view;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import controller.KOSTAController;
import model.dto.StudyRoom;

/**
 * 스터디 예약 뷰
 */
public class StudyView extends JPanel implements ActionListener {

	/**
	 * 콤보박스와 라벨 컴포넌트들
	 */
	JFrame jf;
	URL urlImg = getClass().getClassLoader().getResource("room1.png");
	URL urlImg2 = getClass().getClassLoader().getResource("room2.png");
	Image teachRoomImg = getToolkit().getImage(urlImg);
	Image meetRoomImg = getToolkit().getImage(urlImg2);
	CardLayout card = new CardLayout();
	JPanel cardPanel = new JPanel();
	Canvas teachCanvas = new Canvas() {
		@Override
		public void paint(Graphics g) {
			g.drawImage(teachRoomImg, 0, 0, getWidth(), getHeight(), this);
		}
	};
	Canvas meetCanvas = new Canvas() {
		@Override
		public void paint(Graphics g) {
			g.drawImage(meetRoomImg, 0, 0, getWidth(), getHeight(), this);
		}
	};
	JPanel northPanel = new JPanel();
	JPanel centerPanel = new JPanel();
	JPanel centerWestPanel = new JPanel();
	JPanel westBtnPanel = new JPanel();
	String[] roomKind = { "강사 휴게실", "미팅 룸" };
	String[] dateArr; // 프로그램을 실행시킬때마다 날짜 정보가 달라짐
	JPanel northFlowPanel = new JPanel();
	JLabel roomLabel = new JLabel("ROOM");
	JLabel selectLabel = new JLabel("룸 선택");
	JLabel dateLabel = new JLabel("날짜 선택");
	JComboBox<String> roomCombo = new JComboBox<>(roomKind);
	JComboBox<String> dateCombo;
	JButton searchBtn = new JButton("검색");
	JButton reserveBtn = new JButton("예약하기");
	JButton historyBtn = new JButton("예약내역확인");
	/**
	 * WEST 패널에 위치하는 컴포넌트들
	 */
	String[] columnName = { "예약시간", "예약자", "테이블" };
	Object[][] initDate = { { "07:00 ~ 08:00", null, null }, { "08:00 ~ 09:00", null, null },
			{ "09:00 ~ 10:00", null, null } };
	Object[][] initDateMeet = { { "07:00 ~ 08:00", null, "tb01" }, { "08:00 ~ 09:00", null, "tb01" },
			{ "09:00 ~ 10:00", null, "tb01" }, { "07:00 ~ 08:00", null, "tb02" }, { "08:00 ~ 09:00", null, "tb02" },
			{ "09:00 ~ 10:00", null, "tb02" }, { "07:00 ~ 08:00", null, "tb03" }, { "08:00 ~ 09:00", null, "tb03" },
			{ "09:00 ~ 10:00", null, "tb03" } };
	DefaultTableModel dtm = new DefaultTableModel(columnName, 0) {
		public boolean isCellEditable(int row, int column) {
			return false;
		};
	};
	JTable tb = new JTable(dtm);
	JScrollPane jsp = new JScrollPane(tb);
	
	int month;
	int day;
	/**
	 * 생성자
	 */
	public StudyView(JFrame jf) {
		this.jf = jf;
		
		tableInit();
		dateInit();
		locate();
		KOSTAController.deletePreviousDate("17/" + month + "/" + day);
		searchBtn.addActionListener(this);
		reserveBtn.addActionListener(this);
		historyBtn.addActionListener(this);
	}

	/**
	 * 컴포넌트 설정 및 배치
	 */
	public void locate() {
		setLayout(new BorderLayout());
		setBackground(Color.BLACK);
		add(northPanel, "North");
		add(centerPanel, "Center");
		centerPanel.setLayout(new GridLayout(1, 2));
		centerPanel.setBackground(Color.WHITE);
		centerPanel.add(centerWestPanel);
		centerWestPanel.setLayout(new BorderLayout());
		centerWestPanel.add(jsp, "Center");
		centerWestPanel.add(westBtnPanel, "South");
		westBtnPanel.add(reserveBtn);
		westBtnPanel.add(historyBtn);
		cardPanel.setLayout(card);
		cardPanel.add(teachCanvas, "teach");
		cardPanel.add(meetCanvas, "meet");
		centerPanel.add(cardPanel);
		northPanel.setLayout(new GridLayout(1, 2));
		northPanel.setBackground(Color.ORANGE);
		northPanel.add(northFlowPanel);
		northFlowPanel.setBackground(Color.ORANGE);
		northFlowPanel.add(selectLabel);
		northFlowPanel.add(roomCombo);
		northFlowPanel.add(dateLabel);
		northFlowPanel.add(dateCombo);
		northFlowPanel.add(searchBtn);
		roomLabel.setHorizontalAlignment(JLabel.CENTER);
		northPanel.add(roomLabel);
	}

	/**
	 * 날짜 관련 설정
	 */
	public void dateInit() {
		Calendar cal = Calendar.getInstance();
		month = cal.get(Calendar.MONTH) + 1;
		day = cal.get(Calendar.DATE);
		int maxDay = cal.getActualMaximum(Calendar.DATE);
		dateArr = new String[maxDay - day + 1];
		for (int i = day; i <= maxDay; i++) {
			dateArr[i - day] = month + "/" + i;
		}
		dateCombo = new JComboBox<>(dateArr);
	}

	/**
	 * 테이블 설정
	 */
	public void tableInit() {
		tb.setFont(new Font("나눔고딕코딩", Font.BOLD, 15));
		tb.getTableHeader().setReorderingAllowed(false);
		tb.getTableHeader().setResizingAllowed(false);
		tb.setRowHeight(25);
	}
	
	/**
	 * 스터디룸 종류에 따른 테이블 새로고침
	 */
	public void refreshTable(String roomKind) {
		if (roomKind.equals("meet")) {
			dtm.setRowCount(0);
			dtm.setDataVector(initDateMeet, columnName);
		} else {
			dtm.setRowCount(0);
			dtm.setDataVector(initDate, columnName);
		}
	}
	
	/**
	 * 날짜 FORAMT 변경 ex)-> 3/17 -> 03/17로 바꿔서 SQL 쿼리문으로!
	 */
	public String resizeDate(String date) {
		String[] strArr = date.split("/");
		strArr[0] = String.format("%02d", Integer.parseInt(strArr[0]));
		return strArr[0] + "/" + strArr[1];
	}
	
	/**
	 * Row 추가
	 */
	public void addRowTable(List<Vector<Object>> list) {
		String time = null;
		String table = null;
		String rowTime = null;
		String rowTable = null;
//		System.out.println(list.get(0).get(0).toString().substring(0, 2));
//		System.out.println(list.size());
		for (int i = 0; i < list.size(); i++) {
			Object tableObject = list.get(i).get(2);
			time = list.get(i).get(0).toString().substring(0, 2);
			if (tableObject != null)
				table = tableObject.toString().trim();
			for (int j = 0; j < tb.getRowCount(); j++) {
				Object dtmTableObject = dtm.getValueAt(j, 2);
				rowTime = dtm.getValueAt(j, 0).toString().substring(0, 2);
				if (dtmTableObject != null) {
					rowTable = dtmTableObject.toString().trim();
				}
				if (table != null) {
					if (time.equals(rowTime) && table.equals(rowTable)) {
						dtm.setValueAt(list.get(i).get(1), j, 1);
						dtm.setValueAt(list.get(i).get(2), j, 2);
						break;
					}
				}else {
					if (time.equals(rowTime) && rowTable == null) {
						dtm.setValueAt(list.get(i).get(1), j, 1);
						dtm.setValueAt(list.get(i).get(2), j, 2);
						break;
					}
				}
			}
		}
	}

	/**
	 * 예약하기 버튼이 가능한지 유효성 검사
	 */
	public boolean isReserveValidate() {
		if (tb.getSelectedRow() == -1) {
			FailView.errorMessage("시간이 선택되지 않았습니다.");
			return false;
		}
		if (dtm.getValueAt(tb.getSelectedRow(), 1) != null) {
			System.out.println(dtm.getValueAt(tb.getSelectedRow(), 1).toString());
			FailView.errorMessage("예약자가 이미 있습니다.");
			return false;
		}
		return true;
	}
	
	/**
	 * 취소하기 버튼이 가능한지 유효성 검사
	 */
	public boolean isCancleValidate() {
		if(tb.getSelectedRow() == -1) {
			FailView.errorMessage("취소 할 시간이 선택되지 않았습니다.");
			return false;
		}
		if(dtm.getValueAt(tb.getSelectedRow(), 1) == null) {
			FailView.errorMessage("해당 시간은 예약자가 없습니다.");
			return false;
		}
		if(!dtm.getValueAt(tb.getSelectedRow(), 1).equals(MainView.user.getStudentID())) {
			FailView.errorMessage("해당 예약자가 아닙니다.");
			return false;
		}
		return true;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		/**
		 * 검색버튼
		 */
		if (obj == searchBtn) {
			String roomName = roomCombo.getSelectedItem().toString();
			if (roomName.equals("강사 휴게실")) {
				refreshTable("teach");
				card.show(cardPanel, "teach"); // 강사휴게실 ImagePanel 띄우기
				List<Vector<Object>> list = new ArrayList<>();
				list = KOSTAController.searchForDateRoom(resizeDate(dateCombo.getSelectedItem().toString().trim()),
						"teach");
				if (list != null && list.size() != 0)
					addRowTable(list);
				// else {
				// dtm.setRowCount(0);
				// dtm.setDataVector(initDate, columnName);
				// }
			} else if (roomName.equals("미팅 룸")) {
				refreshTable("meet");
				card.show(cardPanel, "meet"); // 미팅룸 ImagePanel 띄우기
				List<Vector<Object>> list = new ArrayList<>();
				list = KOSTAController.searchForDateRoom(resizeDate(dateCombo.getSelectedItem().toString().trim()),
						"meet");
				if (list != null && list.size() != 0)
					addRowTable(list);
				// else {
				// dtm.setRowCount(0);
				// dtm.setDataVector(initDateMeet, columnName);
				// }
			}
		} // 검색버튼 끝
		/**
		 * 예약버튼
		 */
		else if (obj == reserveBtn) {
			if (isReserveValidate()) {
				int question = JOptionPane.showConfirmDialog(this, "예약하시겠습니까?");
				if(question != 0) {
					return;
				}
				// 날짜 형식 맞추기 ex) date => MM/DD HH:MI로!!
				String date = resizeDate(dateCombo.getSelectedItem().toString().trim()) + " "
						+ dtm.getValueAt(tb.getSelectedRow(), 0).toString().substring(0, 5);
				String roomKind = null;
				String table = null;
				//미팅룸일경우
				if (roomCombo.getSelectedItem().toString().trim().equals("미팅 룸")) {
					roomKind = "meet";
					table = dtm.getValueAt(tb.getSelectedRow(), 2).toString();
				}
				//강사 휴게실일경우
				else {
					roomKind = "teach";
					table = null;
				}

				int result = KOSTAController
						.reserveRoom(new StudyRoom(date, MainView.user.getStudentID(), roomKind, table));
				if (result > 0) {
					
					refreshTable(roomKind);
					List<Vector<Object>> list = new ArrayList<>();
					list = KOSTAController.searchForDateRoom(resizeDate(dateCombo.getSelectedItem().toString().trim()),
							roomKind);
					if (list != null && list.size() != 0)
						addRowTable(list);
				}
			}
		} // 예약버튼 끝
		/**
		 * 예약내역 확인버튼
		 */
		else if(obj == historyBtn) { // 예약 취소하기
			 new StudyHistoryView(jf).setVisible(true);;
		}
	}

}
