package bookmall.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bookmall.db.DBConnection;
import bookmall.vo.BookVo;

public class BookDao {

	public void insert(BookVo bookVo) {

		try (Connection conn = DBConnection.getConnection();
				PreparedStatement pstmt1 = conn
						.prepareStatement("insert into book (title, price, category_no) values(?, ?, ?)");
				PreparedStatement pstmt2 = conn.prepareStatement("select last_insert_id() from dual");) {
			pstmt1.setString(1, bookVo.getTitle());
			pstmt1.setInt(2, bookVo.getPrice());
			pstmt1.setLong(3, bookVo.getCategoryNo());
			pstmt1.executeUpdate();

			ResultSet rs = pstmt2.executeQuery();
			bookVo.setNo(rs.next() ? rs.getLong(1) : null);
			rs.close();
		} catch (SQLException e) {
			System.out.println("error: " + e);
		}

	}

	public void deleteByNo(Long no) {
		try (
				Connection conn = DBConnection.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("delete "
						+ "from book where no = ?");
		) {
			pstmt.setLong(1, no);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println("error: " + e);
		}
	}

}
