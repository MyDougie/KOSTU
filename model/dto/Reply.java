package model.dto;

public class Reply {
	private int replyNo;
	private int boardNo;
	private String writer;
	private String writerId;
	private String content;
	private String writeDate;
	
	public Reply(){}

	public Reply(int replyNo, int boardNo, String writer, String writerId, String content, String writeDate) {
		super();
		this.replyNo = replyNo;
		this.boardNo = boardNo;
		this.writer = writer;
		this.writerId = writerId;
		this.content = content;
		this.writeDate = writeDate;
	}

	public Reply(int boardNo, String writer, String writerId, String content) {
		this.boardNo = boardNo;
		this.writer = writer;
		this.writerId = writerId;
		this.content = content;
	}

	public int getReplyNo() {
		return replyNo;
	}

	public void setReplyNo(int replyNo) {
		this.replyNo = replyNo;
	}

	public int getBoardNo() {
		return boardNo;
	}

	public void setBoard_no(int boardNo) {
		this.boardNo = boardNo;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public String getWriterId() {
		return writerId;
	}

	public void setWriterId(String writerId) {
		this.writerId = writerId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getWriteDate() {
		return writeDate;
	}

	public void setWriteDate(String writeDate) {
		this.writeDate = writeDate;
	}
	
	
}
