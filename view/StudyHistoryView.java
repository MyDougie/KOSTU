package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import controller.KOSTAController;

public class StudyHistoryView extends JDialog implements ActionListener {

	JPanel btnPanel = new JPanel();
	String[] columnName = { "예약번호", "예약자", "예약날짜", "스터디룸 종류", "테이블 번호" };
	DefaultTableModel dtm = new DefaultTableModel(columnName, 0) {
		public boolean isCellEditable(int row, int column) {
			return false;
		};
	};
	JTable tb = new JTable(dtm);
	JScrollPane tableScroll = new JScrollPane(tb);
	JButton okBtn = new JButton("확인");
	JButton cancleBtn = new JButton("예약취소");

	public StudyHistoryView(JFrame jf) {
		super(jf, "예약내역확인", true);
		locate();
		List<Vector<Object>> list = KOSTAController.searchForIDRoom(MainView.user.getStudentID());
		if (list != null && list.size() != 0) {
			refreshTable(list);
		}
		init();
		tableInit();
		okBtn.addActionListener(this);
		cancleBtn.addActionListener(this);
	}

	public void refreshTable(List<Vector<Object>> list) {
		dtm.setRowCount(0);
		for (Vector<Object> vector : list) {
			vector.add(3, replaceRoomName(vector.get(3).toString()));
			vector.remove(4);
			dtm.addRow(vector);
		}
	}

	public String replaceRoomName(String roomName) {
		if (roomName.trim().equals("meet")) {
			return "미팅 룸";
		} else if (roomName.trim().equals("teach")) {
			return "강사 휴게실";
		}
		return "";
	}

	public void tableInit() {
		tb.getTableHeader().setReorderingAllowed(false);
		tb.getTableHeader().setResizingAllowed(false);
		tb.getColumn("예약번호").setPreferredWidth(10);
		tb.getColumn("예약자").setPreferredWidth(15);
		tb.getColumn("예약날짜").setPreferredWidth(100);
		tb.getColumn("스터디룸 종류").setPreferredWidth(40);
		tb.getColumn("테이블 번호").setPreferredWidth(15);
	}

	public void locate() {
		add(tableScroll, "Center");
		add(btnPanel, "South");
		btnPanel.add(okBtn);
		btnPanel.add(cancleBtn);
	}

	public void init() {
		setSize(600, 400);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		/**
		 * 확인버튼
		 */
		if (obj == okBtn) {
			dispose();
		}
		/**
		 * 예약 취소버튼
		 */
		else if (obj == cancleBtn) {
			if (tb.getSelectedRow() == -1) {
				FailView.errorMessage("선택된 예약이 없습니다.");
				return;
			} else {
				if (JOptionPane.showConfirmDialog(this, "정말로 취소하시겠습니까?") != 0) {
					return;
				} else {
					int result = KOSTAController
							.cancleRoom(Integer.parseInt(dtm.getValueAt(tb.getSelectedRow(), 0).toString()));
					if (result > 0) {
						List<Vector<Object>> list = KOSTAController.searchForIDRoom(MainView.user.getStudentID());
						if (list != null && list.size() != 0) {
							refreshTable(list);
						} else {
							dtm.setRowCount(0);
						}
					}
				}
			}
		}

	}
}
