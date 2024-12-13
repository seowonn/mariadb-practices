package bookmall.vo;

public class OrderVo {
	
	private Long no;
	private String number;
	private int payment;
	private String shipping;
	private String status;
	private Long userNo;
	
	public Long getNo() {
		return no;
	}
	
	public void setNumber(String number) {
		this.number = number;
	}
	
	public void setPayment(int payment) {
		this.payment = payment;
	}
	
	public void setShipping(String shipping) {
		this.shipping = shipping;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public void setUserNo(Long userNo) {
		this.userNo = userNo;
	}

}
