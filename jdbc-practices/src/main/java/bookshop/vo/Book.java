package bookshop.vo;

public class Book {

	private int no;
	private String title;
	private String author;
	private int stateCode;

	public Book(int no, String title, String author) {
		this.no = no;
		this.title = title;
		this.author = author;
		this.stateCode = 1;
	}

	public int getNo() {
		return this.no;
	}

	public void rent() {
		if (this.stateCode == 0) {
			System.out.println(this.title + "는 이미 대여된 책입니다.");
			return;
		}

		this.stateCode = 0;
		System.out.println(this.title + "이(가) 대여 됐습니다.");
	}

	public void print() {
		System.out.printf("책 제목:%s, 작가:%s, 대여 유무:%s %n", this.title, this.author, this.stateCode == 1 ? "재고있음" : "대여중");
	}

}
