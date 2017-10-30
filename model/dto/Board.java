package model.dto;
/**
 * 게시판의 기본 틀이 되는 추상클래스
 */
public abstract class Board {
	/**
	 * number = 게시판 번호
	 * writer = 작성자
	 * title = 제목
	 * content = 내용
	 * date = 날짜
	 */
	private int number;
	private String writerId;
	private String writer;
	private String title;
	private String content;
	private String date;

	public Board(int number, String writer, String title, String content, String date) {
		super();
		this.number = number;
		this.writer = writer;
		this.title = title;
		this.content = content;
		this.date = date;
	}
	
	public Board(String writerId, String writer, 
			String title, String content) {
		this.writerId = writerId;
		this.writer = writer;
		this.title = title;
		this.content = content;
	}
	
	public Board(int boardNo, String writerId, 
			String title, String content) {
		this.number = boardNo;
		this.writerId = writerId;
		this.title = title;
		this.content = content;
	}
	
	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getWriterId() {
		return writerId;
	}

	public void setWriterId(String writerId) {
		this.writerId = writerId;
	}
	

}
