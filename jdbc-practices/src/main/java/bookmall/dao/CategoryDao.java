package bookmall.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bookmall.db.DBConnection;
import bookmall.vo.CategoryVo;

public class CategoryDao {

	public void insert(CategoryVo categoryVo) {
		
		try (Connection conn = DBConnection.getConnection();
				PreparedStatement pstmt1 = conn.prepareStatement(
						"insert into category (category_type) values(?)");
				PreparedStatement pstmt2 = conn.prepareStatement(
						"select last_insert_id() from dual");
		){
			pstmt1.setString(1, categoryVo.getCategoryType());
			pstmt1.executeUpdate();

			ResultSet rs = pstmt2.executeQuery();
			categoryVo.setNo(rs.next() ? rs.getLong(1) : null);
			rs.close();
		} catch (SQLException e) {
			System.out.println("error: " + e);
		}

	}

	public List<CategoryVo> findAll() {
		
		List<CategoryVo> result = new ArrayList<>();
		try (Connection conn = DBConnection.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(
						"select category_type from category");
		) {
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				String categoryType = rs.getString(1);
				result.add(new CategoryVo(categoryType));
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
						"delete from category where no = ?");
		) {
			pstmt.setLong(1, no);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("error: " + e);
		}
	}

}
