package bookmall.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bookmall.db.dbConn;
import bookmall.vo.BookVo;
import bookmall.vo.CategoryVo;

public class BookDao {

	private static Connection conn = null;

	public void insert(BookVo bookVo) {
		
		Long lastId = 0L;
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = dbConn.getConnection();

			String sql = "insert into book (book_title, price, category_no) values(?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, bookVo.getBookTitle());
			pstmt.setInt(2, bookVo.getPrice());
			pstmt.setLong(3, bookVo.getCategoryNo());

			pstmt.executeUpdate();
	        
		} catch (SQLException e) {
			System.out.println("error: " + e);
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("error: " + e);
			}
		}

	}

}
