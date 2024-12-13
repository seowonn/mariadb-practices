package bookmall.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bookmall.db.dbConn;
import bookmall.vo.CategoryVo;

public class CategoryDao {

	private static Connection conn = null;

	public void insert(CategoryVo categoryVo) {
		
		Long lastId = 0L;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = dbConn.getConnection();

			String sql = "insert into category (category_type) values(?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, categoryVo.getCategoryType());

			pstmt.executeUpdate();

	        sql = "SELECT last_insert_id() FROM dual";
	        pstmt = conn.prepareStatement(sql);
	        rs = pstmt.executeQuery();
	        if (rs.next()) {
	            lastId = rs.getLong(1);
	            categoryVo.setNo(lastId);
	        }
	        
		} catch (SQLException e) {
			System.out.println("error: " + e);
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (rs != null) {
					rs.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("error: " + e);
			}
		}

	}

	public List<CategoryVo> findAll() {
		
		List<CategoryVo> result = new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = dbConn.getConnection();

			String sql = "select category_type from category";
			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				String categoryType = rs.getString(1);
				result.add(new CategoryVo(categoryType));
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
