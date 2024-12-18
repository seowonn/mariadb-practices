package bookmall.vo;

import bookmall.dao.CartDao;

public class CartVo {

	private int quantity;
	private int price;
	private Long userNo;
	private Long bookNo;
	private String bookTitle;

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setPrice(int price) {
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

	public String getBookTitle() {
		return bookTitle;
	}
	
	public void setBookTitle(String bookTitle) {
		this.bookTitle = bookTitle;
	}

}
