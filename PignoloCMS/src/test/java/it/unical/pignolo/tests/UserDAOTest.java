package it.unical.pignolo.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import it.unical.pignolo.dao.DAOFactory;
import it.unical.pignolo.dao.UserDAO;
import it.unical.pignolo.model.User;
import it.unical.pignolo.utils.ServerUtils;

public class UserDAOTest {
	private static DAOFactory df; 
	static User u1;
	static User u2; 
	static Connection connection;
	
	@BeforeClass
	public static void init() throws SQLException {
		connection = ServerUtils.getConnection();
		df = DAOFactory.getInstance();
		u1 = new User(null, "rgsax", "riccardo", "giordano", "b@b.b", false);
		u2 = new User(null, "ainnoot", "antonio", "ielo", "a.a@a.a", false);
	}
	
	@AfterClass
	public static void clean() throws SQLException {
		connection.close();
		UserDAO ud = df.createUserDAO(connection); 
		if (u1 != null) {
			ud.delete(u1);
		}
		
		if (u2 != null) {
			ud.delete(u2);
		}
	}
	
	@Test
	public void createTest() {
		UserDAO ud = df.createUserDAO(connection);
		ud.save(u2);
		assertNotNull(u2.getId());
		
		Optional<User> ou = ud.get(u2.getId());
		assertTrue(ou.isPresent());
	}
	
	@Test
	public void deleteTest() {
		UserDAO ud = df.createUserDAO(connection);
		ud.save(u1);
		ud.delete(u1);
		
		Optional<User> ou = ud.get(u1.getId());
		assertFalse(ou.isPresent());
		u1 = null; 
	}

}
