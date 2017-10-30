package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Panel;
import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.Vector;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import controller.KOSTAController;
import util.FileProperties;

public class SearchDialogView extends JDialog {
	JPanel centerPanel = new JPanel();
	JScrollPane scrollPane = new JScrollPane(centerPanel);

	public SearchDialogView(JFrame jf, String gisu) {

		super(jf, gisu + "기 명단", true);
		setSize(800, 400);
		add(scrollPane, "Center");
		centerPanel.setLayout(new GridLayout(0, 4));

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);

		List<Vector<Object>> list = KOSTAController.getIdNameFromGisu(gisu);
		// 0 -> id 1-> name 2-> 이미지여부
		int listSize = list.size();

		int x = 0;

		for (int i = 0; i < listSize; i++) {
			Image img = null;

			if (list.get(x).get(2) == null) { // 프사가 없다
				System.out.println("프사업쪄용");
				URL urlImg = getClass().getClassLoader().getResource("base.png");
				img = getToolkit().getImage(urlImg);
				ProfilePanel pp = new ProfilePanel(img, list.get(x).get(1).toString());
				pp.setPreferredSize(new Dimension(180, 180));
				centerPanel.add(pp);
			} else { // 프사가 있다.
				String id = list.get(x).get(0).toString();
				String imgLoc = list.get(x).get(2).toString();
				System.out.println("나 프사	있다!");
				if (!(isFileExist(gisu, id, imgLoc))) {
					System.out.println("근데 내 컴퓨터에는 저장 안되있다!");
					KOSTAController.saveFile(gisu, id, imgLoc);
//					URL urlImg = getClass().getClassLoader().getResource(gisu + "/" + id + "." + imgLoc);
					img = getToolkit().getImage("C:/image/" +gisu + "/" + id + "." + imgLoc);
					ProfilePanel pp = new ProfilePanel(img, list.get(x).get(1).toString());
					pp.setPreferredSize(new Dimension(180, 180));
					centerPanel.add(pp);

				} else {
					// 있으면 그냥 고고띱
					System.out.println("내 컴퓨터에 이미 저장되있죵!");
					System.out.println(gisu);
					System.out.println(id);
//					URL urlImg = getClass().getClassLoader().getResource(gisu + "/" + id + "." + imgLoc);
					img = getToolkit().getImage("C:/image/" +gisu + "/" + id + "." + imgLoc);
					ProfilePanel pp = new ProfilePanel(img, list.get(x).get(1).toString());
					pp.setPreferredSize(new Dimension(180, 180));
					centerPanel.add(pp);
				}
			}
			x++;

		}
		pack();

	}

	private boolean isFileExist(String gisu, String id, String imgLoc) {
		File file = new File("C:/image/" + gisu + "/" + id + "." + imgLoc);
		return file.exists();
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

			add(grim, "Center");

			JLabel nameLabel = new JLabel(str);

			nameLabel.setHorizontalAlignment(JLabel.CENTER);
			add(nameLabel, "South");
		}
	}

}
