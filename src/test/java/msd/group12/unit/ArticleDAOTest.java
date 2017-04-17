package test.java.msd.group12.unit;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import main.java.entities.Article;
import main.java.queryengine.MariaDBDaoFactory;
import main.java.queryengine.dao.DAO;

public class ArticleDAOTest {
	
	public static DAO<Article> articleDao;
	
	@BeforeClass
	public static void init() {
		articleDao = MariaDBDaoFactory.getInstance().getArticleDAO();
	}

	@Test
	public void testFindByAttribute() {
		Set<String> attributeValues = new HashSet<>();
		attributeValues.add("Pattern Matching in Trees and Nets.");
		try {
			assertFalse(articleDao.findByAttribute("title", attributeValues).isEmpty());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testFindByKeys() {
		Set<String> keys = new HashSet<>();
		keys.add("journals/acta/Saxena96");
		
		try {
			assertFalse(articleDao.findByKeys(keys).isEmpty());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
