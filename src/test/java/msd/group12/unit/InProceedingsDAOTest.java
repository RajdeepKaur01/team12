package test.java.msd.group12.unit;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import main.java.entities.InProceeding;
import main.java.queryengine.MariaDBDaoFactory;
import main.java.queryengine.dao.DAO;

public class InProceedingsDAOTest {

	public static DAO<InProceeding> inProceedingDao;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		inProceedingDao = MariaDBDaoFactory.getInstance().getInProceedingsDAO();
	}

	@Test
	public void testFindByKeys() {
		Set<String> keys = new HashSet<>();
		keys.add("journals/lncs/FranchittiK93");
		
		try {
			assertFalse(inProceedingDao.findByKeys(keys).isEmpty());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
