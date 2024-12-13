package bookmall.vo;

public class BookVo {
	
	private Long no;
	private String bookTitle;
	private int price;
	private Long categoryNo;
	
	public BookVo(String bookTitle, int price) {
		this.bookTitle = bookTitle;
		this.price = price;
	}

	public Long getNo() {
		return no;
	}
	
	public void setNo(Long no) {
		this.no = no;
	}
	
	public String getBookTitle() {
		return bookTitle;
	}
	
	public void setBookTitle(String bookTitle) {
		this.bookTitle = bookTitle;
	}
	
	public int getPrice() {
		return price;
	}
	
	public void setPrice(int price) {
		this.price = price;
	}
	
	public Long getCategoryNo() {
		return categoryNo;
	}
	
	public void setCategoryNo(Long categoryNo) {
		this.categoryNo = categoryNo;
	}	

}
