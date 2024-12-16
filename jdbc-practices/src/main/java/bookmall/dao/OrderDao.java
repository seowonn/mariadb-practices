package bookmall.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bookmall.db.DBConnection;
import bookmall.vo.OrderVo;
import bookmall.vo.OrderBookVo;

public class OrderDao {

	public void insert(OrderVo ordersVo) {

		try (Connection conn = DBConnection.getConnection();
				PreparedStatement pstmt1 = conn.prepareStatement(
						"insert into orders (user_no, number, status, payment, shipping) "
						+ "values(?, ?, ?, ?, ?)");
				PreparedStatement pstmt2 = conn.prepareStatement(
						"select last_insert_id() from dual");
		) {
			pstmt1.setLong(1, ordersVo.getUserNo());
			pstmt1.setString(2, ordersVo.getNumber());
			pstmt1.setString(3, ordersVo.getStatus());
			pstmt1.setInt(4, ordersVo.getPayment());
			pstmt1.setString(5, ordersVo.getShipping());
			pstmt1.executeUpdate();

			ResultSet rs = pstmt2.executeQuery();
			ordersVo.setNo(rs.next() ? rs.getLong(1) : null);
			rs.close();
		} catch (SQLException e) {
			System.out.println("error: " + e);
		}
	}

	public OrderVo findByNoAndUserNo(Long no, Long userNo) {
		OrderVo result = null;
		try (Connection conn = DBConnection.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(
						"select no, user_no, number, status, payment, shipping" 
						+ " from orders where no = ?");
		) {
			pstmt.setLong(1, no);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				Long savedUserNo = rs.getLong(2);
				if (savedUserNo == userNo) {
					result = new OrderVo();
					result.setNo(no);
					result.setUserNo(savedUserNo);
					result.setNumber(rs.getString(3));
					result.setStatus(rs.getString(4));
					result.setPayment(rs.getInt(5));
					result.setShipping(rs.getString(6));
				}
			}
			rs.close();
		} catch (SQLException e) {
			System.out.println("error: " + e);
		}

		return result;
	}

	public void insertBook(OrderBookVo orderBookVo) {

		try (Connection conn = DBConnection.getConnection();
				PreparedStatement pstmt1 = conn.prepareStatement(
						"insert into orders_book " 
						+ "(quantity, price, book_no, orders_no) values(?, ?, ?, ?)");
				PreparedStatement pstmt2 = conn.prepareStatement(
						"select last_insert_id() " 
						+ "from dual");
		) {
			pstmt1.setInt(1, orderBookVo.getQuantity());
			pstmt1.setInt(2, orderBookVo.getPrice());
			pstmt1.setLong(3, orderBookVo.getBookNo());
			pstmt1.setLong(4, orderBookVo.getOrderNo());
			pstmt1.executeUpdate();

			ResultSet rs = pstmt2.executeQuery();
			orderBookVo.setNo(rs.next() ? rs.getLong(1) : null);
			rs.close();
		} catch (SQLException e) {
			System.out.println("error: " + e);
		}
	}

	public List<OrderBookVo> findBooksByNoAndUserNo(Long orderNo, Long userNo) {

		List<OrderBookVo> result = new ArrayList<>();
		try (Connection conn = DBConnection.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(
						"select a.no, b.no, b.quantity, b.price, b.book_no, c.title" 
						+ " from orders_book b"
						+ " join orders a on a.no = b.orders_no" 
						+ " join book c on c.no = b.book_no"
						+ " where b.orders_no = ?" + " and a.user_no = ?");
		) {
			pstmt.setLong(1, orderNo);
			pstmt.setLong(2, userNo);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				OrderBookVo orderBookVo = new OrderBookVo();
				orderBookVo.setOrderNo(rs.getLong(1));
				orderBookVo.setNo(rs.getLong(2));
				orderBookVo.setQuantity(rs.getInt(3));
				orderBookVo.setPrice(rs.getInt(4));
				orderBookVo.setBookNo(rs.getLong(5));
				orderBookVo.setBookTitle(rs.getString(6));
				result.add(orderBookVo);
			}
			rs.close();
		} catch (SQLException e) {
			System.out.println("error: " + e);
		}

		return result;
	}

	public void deleteBooksByNo(Long no) {
		try (Connection conn = DBConnection.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(
						"delete from orders_book where orders_no = ?");
		) {
			pstmt.setLong(1, no);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("error: " + e);
		}

	}

	public void deleteByNo(Long no) {
		try (Connection conn = DBConnection.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(
						"delete from orders where no = ?");
		) {
			pstmt.setLong(1, no);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("error: " + e);
		}
	}

}
