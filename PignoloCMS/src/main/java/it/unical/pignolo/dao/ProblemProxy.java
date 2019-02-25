package it.unical.pignolo.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import it.unical.pignolo.model.Problem;
import it.unical.pignolo.model.ProblemStatistics;
import it.unical.pignolo.model.Tag;
import it.unical.pignolo.model.TestCase;
import it.unical.pignolo.utils.ServerUtils;

public class ProblemProxy extends Problem {
	private List<Tag> problemTags = null;
	private List<TestCase> problemTestCases = null;
	private ProblemStatistics problemStats = null;
	
	public ProblemProxy() { super(); }
	
	public ProblemProxy(Long id, String name, Integer difficulty, 
			String description, Double timelimit) {
		super(id, name, difficulty, description, timelimit);
	}
	
	@Override
	public List<Tag> getProblemTags() {
		if(problemTags == null) {
			Connection connection;
			try {
				connection = ServerUtils.getConnection();
				TagDAO td = DAOFactory.getInstance().createTagDAO(connection);				
				problemTags = td.getProblemTags(this);
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return problemTags; 
	}
	
	@Override
	public ProblemStatistics getProblemStats() {
		if(problemStats == null) {
			Connection connection;
			try {
				connection = ServerUtils.getConnection();

				ProblemStatisticsDAO psd = DAOFactory.getInstance().createProblemStatisticsDAO(connection);
				problemStats = psd.get(getId()).get();
				connection.close(); 
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		
		return problemStats;
	}
	
	@Override
	public List<TestCase> getProblemTestCases() {
		if(problemTestCases == null) {
			Connection connection;
			try {
				connection = ServerUtils.getConnection();

				TestCaseDAO tcd = DAOFactory.getInstance().createTestCaseDAO(connection);
				problemTestCases = tcd.getProblemTestCases(this);
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		
		return problemTestCases;
	}
}
