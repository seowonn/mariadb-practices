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

	public void insert(CartVo cartVo) {
		
		try (Connection conn = DBConnection.getConnection();
				PreparedStatement pstmt1 = conn.prepareStatement(
						"insert into cart (quantity, price, user_no, book_no) values(?, ?, ?, ?)");
				PreparedStatement pstmt2 = conn.prepareStatement(
						"select last_insert_id() from dual");
		){
			pstmt1.setInt(1, cartVo.getQuantity());
			pstmt1.setInt(2, cartVo.getPrice());
			pstmt1.setLong(3, cartVo.getUserNo());
			pstmt1.setLong(4, cartVo.getBookNo());
			pstmt1.executeUpdate();

			ResultSet rs = pstmt2.executeQuery();
			cartVo.setNo(rs.next() ? rs.getLong(1) : null);
			rs.close();
		} catch (SQLException e) {
			System.out.println("error: " + e);
		}

	}

	public List<CartVo> findByUserNo(Long userNo) {

		List<CartVo> result = new ArrayList<>();
		try (Connection conn = DBConnection.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(
						"select a.no, a.quantity, a.price, b.title, b.no"
						+ " from cart a join book b on a.book_no = b.no"
						+ " where user_no = ?");
		) {
			pstmt.setLong(1, userNo);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				CartVo cartVo = new CartVo();
				cartVo.setNo(rs.getLong(1));
				cartVo.setQuantity(rs.getInt(2));
				cartVo.setPrice(rs.getInt(3));
				cartVo.setBookTitle(rs.getString(4));
				cartVo.setBookNo(rs.getLong(5));
				cartVo.setUserNo(userNo);
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
				PreparedStatement pstmt = conn.prepareStatement(
						"delete from cart where user_no = ? and book_no = ?");
		) {
			pstmt.setLong(1, userNo);
			pstmt.setLong(2, bookNo);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("error: " + e);
		}
	}

}
