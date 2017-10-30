package model.dto;
/**
 * 질문/답변 게시판
 * 기본 게시판 틀에서  질문/답변 게시판만의 
 * 특별하게 추가될 속성이 있는지 생각해서 추가 바람.
 */
public class QABoard extends Board {

	public QABoard(int number, String writer, String title, String content, String date) {
		super(number, writer, title, content, date);
	}

	public QABoard(String writerId, String writer, String title, String content) {
		super(writerId, writer, title, content);
	}
	
	public QABoard(int boardNo, String writerId, 
			String title, String content) {
		super(boardNo, writerId, title, content);
	}

}
