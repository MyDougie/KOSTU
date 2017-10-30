package view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import controller.KOSTAController;
import model.dto.Board;
import model.dto.FreeBoard;
import model.dto.QABoard;
import model.dto.Reply;

public class BoardView extends JPanel implements ActionListener, MouseListener {

   String loginId = MainView.user.getStudentID();
   String loginUserName = MainView.user.getStudentName();
   
   int clickedBoardNo = 0; // 선택된 계시판의 번호 저장
   int index = 0; // 자유게시판이 클릭(0)되었는지 QA게시판이 클릭(1)되었는지 저장하는 변수
   int noticeCount = 0; //공지글의 갯수
   
   JButton freeBoard = new JButton("자유 게시판");
   JButton QnABoard = new JButton("Q&A 게시판");
   
   int [][] range = {
		   {1,13},
		   {14,26},
		   {27,39},
		   {40,52},
		   {53,65}
   };
   
   JPanel westPanel = new JPanel();
   
   JLabel NorthLabel = new JLabel("자유 게시판");
   JPanel subNorthPanel = new JPanel();
   JPanel subEastPanel = new JPanel();
   JPanel NorthPanel = new JPanel(new BorderLayout(0,10));
   JPanel CenterPanel = new JPanel();
   
   //for search
   JPanel SouthPanel = new JPanel();
   String [] comboName={"  ALL  ","  글 번호  ","  제목 ","  내용 ","  제목+내용 ","  작성자 "};
   
   JComboBox combo = new JComboBox(comboName);
   JTextField jtf = new JTextField(20);
   JButton search = new JButton("검색");
   JButton write = new JButton("글쓰기");
   
   
   String names [] = {"No","종류","        제목      ", "작성자", " 작성일 "};
   DefaultTableModel dtm = new DefaultTableModel(names, 0){
      public boolean isCellEditable(int row, int column) {
         return false;
      }
   };
   
   String namesComment[] = {"No"," 작성자 ", "            Comment            "   , "  작성일  "};
   DefaultTableModel dtmComment = new DefaultTableModel(namesComment, 0){
      public boolean isCellEditable(int row, int column) {
         return false;
      }
      
     
   };
   
