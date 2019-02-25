package it.unical.pignolo.dao;

import java.sql.Connection;

public class DAOFactory {
	private static DAOFactory instance = null;  
	
	private DAOFactory() { 
	}
	
	public static DAOFactory getInstance() {
		if (instance == null) {
			return new DAOFactory(); 
		}	
		return instance; 
	}
	
	public ProblemDAO createProblemDAO(Connection c) {
		return new ProblemDAO(c);
	}
	
	public ProblemStatisticsDAO createProblemStatisticsDAO(Connection c) {
		return new ProblemStatisticsDAO(c); 
	}
	
	public SubmissionDAO createSubmissionDAO(Connection c) {
		return new SubmissionDAO(c);
	}
	
	public TestCaseDAO createTestCaseDAO(Connection c) {
		return new TestCaseDAO(c); 
	}
	
	public UserDAO createUserDAO(Connection c) {
		return new UserDAO(c); 
	}
	
	public TagDAO createTagDAO(Connection c) {
		return new TagDAO(c);
	}
	
	public StatusDAO createStatusDAO(Connection c) {
		return new StatusDAO(c);
	}
	
	public LanguageDAO createLanguageDAO(Connection c) {
		return new LanguageDAO(c);
	}
	
	public QuestionDAO createQuestionDAO(Connection c) {
		return new QuestionDAO(c); 
	}
	
	public AnswerDAO createAnswerDAO(Connection c) {
		return new AnswerDAO(c); 
	}
	
}
