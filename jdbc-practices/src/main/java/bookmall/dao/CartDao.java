package bookmall.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bookmall.db.DBConnection;
import bookmall.vo.CartVo;

public class CartDao {

	private static Connection conn = null;

	public void insert(CartVo cartVo) {

		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = DBConnection.getConnection();

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

	public List<CartVo> findByUserNo(Long userNo) {

		List<CartVo> result = new ArrayList<>();
		try (Connection conn = DBConnection.getConnection();
				PreparedStatement pstmt = conn
						.prepareStatement("select a.no, a.quantity, a.price,"
					+ " b.title, b.no from cart a join book b on a.book_no = b.no where user_no = ?");

		) {
			pstmt.setLong(1, userNo);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Long no = rs.getLong(1);
				int quantity = rs.getInt(2);
				int price = rs.getInt(3);
				String bookTitle = rs.getString(4);
				Long bookNo = rs.getLong(5);
				
				CartVo cartVo = new CartVo();
				cartVo.setNo(no);
				cartVo.setQuantity(quantity);
				cartVo.setPrice(price);
				cartVo.setUserNo(userNo);
				cartVo.setBookNo(bookNo);
				cartVo.setBookTitle(bookTitle);
				result.add(cartVo);
			}
			rs.close();
		} catch (SQLException e) {
			System.out.println("error: " + e);
		}

		return result;
	}

	public void deleteByUserNoAndBookNo(Long userNo, Long bookNo) {
		try (
				Connection conn = DBConnection.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("delete "
						+ "from cart where user_no = ? and book_no = ?");
		) {
			pstmt.setLong(1, userNo);
			pstmt.setLong(2, bookNo);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println("error: " + e);
		}
	}

}