   JTable jt = new JTable(dtm){
	     // private final Color grayColor1 = new Color(243, 243, 243);
	     // private final Color grayColor2 = new Color(207, 207, 207);
	      public Component prepareRenderer(TableCellRenderer tcr, int row, int column) {
	        Component c = super.prepareRenderer(tcr, row, column);
	        if(isRowSelected(row)) {
	          c.setForeground(getSelectionForeground());
	          c.setBackground(getSelectionBackground());
	        }else{
	          c.setForeground(getForeground());
	          if( row<noticeCount ){
	            c.setBackground(Color.yellow);
	          } else {
	           // c.setBackground((row%2==0)?grayColor1:grayColor2);
	        	  c.setBackground(Color.WHITE);
	          }
	        }
	        return c;
	      }
	    };
   //게시판
   JScrollPane jsp = new JScrollPane(jt, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
   
   JTable jt2 = new JTable(dtmComment); //댓글
   JScrollPane commentScroll = new JScrollPane(jt2);
   
   //글쓰기 패널
   CardLayout card = new CardLayout();
   String caption[] = {"게시판", "글쓰기", "게시판내용", "수정화면"};
   
   
   
   JPanel tablePanel = new JPanel();
   
   //페이징 패널
   JPanel pagePanel = new JPanel();
   JLabel prev = new JLabel(" ◁ ");
   JLabel next = new JLabel(" ▷ ");
   
   JLabel prevPrev = new JLabel("◀◀");
   JLabel page1 = new JLabel(" 1 ");
   JLabel page2 = new JLabel(" 2 ");
   JLabel page3 = new JLabel(" 3 ");
   JLabel page4 = new JLabel(" 4 ");
   JLabel page5 = new JLabel(" 5 ");
   JLabel nextNext = new JLabel("▶▶");
   
   JLabel beforePage = page1;//이전 페이지 저장
   JLabel currentPage = page1;//현재 페이지 저장
   
   //게시판패널은 JTable jt
   JPanel CenterPanelWrite = new JPanel(); //글쓰기 패널
   JPanel CenterPanelContent = new JPanel();// 글내용패널
   JPanel CenterPanelUpdate = new JPanel(); // 수정패널
   
   //글쓰기패널 나누기
   JPanel writeNorthP = new JPanel();
   JPanel writeCenterP = new JPanel();
   JPanel writeSouthP = new JPanel();
   //글내용패널 나누기
   JPanel contentNorthP = new JPanel();
   JPanel contentCenterP = new JPanel();
   JPanel contentSouthP = new JPanel();
   //글내용패널 중 댓글 관련 
   JTextField commentField = new JTextField();
   JButton commentButton = new JButton("댓글등록");
   JButton commentUpdate = new JButton("댓글수정");
   JButton commentDelete = new JButton("댓글삭제");
   
   //수정패널 나누기
   JPanel updateNorthP = new JPanel();
   JPanel updateCenterP = new JPanel();
   JPanel updateSouthP = new JPanel();
   
   //CenterPanelWrite : 글쓰기 패널
   JLabel titleLable = new JLabel(" 제목 ");
   JTextField titleField = new JTextField(40);
   JCheckBox noticeCheck = new JCheckBox("공지");
   JTextArea contentArea = new JTextArea(12,50);
   JScrollPane contentScroll = new JScrollPane(contentArea);
   JButton upLoadButton = new JButton("등록");
   
   //CenterPanelContent : 글내용 확인 패널
   JLabel titleLable2 = new JLabel(" 제목 ");
   JTextField titleField2 = new JTextField(40);
   JTextArea contentArea2 = new JTextArea(12,50 );
   JScrollPane contentScroll2 = new JScrollPane(contentArea2);
   JButton updateButton = new JButton("수정");
   JButton deleteButton = new JButton("삭제");
   JButton backButton = new JButton("뒤로");
   
   //CenterPanelUpdate : 수정화면
   JLabel titleLable3 = new JLabel(" 제목 ");
   JTextField titleField3 = new JTextField(40);
   JTextArea contentArea3 = new JTextArea(20, 50);
   JScrollPane contentScroll3 = new JScrollPane(contentArea3);
   JButton updateComplete = new JButton("수정완료");
   JButton updateCancle = new JButton("취소");
   
   
   public BoardView() {
      this.setLayout(new BorderLayout());
      jt.setRequestFocusEnabled(true);
      //Left - BoxLayout
      westPanel.setLayout(new BoxLayout(westPanel, BoxLayout.Y_AXIS));
      //westPanel.add(Box.createVerticalStrut(10));//버튼사이 간격
      westPanel.add(freeBoard);
      westPanel.add(Box.createVerticalStrut(20));
      westPanel.add(QnABoard);
      
      westPanel.add(Box.createHorizontalStrut(10));
      //westPanel.add(Box.createVerticalStrut(0));
      westPanel.add(backButton);
      westPanel.add(Box.createVerticalStrut(60));
      westPanel.add(commentUpdate);
      westPanel.add(Box.createVerticalStrut(10));
      westPanel.add(commentDelete);
      commentUpdate.setVisible(false);
      commentDelete.setVisible(false);
      
      //North - Label
      NorthLabel.setFont(new Font("돋움", Font.BOLD, 20));
      NorthLabel.setBorder(BorderFactory.createEmptyBorder(30 , 90 , 10 , 0));//라벨 상좌 하우 여백
      subNorthPanel.add(NorthLabel, "Center");
      subEastPanel.add(Box.createVerticalStrut(70));
      subEastPanel.add(deleteButton);
      subEastPanel.add(updateButton);
      
      subEastPanel.setVisible(false);
      backButton.setVisible(false);
      
      NorthPanel.add(new JPanel(), "West"); 
      NorthPanel.add(subNorthPanel, "Center");
      NorthPanel.add(subEastPanel, "East");
      //NorthPanel.add(okButton, "East");
      
      //Center - Table
      //call controller for initial data
      List<Vector<Object>> list = KOSTAController.searchBoardByPage(index, range[0][0], range[0][1]);
	  loadData(list);
      
      
      contentArea.setLineWrap(true);
      ///////////////////////////////////////////////////////////////////////////////////////////////////////   
      //CenterPanelWrite - 글쓰기
      
      //1)최대화했을 때 같이 늘어나게하기
      CenterPanelWrite.setLayout(new BorderLayout());
      CenterPanelWrite.add(writeNorthP, "North");
      CenterPanelWrite.add(writeCenterP, "Center");
      CenterPanelWrite.add(writeSouthP, "South");
      
      writeNorthP.setLayout(new BorderLayout());
      writeNorthP.add(titleLable,"West");
      writeNorthP.add(titleField, "Center");
      writeNorthP.add(noticeCheck,"East");
      
      writeCenterP.setLayout(new BorderLayout());
      writeCenterP.add(contentScroll, "Center");
      writeSouthP.add(upLoadButton); 
      
      jt2.setPreferredScrollableViewportSize(new Dimension(80,100));//table크기조절 


      //1)최대화했을 때 같이 늘어나게하기
      CenterPanelContent.setLayout(new BorderLayout(20, 20));
      CenterPanelContent.add(contentNorthP, "North");
      CenterPanelContent.add(contentCenterP, "Center");
      CenterPanelContent.add(contentSouthP, "South");
      
      contentNorthP.setLayout(new BorderLayout());
      contentNorthP.add(titleLable2,"West");
      contentNorthP.add(titleField2, "Center");
      
      contentCenterP.setLayout(new BorderLayout());
      contentCenterP.add(contentScroll2, "Center");

      contentSouthP.setLayout(new BorderLayout(20 , 20));
      contentSouthP.add(commentField, "Center");
      contentSouthP.add(commentButton, "East");
      contentSouthP.add(commentScroll, "South"); 
 
      
      //1)최대화했을 때 같이 늘어나게하기
      CenterPanelUpdate.setLayout(new BorderLayout());
      CenterPanelUpdate.add(updateNorthP, "North");
      CenterPanelUpdate.add(updateCenterP, "Center");
      CenterPanelUpdate.add(updateSouthP, "South");
      
      updateNorthP.setLayout(new BorderLayout());
      updateNorthP.add(titleLable3,"West");
      updateNorthP.add(titleField3, "Center");
      
      updateCenterP.setLayout(new BorderLayout());
      updateCenterP.add(contentScroll3, "Center");
      updateSouthP.add(updateCancle);
      updateSouthP.add(updateComplete); 
//////////////////////////////////////////////////////////////////////////////
      
      tablePanel.setLayout(new BorderLayout());
      tablePanel.add(jsp, "Center");
      tablePanel.add(pagePanel, "South");
      pagePanel.add(prevPrev);
      pagePanel.add(Box.createHorizontalStrut(1));
      pagePanel.add(prev);
      pagePanel.add(Box.createHorizontalStrut(3));
      pagePanel.add(page1);
      pagePanel.add(page2);
      pagePanel.add(page3);
      pagePanel.add(page4);
      pagePanel.add(page5);
      pagePanel.add(Box.createHorizontalStrut(3));
      pagePanel.add(next);
      pagePanel.add(Box.createHorizontalStrut(1));
      pagePanel.add(nextNext);
      
      page1.setFont(new Font("돋움", Font.BOLD, 14));
      page1.setForeground(Color.MAGENTA);
      page2.setFont(new Font("돋움", Font.BOLD, 14));
      page3.setFont(new Font("돋움", Font.BOLD, 14));
      page4.setFont(new Font("돋움", Font.BOLD, 14));
      page5.setFont(new Font("돋움", Font.BOLD, 14));
      
//////////////////////////////////////////////////////////////////////////////      
            
      CenterPanel.setLayout(card);
      CenterPanel.add(caption[0], tablePanel);
      CenterPanel.add(caption[1], CenterPanelWrite);
      CenterPanel.add(caption[2], CenterPanelContent);
      CenterPanel.add(caption[3], CenterPanelUpdate);
      
      //처음에 게시판 패널보이기 
      card.show(CenterPanel, caption[0]);
      
      //컬럼 고정
      jt.getTableHeader().setReorderingAllowed(false);
      jt2.getTableHeader().setReorderingAllowed(false);
      
      //셀 너비 조절
      jt.setSize(200, 300);
      TableColumn column = null;
      int headerWidth = 0;
      int font = 23;

      for (int i = 0, n = dtm.getColumnCount(); i < n; i++) {
         column = jt.getColumnModel().getColumn(i);
         try{
            headerWidth = column.getHeaderValue().toString().length() * font;
            column.setPreferredWidth(headerWidth);
         }catch (Exception e) {
            e.printStackTrace();
         }
      }
      
      for (int i = 0, n = dtmComment.getColumnCount(); i < n; i++) {
         column = jt2.getColumnModel().getColumn(i);
         try{
            headerWidth = column.getHeaderValue().toString().length() * font;
            column.setPreferredWidth(headerWidth);
         }catch (Exception e) {
            e.printStackTrace();
         }
      }

      //셀 높이 조절
      jt.setRowHeight(30);
      jt2.setRowHeight(20);
      
      // DefaultTableCellHeaderRenderer 생성 (가운데 정렬을 위한)
      DefaultTableCellRenderer tScheduleCellRenderer = new DefaultTableCellRenderer();
       
      // DefaultTableCellHeaderRenderer의 정렬을 가운데 정렬로 지정
      tScheduleCellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
       
      // 정렬할 테이블의 ColumnModel을 가져옴
      TableColumnModel tcmSchedule = jt.getColumnModel();
      TableColumnModel tcmSchedule2 = jt2.getColumnModel();
       
      // 반복문을 이용하여 테이블을 가운데 정렬로 지정
      for (int i = 0; i < tcmSchedule.getColumnCount(); i++) {
         tcmSchedule.getColumn(i).setCellRenderer(tScheduleCellRenderer);
      }
      
    //  tcmSchedule
    
      tcmSchedule2.getColumn(0).setCellRenderer(tScheduleCellRenderer);
      tcmSchedule2.getColumn(1).setCellRenderer(tScheduleCellRenderer);
      tcmSchedule2.getColumn(3).setCellRenderer(tScheduleCellRenderer);
      
      
      SouthPanel.add(combo);
      SouthPanel.add(jtf);
      SouthPanel.add(search);
      SouthPanel.add(write);
      SouthPanel.setBorder(BorderFactory.createEmptyBorder(0 , 0 , 10 , 10));//패널 상하 좌우 여백
            
      this.add(westPanel, "West");
      this.add(NorthPanel, "North");
      this.add(CenterPanel, "Center");
      this.add(SouthPanel, "South");
      
      
      
      //게시글 보기(더블클릭)
      jt.addMouseListener(new MouseAdapter() {
            @Override 
            public void mouseClicked(MouseEvent e) {
                JTable t = (JTable)e.getSource();
              //  List<Vector<Object>> list = null;
                                        
                if(e.getClickCount()==2) {
                    TableModel m = t.getModel();
                    Point pt = e.getPoint();
                    int i = t.rowAtPoint(pt);
                    if(i>=0) {
                        int row = t.convertRowIndexToModel(i);
                        clickedBoardNo = Integer.parseInt(m.getValueAt(row, 0)+"");
                        titleField2.setText(String.format("%s", m.getValueAt(row, 2)));
                        contentArea2.setText(KOSTAController.getContent(clickedBoardNo, index));
                        contentArea2.setLineWrap(true);
                        titleField2.setEditable(false);
                        contentArea2.setEditable(false);
                        SouthPanel.setVisible(false);
                        card.show(CenterPanel, caption[2]);
                        
                        subEastPanel.setVisible(true);
                        backButton.setVisible(true);
                        commentUpdate.setVisible(true);
                        commentDelete.setVisible(true);
                        
                        List<Vector<Object>> list = KOSTAController.searchAllReply(index, clickedBoardNo);
                        loadReply(list);
                                          
                    }
                }
            }
        });
      
      
      //이벤트등록
      freeBoard.addActionListener(this);
      QnABoard.addActionListener(this);
      search.addActionListener(this);
      write.addActionListener(this);
      upLoadButton.addActionListener(this);
      backButton.addActionListener(this);
      updateButton.addActionListener(this);
      updateComplete.addActionListener(this);
      updateCancle.addActionListener(this);
      deleteButton.addActionListener(this);
      //
      commentButton.addActionListener(this);
      commentDelete.addActionListener(this);
      commentUpdate.addActionListener(this);
      
     page1.addMouseListener(this);
     page2.addMouseListener(this);
     page3.addMouseListener(this);
     page4.addMouseListener(this);
     page5.addMouseListener(this);
     prev.addMouseListener(this);
     prevPrev.addMouseListener(this);
     next.addMouseListener(this);
     nextNext.addMouseListener(this);
      
   }


   @Override
   public void actionPerformed(ActionEvent e) {
      Object eventTarget = e.getSource();
      Object target = e.getActionCommand();
      
      if(eventTarget == freeBoard){
         NorthLabel.setText("자유 게시판");
         SouthPanel.setVisible(true);
         card.show(CenterPanel, caption[0]);
         
         index = 0;
         List<Vector<Object>> list = KOSTAController.searchBoardByPage(index, range[0][0], range[0][1]);
   	  	 loadData(list);
   	  	 
   	  	 setPageColor(page1);
         
         commentUpdate.setVisible(false);
         commentDelete.setVisible(false);
         subEastPanel.setVisible(false);
         backButton.setVisible(false);
         
         
      }else if(eventTarget == QnABoard){
         NorthLabel.setText("Q&A 게시판");
         SouthPanel.setVisible(true);
         card.show(CenterPanel, caption[0]);
         
         
         index = 1;
         List<Vector<Object>> list = KOSTAController.searchBoardByPage(index, range[0][0], range[0][1]);
   	  	 loadData(list);
         
         commentUpdate.setVisible(false);
         commentDelete.setVisible(false);
         subEastPanel.setVisible(false);
         backButton.setVisible(false);
         
         setPageColor(page1);
         
      }else if(eventTarget == search){
         card.show(CenterPanel, caption[0]);
         if(jtf.getText().trim().equals("")){
            FailView.errorMessage("검색단어를 입력하세요.");
            jtf.setText("");
            jtf.requestFocus();
            return;
         }
         
         List<Vector<Object>> list = null;
         if(index == 0){
            list = KOSTAController.getUserSearchFree(combo.getSelectedItem().toString().trim(), jtf.getText());
         }
         else if (index == 1){
            list = KOSTAController.getUserSearchQA(combo.getSelectedItem().toString().trim(), jtf.getText());
         }
         
         if(list != null && list.size() != 0){
            this.loadData(list);
         }

      }
      // 글쓰기 버튼
      else if (eventTarget == write) {
         SouthPanel.setVisible(false);
         titleField.setText("");
         contentArea.setText("");
         backButton.setVisible(true);
         card.show(CenterPanel, caption[1]);

         if (index == 0)
            noticeCheck.setVisible(true);
         else if (index == 1)
            noticeCheck.setVisible(false);
      }

      // 글올리기 버튼
      else if (eventTarget == upLoadButton) {
         int result = 0;
         int category = 1;
         
         if (index == 0) {
            if (isValidate()) {
               if (noticeCheck.isSelected())
                  category = 0;
               else
                  category = 1;

               Board fb = new FreeBoard(category, loginId, loginUserName, titleField.getText(), contentArea.getText());

               result = KOSTAController.insertBoard(fb, index);

               if (result > 0) {
                  titleField.setText("");
                  contentArea.setText("");

                  // 게시판 새로고침
                  List<Vector<Object>> list = KOSTAController.searchBoardByPage(index, range[0][0], range[0][1]);
            	  loadData(list);
                  SouthPanel.setVisible(true);
                  backButton.setVisible(false);
                  card.show(CenterPanel, caption[0]);
               }
            }
         } else if (index == 1) {
            if (isValidate()) {
               Board qb = new QABoard(loginId, loginUserName, titleField.getText(), contentArea.getText());
               result = KOSTAController.insertBoard(qb, index);
               if (result > 0) {
                  titleField.setText("");
                  contentArea.setText("");

                  // 게시판 새로고침
                  List<Vector<Object>> list = KOSTAController.searchBoardByPage(index, range[0][0], range[0][1]);
            	  loadData(list);
                  SouthPanel.setVisible(true);
                  backButton.setVisible(false);
                  card.show(CenterPanel, caption[0]);
               }
            }
         }

         

      }
      // 게시글 내용에서 뒤로버튼
      else if (eventTarget == backButton) {
         SouthPanel.setVisible(true);
         card.show(CenterPanel, caption[0]);
         
       
        int page = getCurrentPage(currentPage);
		List<Vector<Object>> list = KOSTAController.searchBoardByPage(index, range[page-1][0], range[page-1][1]);
   	    loadData(list);
         
         subEastPanel.setVisible(false);
         backButton.setVisible(false);
         commentUpdate.setVisible(false);
         commentDelete.setVisible(false);
         
         
      // 게시글 내용에서 수정버튼
      } else if (eventTarget == updateButton) {
         // 해당게시물을 로그인한 유저가 쓴게 맞으면 update를 실행하도록 한다.
         if (KOSTAController.isYours(loginId, clickedBoardNo, index)) {
            titleField3.setText(titleField2.getText());
            contentArea3.setText(contentArea2.getText());
            card.show(CenterPanel, caption[3]);
            contentArea3.setLineWrap(true);
            commentUpdate.setVisible(false);
            commentDelete.setVisible(false);
            backButton.setVisible(true);
         }else{
        	 return;
         }
         
      }else if(eventTarget == updateCancle){
         card.show(CenterPanel, caption[2]);
      }else if(eventTarget == updateComplete){
         if(KOSTAController.isYours(loginId, clickedBoardNo, index)){
            if (index == 0) {
               KOSTAController.updateFreeBoard(
                     new FreeBoard(clickedBoardNo, loginId, titleField3.getText(), contentArea3.getText()));
            } else if (index == 1) {
               KOSTAController.updateQABoard(
                     new QABoard(clickedBoardNo, loginId, titleField3.getText(), contentArea3.getText()));
            }
            SuccessView.successMessage("게시물이 수정되었습니다.");
            card.show(CenterPanel, caption[2]);
            titleField2.setText(titleField3.getText());
            contentArea2.setText(contentArea3.getText());
         }else{
        	 card.show(CenterPanel, caption[2]);
         }
      } else if (eventTarget == deleteButton) {

         //해당게시물을 로그인한 유저가 쓴게 맞으면 delete를 실행하도록 한다.
         
         //0 - 삭제, 1 - 취소
         if (KOSTAController.isYours(loginId, clickedBoardNo, index)) {
        	 System.out.println("a");
            int re = JOptionPane.showConfirmDialog(this, "정말 삭제하시겠습니까?", "게시물 삭제", JOptionPane.YES_NO_OPTION,
                  JOptionPane.WARNING_MESSAGE, null);
            if (re == 0) {
               KOSTAController.deleteBoard(clickedBoardNo, index);
               card.show(CenterPanel, caption[0]);
               SouthPanel.setVisible(true);
               List<Vector<Object>> list = KOSTAController.searchBoardByPage(index, range[0][0], range[0][1]);
         	   loadData(list);
               SuccessView.successMessage("게시물이 삭제되었습니다.");
               subEastPanel.setVisible(false);
               backButton.setVisible(false);
               commentUpdate.setVisible(false);
               commentDelete.setVisible(false);
            }
         }else{
        	 //FailView.errorMessage("작성자만 삭제할 수 있습니다.");
         }
      } else if (eventTarget == commentButton){
			if (commentField.getText().trim().equals("")) {
				FailView.errorMessage("댓글 내용을 입력해주세요.");
				commentField.requestFocus();
				return;
			}

			int re = KOSTAController.insertComment(new Reply(clickedBoardNo, loginUserName, loginId, commentField.getText()), index);
			if (re > 0) {
				List<Vector<Object>> list = KOSTAController.searchAllReply(index, clickedBoardNo);
				loadReply(list);
				commentField.setText("");
			}
          
    	  
	  //댓글 삭제
      }else if(eventTarget == commentDelete){
    	  if(jt2.getSelectedRow() == -1){
    		  FailView.errorMessage("삭제할 댓글이 없습니다.");
    		  return;
    	  }
    	  int replyNo = (int)jt2.getValueAt(jt2.getSelectedRow(), 0);
    	  
    	  if(KOSTAController.isYourReply(loginId, replyNo, index)){//로그인한 회원의 댓글이면 
    		  int re = JOptionPane.showConfirmDialog(this, "정말 삭제하시겠습니까?", "댓글 삭제", JOptionPane.YES_NO_OPTION,
                      JOptionPane.WARNING_MESSAGE, null);
    		 
                if (re == 0) {
                   int re2 = KOSTAController.deleteReply(replyNo, index);
					// card.show(CenterPanel, caption[0]);
					if (re2 > 0) {
						SuccessView.successMessage("댓글이 삭제되었습니다.");
						List<Vector<Object>> list = null;
						if (index == 0)
							list = KOSTAController.searchAllReply(0, clickedBoardNo);
						else if (index == 1)
							list = KOSTAController.searchAllReply(1, clickedBoardNo);
						loadReply(list);
					}
				}
    	  }else{
    		  //FailView.errorMessage("댓글 작성한 회원만 삭제 가능합니다.");
    	  }
    	
    	  
      }else if(eventTarget == commentUpdate){
    	  if(jt2.getSelectedRow() == -1){
    		  FailView.errorMessage("수정할 댓글이 없습니다.");
    		  return;
    	  }
    	  int replyNo = (int)jt2.getValueAt(jt2.getSelectedRow(), 0);
    	  if(KOSTAController.isYourReply(loginId, replyNo, index)){//로그인한 회원의 댓글이면 
    		  
    		 //showInputDialog에서 수정할 문구 입력
    		 String updateComment = (String) JOptionPane.showInputDialog(this, "댓글을 수정하세요", "댓글 수정", 
    				  JOptionPane.OK_CANCEL_OPTION, null, null, jt2.getValueAt(jt2.getSelectedRow(), 2));
 		  
    		  //수정완료누르면?
    		 	if(updateComment != null){
                   int re = KOSTAController.updateReply(replyNo, updateComment ,index);
					// card.show(CenterPanel, caption[0]);
					if (re > 0) {
						SuccessView.successMessage("댓글이 수정되었습니다.");
						List<Vector<Object>> list = null;
						if (index == 0)
							list = KOSTAController.searchAllReply(0, clickedBoardNo);
						else if (index == 1)
							list = KOSTAController.searchAllReply(1, clickedBoardNo);
						loadReply(list);
					}
				}
    	  }else{
    		 // FailView.errorMessage("댓글 작성한 회원만 수정 가능합니다.");
    	  }
      }

   }
   
   
      
   
   public void loadData(List<Vector<Object>> list) {      
         dtm.setRowCount(0);// 레코드 비우기
         
         if (list != null && list.size() != 0) {
         // 레코드 추가
         noticeCount = 0;
         for (Vector<Object> v : list){
            dtm.addRow(v);            
            if(v.contains("[공지]")){
            	noticeCount++;
            }
         }
         
         jt.setRowSelectionInterval(0, 0);
      }
   }  
   
   public void loadReply(List<Vector<Object>> list) {      
       dtmComment.setRowCount(0);// 레코드 비우기
       
       if (list != null && list.size() != 0) {
       // 레코드 추가
       for (Vector<Object> v : list){
    	   dtmComment.addRow(v);
       }

       jt2.setRowSelectionInterval(0, 0);
    }
 }
   
   
   //글작성시 유효성검사
   //true : 성공 , false : 실패
   
   public boolean isValidate(){
      //제목 체크
      if(titleField.getText().trim().equals("")){
         FailView.errorMessage("제목을 입력해주세요.");
         card.show(CenterPanel, caption[1]);
         titleField.requestFocus();
         return false;
      }
      //내용 체크
      if(contentArea.getText().trim().equals("")){
         FailView.errorMessage("내용을 입력해주세요.");
         card.show(CenterPanel, caption[1]);
         contentArea.requestFocus();
         return false;
      }    
      if(titleField.getText().trim().length()>23){
          FailView.errorMessage("제목은 23자 내외로 작성해주세요.");
          card.show(CenterPanel, caption[1]);
          titleField.requestFocus();
          return false;
       }
      return true;
   }

	@Override
	public void mouseClicked(MouseEvent e) {
		
	
	}
	public JLabel getPrev(JLabel currentPage){
		JLabel prevPage = null;
		if(currentPage == page1)
			return null;
		else if(currentPage == page2)
			prevPage = page1;
		else if(currentPage == page3)
			prevPage = page2;
		else if(currentPage == page4)
			prevPage = page3;
		else if(currentPage == page5)
			prevPage = page4;
		
		return prevPage;
	}
	
	public JLabel getNext(JLabel currentPage){
		JLabel nextPage = null;
		if(currentPage == page1)
			return page2;
		else if(currentPage == page2)
			nextPage = page3;
		else if(currentPage == page3)
			nextPage = page4;
		else if(currentPage == page4)
			nextPage = page5;
		else if(currentPage == page5)
			return null;
		
		return nextPage;
	}
	
	public int getCurrentPage(JLabel currentPage){
		int re = 1;
		if(currentPage == page1)
			re = 1;
		else if(currentPage == page2)
			re = 2;
		else if(currentPage == page3)
			re = 3;
		else if(currentPage == page4)
			re = 4;
		else if(currentPage == page5)
			re = 5;
		
		return re;
	}
	
	public void setPageColor(JLabel currentPage){
		int re = getCurrentPage(currentPage);
		if(re == 1){
			page1.setForeground(Color.magenta);
			page2.setForeground(Color.black);
			page3.setForeground(Color.black);
			page4.setForeground(Color.black);
			page5.setForeground(Color.black);
		}else if(re == 2){
			page2.setForeground(Color.magenta);
			page1.setForeground(Color.black);
			page3.setForeground(Color.black);
			page4.setForeground(Color.black);
			page5.setForeground(Color.black);
		}else if(re == 3){
			page3.setForeground(Color.magenta);
			page1.setForeground(Color.black);
			page2.setForeground(Color.black);
			page4.setForeground(Color.black);
			page5.setForeground(Color.black);
		}else if(re == 4){
			page4.setForeground(Color.magenta);
			page1.setForeground(Color.black);
			page2.setForeground(Color.black);
			page3.setForeground(Color.black);
			page5.setForeground(Color.black);
		}else if(re == 5){
			page5.setForeground(Color.magenta);
			page1.setForeground(Color.black);
			page2.setForeground(Color.black);
			page3.setForeground(Color.black);
			page4.setForeground(Color.black);
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		Object event = e.getSource();
		if(event == page1){
			
			currentPage = page1;
			//라벨 색깔 바꿔주고
			
			setPageColor(currentPage);
			
			//선택된 페이지 보여주고
			List<Vector<Object>> list = KOSTAController.searchBoardByPage(index, range[0][0], range[0][1]);
			loadData(list);
			
			
			//현재 페이지 저장하고
			beforePage = currentPage;
		}else if(event == page2){
			currentPage = page2;
			
			setPageColor(currentPage);
			
			List<Vector<Object>> list = KOSTAController.searchBoardByPage(index, range[1][0], range[1][1]);
			loadData(list);
			
			
			beforePage = currentPage;
		}else if(event == page3){
			currentPage = page3;
			
			setPageColor(currentPage);
			
			List<Vector<Object>> list = KOSTAController.searchBoardByPage(index, range[2][0], range[2][1]);
			loadData(list);
			
			beforePage = currentPage;
		}else if(event == page4){
			currentPage = page4;
			
			setPageColor(currentPage);
			
			List<Vector<Object>> list = KOSTAController.searchBoardByPage(index, range[3][0], range[3][1]);
			loadData(list);
			
			
			
			beforePage = currentPage;
		}else if(event == page5){
			currentPage = page5;
			
			setPageColor(currentPage);

			List<Vector<Object>> list = KOSTAController.searchBoardByPage(index, range[4][0], range[4][1]);
			loadData(list);
			
			beforePage = currentPage;
		}
		
		
		else if(event == prevPrev){
			currentPage = page1;
			setPageColor(currentPage);
			// 1 페이지 보여주고
			List<Vector<Object>> list = KOSTAController.searchBoardByPage(index, range[0][0], range[0][1]);
      	    loadData(list);
			
			// 현재 페이지 저장하고
			
			beforePage = currentPage;
		}else if(event == prev){
			JLabel prevPage = getPrev(currentPage); 
			if(prevPage != null){
			//라벨 색깔 바꿔주고
				setPageColor(prevPage);
			// 현재페이지 - 1 에 해당하는 페이지 보여주고 (예외처리)
				
				int page = getCurrentPage(prevPage);
				List<Vector<Object>> list = KOSTAController.searchBoardByPage(index, range[page-1][0], range[page-1][1]);
	      	    loadData(list);
				
			// 현재페이지 저장
				currentPage = prevPage;//4
				
			}
			
			
			
		}else if(event == next){
			
			JLabel nextPage = getNext(currentPage); 
			if(nextPage != null){
			//라벨 색깔 바꿔주고
				setPageColor(nextPage);
			// 현재페이지 - 1 에 해당하는 페이지 보여주고 (예외처리)
				int page = getCurrentPage(nextPage);
				List<Vector<Object>> list = KOSTAController.searchBoardByPage(index, range[page-1][0], range[page-1][1]);
	      	    loadData(list);
				
			// 현재페이지 저장
				currentPage = nextPage;//4
				
			}
			
			
			
		}else if(event == nextNext){
			currentPage = page5;
			
			setPageColor(currentPage);
			
			List<Vector<Object>> list = KOSTAController.searchBoardByPage(index, range[4][0], range[4][1]);
      	    loadData(list);
		}
	}
	@Override
	public void mouseReleased(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}

}
