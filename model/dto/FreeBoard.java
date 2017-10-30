package model.dto;

/**
 * 자유게시판
 */
public class FreeBoard extends Board {
	/**
	 * 기본 게시판 틀에 카테고리라는 속성이 추가됨(공지 or 일반글)
	 */
	private int category;

	public FreeBoard(int category, int number, String writer, 
			String title, String content, String date) {
		super(number, writer, title, content, date);
		this.category = category;
	}
	
	
	
	public FreeBoard(int category, String writerId, String writer, 
			String title, String content) {
		super(writerId, writer, title, content);
		this.category = category;
	}
	
	public FreeBoard(int boardNo, String writerId, 
			String title, String content) {
		super(boardNo, writerId, title, content);
	}


	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

}
