package bookmall.vo;

public class CategoryVo {

	private Long no;
	private String categoryType;
	
	public CategoryVo(String categoryType) {
		this.categoryType = categoryType;
	}

	public Long getNo() {
		return no;
	}
	
	public void setNo(Long no) {
		this.no = no;
	}
	
	public String getCategoryType() {
		return categoryType;
	}
	
	public void setCategoryType(String categoryType) {
		this.categoryType = categoryType;
	}
	
}
