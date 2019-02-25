package it.unical.pignolo.tests;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import it.unical.pignolo.dao.DAOFactory;
import it.unical.pignolo.dao.LanguageDAO;
import it.unical.pignolo.dao.ProblemDAO;
import it.unical.pignolo.dao.ProblemProxy;
import it.unical.pignolo.dao.StatusDAO;
import it.unical.pignolo.dao.SubmissionDAO;
import it.unical.pignolo.dao.TestCaseDAO;
import it.unical.pignolo.dao.UserDAO;
import it.unical.pignolo.model.Language;
import it.unical.pignolo.model.Problem;
import it.unical.pignolo.model.Status;
import it.unical.pignolo.model.Submission;
import it.unical.pignolo.model.TestCase;
import it.unical.pignolo.model.User;
import it.unical.pignolo.utils.ServerUtils;

public class ComplexTest {

	static DAOFactory factory;
	static ProblemDAO problemDAO;
	static SubmissionDAO submissionDAO;
	static UserDAO userDAO;
	static StatusDAO statusDAO;
	static LanguageDAO languageDAO;
	static TestCaseDAO testcaseDAO;
	static Status status;
	static Language language;
	static Connection connection;
	
	@BeforeClass
	public static void init() throws SQLException {
		connection = ServerUtils.getConnection();
		factory = DAOFactory.getInstance();
		problemDAO = factory.createProblemDAO(connection);
		submissionDAO = factory.createSubmissionDAO(connection);
		userDAO = factory.createUserDAO(connection);
		statusDAO = factory.createStatusDAO(connection);
		languageDAO = factory.createLanguageDAO(connection);
		testcaseDAO = factory.createTestCaseDAO(connection);
		
		status = statusDAO.get(new Long(0)).get();
		language = new Language(null, "C++", "g++", "./");
		languageDAO.save(language);
	}
	
	@AfterClass
	public static void destroyAll() throws SQLException {
		languageDAO.delete(language);
		connection.close();
	}
	
	@Test
	public void complexTest() {		
		User user = new User(null, "tester", "User", "Test", "test@case.it", false);
		Problem problem = new ProblemProxy(null, "k-colorabilità", 1, "parla con la Perri", 0.10);
		
		TestCase testc1 = new TestCase(null, problem, "cusè", "okok");
		TestCase testc2 = new TestCase(null, problem, "giusto", "out giusto");
		
		Submission sub1 = new Submission(null, problem, user, "#include<iostream>", testc1, status, language);
		Submission sub2 = new Submission(null, problem, user, "patate", null, status, language);
		
		userDAO.save(user);
		problemDAO.save(problem);
		testcaseDAO.save(testc1);
		testcaseDAO.save(testc2);
		submissionDAO.save(sub1);
		submissionDAO.save(sub2);
		
		for(TestCase tc : problem.getProblemTestCases()) {
			System.out.println(tc);
		}
		
		userDAO.delete(user);
		problemDAO.delete(problem);
	}

}
