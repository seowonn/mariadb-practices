package bookmall.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bookmall.db.DBConnection;
import bookmall.vo.UserVo;
import emaillist.vo.EmaillistVo;

public class UserDao {
	
	private static Connection conn = null;

	public void insert(UserVo userVo) {
		
		try(
				Connection conn = DBConnection.getConnection();
				PreparedStatement pstmt1 = conn.prepareStatement(
						"insert into user (name, email, password, phone_number) values(?, ?, ?, ?)");
				PreparedStatement pstmt2 = conn.prepareStatement("select last_insert_id() from dual");
		) {
			pstmt1.setString(1, userVo.getName());
			pstmt1.setString(2, userVo.getEmail());
			pstmt1.setString(3, userVo.getPassword());
			pstmt1.setString(4, userVo.getPhoneNumber());
			pstmt1.executeUpdate();
			
			ResultSet rs = pstmt2.executeQuery();
			userVo.setNo(rs.next() ? rs.getLong(1) : null);
			rs.close();
		} catch (SQLException e) {
			System.out.println("error: " + e);
		}

	}

	public List<UserVo> findAll() {
		
		List<UserVo> result = new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = DBConnection.getConnection();

			String sql = "select name, email, password, phone_number from user";
			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				String name = rs.getString(1);
				String email = rs.getString(2);
				String password = rs.getString(3);
				String phoneNumber = rs.getString(4);

				result.add(new UserVo(name, email, password, phoneNumber));
			}

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
		
		return result;
	}

	public void deleteByNo(Long no) {
		try (
				Connection conn = DBConnection.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("delete "
						+ "from user where no = ?");
		) {
			pstmt.setLong(1, no);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println("error: " + e);
		}
	}

}
