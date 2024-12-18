package bookmall.vo;

public class OrderBookVo {
	
	private int quantity;
	private int price;
	private Long orderNo;
	private Long bookNo;
	private String bookTitle;
		
	public int getQuantity() {
		return quantity;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public int getPrice() {
		return price;
	}
	
	public void setPrice(int price) {
		this.price = price;
	}
	
	public Long getOrderNo() {
		return orderNo;
	}
	
	public void setOrderNo(Long orderNo) {
		this.orderNo = orderNo;
	}
	
	public Long getBookNo() {
		return bookNo;
	}
	
	public void setBookNo(Long bookNo) {
		this.bookNo = bookNo;
	}
	
	public String getBookTitle() {
		return bookTitle;
	}
	
	public void setBookTitle(String bookTitle) {
		this.bookTitle = bookTitle;
	}

}
