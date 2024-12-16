package bookshop.dao;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import bookshop.vo.AuthorVo;

public class AuthorDaoTest {
	
	private static AuthorVo mockAuthorVo = new AuthorVo();
	private static AuthorDao dao = new AuthorDao();

	@Test
	@Order(1)
	public void insertTest() {
		mockAuthorVo.setName("칼세이건");
		dao.insert(mockAuthorVo);
		
		assertNotNull(mockAuthorVo.getId());
	}
	
	@AfterAll
	public static void cleanUp() {
		dao.deleteById(mockAuthorVo.getId());
	}
	
	@Test
	@Order(2)
	public void deleteByIdTest() {
		
	}
}
