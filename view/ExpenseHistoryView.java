package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import controller.KOSTAController;

public class ExpenseHistoryView extends JDialog implements ActionListener {
	String[] str = { "월", "출석", "지각/조퇴/외출", "결석", "훈련비", "식비", "총합" };
	DefaultTableModel dtm = new DefaultTableModel(str, 0);
	JTable tb = new JTable(dtm);
	JScrollPane scroll = new JScrollPane(tb);
	JPanel southPanel = new JPanel();
	JButton deleteBtn = new JButton("삭제");
	JButton okBtn = new JButton("확인");

	public ExpenseHistoryView(JFrame jf) {
		super(jf, "출석확인", true);
		add(scroll, "Center");
		add(southPanel, "South");
		southPanel.add(deleteBtn);
		southPanel.add(okBtn);
		setSize(600, 400);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);

		List<Vector<Object>> list = KOSTAController.selectAllDon(MainView.user.getStudentID());
		addRow(list);
		okBtn.addActionListener(this);
		deleteBtn.addActionListener(this);

	}

	private void addRow(List<Vector<Object>> list) {
		dtm.setRowCount(0);
		for (Vector<Object> vector : list) {
			dtm.addRow(vector);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj == okBtn) {
			dispose();
		} else if (obj == deleteBtn) {
			int result = 0;
			int selected = tb.getSelectedRow();
			if (selected == -1) {
				FailView.errorMessage("월을 선택하세요");
				return;
			}
			result += KOSTAController.deleteMonthAttend(MainView.user.getStudentID(),
					(Integer) dtm.getValueAt(selected, 0));
			result += KOSTAController.deleteMonthCost(MainView.user.getStudentID(),
					(Integer) dtm.getValueAt(selected, 0));
			if (result == 2) {
				SuccessView.successMessage("삭제 완료");
			}
			List<Vector<Object>> list = KOSTAController.selectAllDon(MainView.user.getStudentID());
			if (list == null || list.size() == 0) {
				dtm.setRowCount(0);
				
			} else {
				addRow(list);
				
			}
		}
	}
}
