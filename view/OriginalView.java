package view;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URI;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import controller.KOSTAController;
import util.FileProperties;

/**
 * 초기의 메인화면을 출력하는 뷰
 */
public class OriginalView extends JPanel {
	Image profileImage;
	LogoCanvas logoCanvas = new LogoCanvas();
	Image[] logoImage = new Image[5];
	JPanel mainGridPanel = new JPanel();
	JPanel northGridPanel = new JPanel();
	JPanel ingiPanel = new JPanel();
	JPanel studentPanel = new JPanel();
	JPanel newsPanel = new JPanel();
	JPanel newsNorthPanel = new JPanel();
	JPanel newsCenterPanel = new JPanel();
	JScrollPane newsScroll = new JScrollPane(newsCenterPanel);
	JScrollPane ingiScroll = new JScrollPane(ingiPanel);
	JLabel ingiWord = new JLabel("현재 인기검색어");
	JLabel newsTitleLabel = new JLabel("★최신 IT 뉴스★");
	JButton moreButton = new JButton("뉴스 자세히보기");
	JLabel idLabel = new JLabel("ID: " + MainView.user.getStudentID());
	JLabel nameLabel = new JLabel("NAME: " + MainView.user.getStudentName());
	JLabel phoneLabel = new JLabel("PHONE: " + MainView.user.getStudentPhone());
	JLabel infoLabel = new JLabel("--------회원 정보--------");
	BoxLayout boxLayout = new BoxLayout(ingiPanel, BoxLayout.Y_AXIS);

