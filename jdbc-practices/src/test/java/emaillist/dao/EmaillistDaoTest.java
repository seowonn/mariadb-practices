package emaillist.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import emaillist.dao.EmaillistDao;
import emaillist.vo.EmaillistVo;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EmaillistDaoTest {

	private int count = 0;

	@BeforeAll
	public void setUp() {
		List<EmaillistVo> list = new EmaillistDao().findAll();
		count = list.size();
	}

	@Test
	@Order(1)
	public void insertTest() {
		EmaillistVo vo = new EmaillistVo();
		vo.setFirstName("데");
		vo.setLastName("스트");
		vo.setEmail("test@test.com");

		Boolean result = new EmaillistDao().insert(vo);

		assertTrue(result);
	}

	@Test
	@Order(2)
	public void findAllTest() {
		List<EmaillistVo> list = new EmaillistDao().findAll();
		assertEquals(count + 1, list.size());
	}

	@Test
	@Order(3)
	public void deleteByEmailTest() {
		Boolean result = new EmaillistDao().deleteByEmail("test@test.com");
		assertTrue(result);
	}

	@AfterAll
	public void cleanUp() {

	}
}
