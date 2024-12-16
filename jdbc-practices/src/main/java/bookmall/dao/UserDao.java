package bookmall.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bookmall.db.DBConnection;
import bookmall.vo.UserVo;

public class UserDao {
	
	public void insert(UserVo userVo) {
		
		try(
				Connection conn = DBConnection.getConnection();
				PreparedStatement pstmt1 = conn.prepareStatement(
						"insert into user (name, email, password, phone_number) "
						+ "values(?, ?, ?, ?)");
				PreparedStatement pstmt2 = conn.prepareStatement(
						"select last_insert_id() from dual");
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
		try (Connection conn = DBConnection.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(
						"select name, email, password, phone_number from user");
		) {
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				UserVo userVo = new UserVo();
				userVo.setName(rs.getString(1));
				userVo.setEmail(rs.getString(2));
				userVo.setPassword(rs.getString(3));
				userVo.setPhoneNumber(rs.getString(4));
				result.add(userVo);
			}
			rs.close();
		} catch (SQLException e) {
			System.out.println("error: " + e);
		}
		return result;
	}

	public void deleteByNo(Long no) {
		try (
				Connection conn = DBConnection.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(
						"delete from user where no = ?");
		) {
			pstmt.setLong(1, no);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("error: " + e);
		}
	}

}
