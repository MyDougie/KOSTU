package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.KOSTAController;

public class SearchView extends JPanel {
	JPanel northPanel = new JPanel();
	JPanel centerPanel = new JPanel();
	JFrame jf;
	JComboBox<String> gisuCombo = new JComboBox<>();
	JButton searchBtn = new JButton("검색");


	public SearchView(JFrame jf) {
		this.jf = jf;
		add(northPanel, "North");
		northPanelInit();
		comboInit();
		init();

		searchBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String gisu = gisuCombo.getSelectedItem().toString();
				new SearchDialogView(jf, gisu).setVisible(true);
			}
		});
	}


	private void comboInit() {
		List<String> list = KOSTAController.getGisuList();
		for (String gisu : list) {
			gisuCombo.addItem(gisu);
		}
	}

	private void northPanelInit() {
		northPanel.setBackground(Color.BLACK);
		JLabel gisuLabel = new JLabel("기수");
		gisuLabel.setHorizontalAlignment(JLabel.CENTER);
		gisuLabel.setFont(new Font("나눔고딕코딩", Font.BOLD, 20));
		gisuLabel.setForeground(Color.WHITE);
	northPanel.add(gisuLabel);
		northPanel.add(gisuCombo);
		northPanel.add(searchBtn);
	}

	private void init() {
		setBackground(Color.BLACK);
	}

	class ProfilePanel extends JPanel {
		Image img;

		public ProfilePanel(Image img, String str) {
			this.img = img;
			setLayout(new BorderLayout());
			setBackground(Color.WHITE);
			Panel grim = new Panel() {
				public void paint(Graphics g) {
					g.drawImage(img, 0, 0, 200, 180, this);
				}
			};
			grim.setPreferredSize(new Dimension(200, 180));
			add(grim, "Center");
			
			JLabel nameLabel = new JLabel(str);
			nameLabel.setPreferredSize(new Dimension(200, 20));
			nameLabel.setHorizontalAlignment(JLabel.CENTER);
			add(nameLabel, "South");
		}
	}

}
