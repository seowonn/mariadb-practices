package bookmall.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bookmall.db.dbConn;
import bookmall.vo.UserVo;
import emaillist.vo.EmaillistVo;

public class UserDao {
	
	private static Connection conn = null;

	public void insert(UserVo userVo) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = dbConn.getConnection();

			String sql = "insert into user (name, email, password, phone_number) values(?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userVo.getName());
			pstmt.setString(2, userVo.getEmail());
			pstmt.setString(3, userVo.getPassword());
			pstmt.setString(4, userVo.getPhoneNumber());

			int count = pstmt.executeUpdate();
			System.out.println(count);

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

	public List<UserVo> findAll() {
		
		List<UserVo> result = new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = dbConn.getConnection();

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

}
