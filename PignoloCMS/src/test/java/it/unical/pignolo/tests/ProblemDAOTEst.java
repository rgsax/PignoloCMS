package it.unical.pignolo.tests;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import it.unical.pignolo.dao.DAOFactory;
import it.unical.pignolo.dao.ProblemDAO;
import it.unical.pignolo.model.Problem;
import it.unical.pignolo.utils.ServerUtils;

public class ProblemDAOTEst {	
	static Connection connection;
	
	@BeforeClass
	public static void setup() throws SQLException {
		connection = ServerUtils.getConnection();
	}
	
	@AfterClass
	public static void destroyAll() throws SQLException {
		connection.close();
	}
	
//	@Test
//	public void createProblemTest() {
//		Problem p = new Problem(null, "n-queens", 4, "Disponi n regine su una scacchiera nxn...", 0.25);
//		ProblemDAO pd = DAOFactory.getInstance().createProblemDAO();
//		
//		pd.save(p);
//		assertNotNull(p.getId());
//		
//		Optional<Problem> op = pd.get(p.getId());
//		assertTrue(op.isPresent());
//	}
//	
	@Test
	public void deleteProblemTest() {
		Problem p = new Problem(null, "n-queens", 4, "Disponi n regine su una scacchiera nxn...", 0.25);
		ProblemDAO pd = DAOFactory.getInstance().createProblemDAO(connection);
		
		pd.save(p);
		assertNotNull(p.getId());
		
		Optional<Problem> op = pd.get(p.getId());
		assertTrue(op.isPresent());
		
		pd.delete(p);
	}
	
	@BeforeClass
	public static void clean() {
		
	}

}
