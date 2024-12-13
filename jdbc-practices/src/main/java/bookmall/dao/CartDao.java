package bookmall.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import bookmall.db.dbConn;
import bookmall.vo.CartVo;

public class CartDao {

	private static Connection conn = null;

	public void insert(CartVo cartVo) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = dbConn.getConnection();

			String sql = "insert into cart (quantity, price, user_no, book_no) values(?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, cartVo.getQuantity());
			pstmt.setInt(2, cartVo.getPrice());
			pstmt.setLong(3, cartVo.getUserNo());
			pstmt.setLong(4, cartVo.getBookNo());

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
