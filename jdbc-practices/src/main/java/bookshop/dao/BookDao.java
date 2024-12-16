package bookshop.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.checkerframework.checker.units.qual.s;

import bookshop.vo.BookVo;

public class BookDao {
	
	private Connection getConnection() throws SQLException {
		Connection conn = null;

		try {
			Class.forName("org.mariadb.jdbc.Driver");

			String url = "jdbc:mariadb://192.168.0.10:3306/webdb";
			conn = DriverManager.getConnection(url, "webdb", "webdb");

		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패: " + e);
		} catch (SQLException e) {
			System.out.println("error: " + e);
		}

		return conn;
	}

	public int insert(BookVo bookVo) {
		int count = 0;

		try (
				Connection conn = getConnection(); 
				PreparedStatement pstmt1 = conn.prepareStatement("insert into book(title, author_id) values(?, ?)");
				PreparedStatement pstmt2 = conn.prepareStatement("select last_insert_id() from dual");
		) {
			pstmt1.setString(1, bookVo.getTitle());
			pstmt1.setLong(2, bookVo.getAuthorId());
			count = pstmt1.executeUpdate();
			
			ResultSet rs = pstmt2.executeQuery();
			bookVo.setId(rs.next() ? rs.getLong(1) : null);
			rs.close();
		} catch (SQLException e) {
			System.out.println("error: " + e);
		}

		return count;
	}

	public int deleteAll() {
		int count = 0;

		try (
				Connection conn = getConnection(); 
				PreparedStatement pstmt = conn.prepareStatement("delete from book");
		) {
			count = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("error: " + e);
		}

		return count;
	}

	public int update(Long id, String status) {
		int count = 0;

		try (
				Connection conn = getConnection(); 
				PreparedStatement pstmt = conn.prepareStatement("update book set status = ? where id = ?");
		) {
			pstmt.setString(1, status);
			pstmt.setLong(2, id);
			count = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("error: " + e);
		}

		return count;
	}

	public List<BookVo> findAll() {
		
		List<BookVo> result = new ArrayList<>();
		try(
				Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement("select a.id, a.title, b.name, a.");
		) {
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				Long id = rs.getLong(1);
				String title = rs.getString(2);
				String authorName = rs.getString(3);
				String status = rs.getString(4);
				result.add(new BookVo());
			}
			rs.close();
		} catch (SQLException e) {
			System.out.println("error: " + e);
		}
		
		return result;
	}

}
