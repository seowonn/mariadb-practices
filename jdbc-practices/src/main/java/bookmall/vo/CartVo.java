package bookmall.vo;

public class CartVo {
	
	private int quantity;
	private int price;
	private Long userNo;
	private Long bookNo;
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public void setPriced(int price) {
		this.price = price;
	}
	
	public int getPrice() {
		return price;
	}
	
	public void setUserNo(Long userNo) {
		this.userNo = userNo;
	}
	
	public Long getUserNo() {
		return userNo;
	}
	
	public void setBookNo(Long bookNo) {
		this.bookNo = bookNo;
	}
	
	public Long getBookNo() {
		return bookNo;
	}

}