	public OriginalView() {
		for (int i = 0; i < logoImage.length; i++) {
			URL urlImg = getClass().getClassLoader().getResource("p" + (i + 1) + ".png");
			logoImage[i] = getToolkit().getImage(urlImg);
		}
		Thread ingiTh = new Thread() {
			public void run() {
				while (true) {
					try {
						Thread.sleep(10000);
						setIngiPanel();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		};
		Thread logoTh = new Thread() {
			public void run() {
				int i = 0;
				while (true) {
					try {
						if (i > 3) {
							i = 0;
						}
						i++;
						logoCanvas.setImage(logoImage[i]);
						logoCanvas.repaint();
						Thread.sleep(100);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		};

		ingiTh.start();
		logoTh.start();
		setBackground(Color.BLACK);
		setLayout(new BorderLayout());
		locate();

		moreButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Desktop.getDesktop().browse(new URI("http://www.itworld.co.kr/news"));
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});

	}

	/**
	 * 컴포넌트 및 레이아웃 배치
	 */
	public void locate() {
		add(mainGridPanel, "Center");
		mainGridPanel.setLayout(new BorderLayout());
		mainGridPanel.add(northGridPanel, "North");
		mainGridPanel.add(newsPanel, "Center");
		northGridPanel.setLayout(new BorderLayout());
		ingiPanel.setLayout(boxLayout);
		ingiPanel.setBackground(Color.WHITE);
		ingiWord.setForeground(Color.RED);
		ingiWord.setFont(new Font("나눔고딕코딩", Font.BOLD, 15));
		ingiPanel.setBackground(Color.BLACK);
		ingiPanel.add(ingiWord);
		setIngiPanel();
		northGridPanel.add(ingiScroll, "West");
		logoCanvas.setImage(logoImage[0]);
		northGridPanel.add(logoCanvas, "Center");
		northGridPanel.add(studentPanel, "East");
		studentPanelInit();
		newsPanelInit();
	}

	/**
	 * 뉴스패널 설정
	 */
	private void newsPanelInit() {
		newsPanel.setBackground(Color.BLACK);
		newsPanel.setLayout(new BorderLayout());
		newsTitleLabel.setForeground(Color.RED);
		newsTitleLabel.setFont(new Font("나눔고딕코딩", Font.BOLD, 25));
		newsTitleLabel.setHorizontalAlignment(JLabel.CENTER);
		newsPanel.add(newsNorthPanel, "North");
		newsPanel.add(newsScroll, "Center");
		newsNorthPanel.setBackground(Color.BLACK);
		newsCenterPanel.setLayout(new BoxLayout(newsCenterPanel, BoxLayout.Y_AXIS));
		newsCenterPanel.setBackground(Color.DARK_GRAY);
		newsNorthPanel.setLayout(new BorderLayout());
		newsNorthPanel.add(newsTitleLabel, "Center");
		newsNorthPanel.add(moreButton, "East");
		try {
			Document doc = Jsoup.connect("http://www.itworld.co.kr").get();
			Elements element = doc.select("li > a > span");
			for (int i = 0; i < element.size(); i++) {
				JLabel newsLabel = new JLabel(element.get(i).select("span.font_16").text());
				newsLabel.setFont(new Font("나눔고딕코딩", 0, 17));
				newsLabel.setForeground(Color.WHITE);
				newsCenterPanel.add(newsLabel);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 학생정보 패널 설정
	 */
	private void studentPanelInit() {
		String gisu = MainView.user.getKostaNumber() + "";
		String id = MainView.user.getStudentID();
		String imgLoc = MainView.user.getImgLoc();
		studentPanel.setBackground(Color.BLACK);
		studentPanel.setLayout(new BoxLayout(studentPanel, BoxLayout.Y_AXIS));
		infoLabel.setForeground(Color.CYAN);
		idLabel.setForeground(Color.WHITE);
		nameLabel.setForeground(Color.WHITE);
		phoneLabel.setForeground(Color.WHITE);
		studentPanel.add(infoLabel);
		studentPanel.add(idLabel);
		studentPanel.add(nameLabel);
		studentPanel.add(phoneLabel);
		if (imgLoc == null) {
			System.out.println("널이야");
			URL urlImg = getClass().getClassLoader().getResource("base.png");
			profileImage = getToolkit().getImage(urlImg);
		} else {
			System.out.println("아니야");
//			URL urlFile = getClass().getClassLoader().getResource(gisu + "/" + id + "." + imgLoc);
			File file = null;
			try {
			file = new File("C:/image/"+gisu + "/" + id + "." + imgLoc);
			}catch(Exception e) {
				e.printStackTrace();
			}
			if (file.exists()) {
//				URL urlImg = getClass().getClassLoader().getResource(gisu + "/" + id + "." + imgLoc);
				profileImage = getToolkit().getImage("C:/image/"+gisu + "/" + id + "." + imgLoc);
			} else {
				System.out.println("파일이 없다?");
				KOSTAController.saveFile(gisu, id, imgLoc);
//				URL urlImg = getClass().getClassLoader().getResource(gisu + "/" + id + "." + imgLoc);
				profileImage = getToolkit().getImage("C:/image/"+gisu + "/" + id + "." + imgLoc);
			}
		}
		
		Canvas profileCanvas = new Canvas() {
			@Override
			public void paint(Graphics g) {
				g.drawImage(profileImage, 0, 0, 125, 100, this);
			}
		};
		studentPanel.add(profileCanvas);
		revalidate();
		repaint();

	}

	public void makeFolder(String gisu) {
		File file = new File(FileProperties.IMAGE_PATH + "/" + gisu);
		if (!file.exists()) {
			file.mkdirs();
		}
	}

	/**
	 * 인기검색어 패널
	 */
	private void setIngiPanel() {
		try {
			ingiPanel.removeAll();
			ingiPanel.add(ingiWord);
			Document document = Jsoup.connect("http://www.naver.com").get();

			if (null != document) {
				// id가 realrank 인 ol 태그 아래 id가 lastrank인 li 태그를 제외한 모든 li 안에
				// 존재하는
				// a 태그의 내용을 가져옵니다.
				Elements elements = document.select("ol#realrank > li:not(#lastrank) > a");
				for (int i = 0; i < 10; i++) {
					JLabel ingiLabel = new JLabel((i + 1) + ": " + elements.get(i).select("span.ell").text());
					ingiLabel.setFont(new Font("나눔고딕코딩", Font.BOLD, 13));
					ingiLabel.setForeground(Color.WHITE);
					ingiPanel.add(ingiLabel);
				}
			}
			long time = System.currentTimeMillis();
			SimpleDateFormat dayTime = new SimpleDateFormat("hh:mm:ss");
			String str = dayTime.format(new Date(time));
			JLabel refreshLabel = new JLabel(str + "에 갱신됨");
			refreshLabel.setFont(new Font("나눔고딕코딩", 0, 12));
			refreshLabel.setForeground(Color.WHITE);
			ingiPanel.add(refreshLabel);
			this.repaint();
			this.revalidate();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	class LogoCanvas extends Canvas {
		Image img;

		public void setImage(Image img) {
			this.img = img;
		}

		public void paint(Graphics g) {
			g.clearRect(0, 0, getWidth(), getHeight());
			g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
		}

		public void update(Graphics g) {
			paint(g);
		}
	}
	

}
