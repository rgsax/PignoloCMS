package it.unical.pignolo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import it.unical.pignolo.model.Problem;
import it.unical.pignolo.model.TestCase;

public class TestCaseDAO extends DAO<TestCase> {
	
	public TestCaseDAO(Connection c) {
		super(c);
	}

	public List<TestCase> getAll() {
		List<TestCase> ts = new ArrayList<>(); 
		try  {
			String q = "SELECT * FROM testcase;"; 
			PreparedStatement ps = connection.prepareStatement(q);
			
			ResultSet rs = ps.executeQuery();
			ps.close();
			
			while (rs.next()) {
				Long problemId = rs.getLong(1);
				String input = rs.getString(2);
				String output = rs.getString(3); 
				
				TestCase t = new TestCase();
				t.setInput(input);
				t.setOutput(output);
				t.setProblem(DAOFactory.getInstance().createProblemDAO(connection).get(problemId).get());
				
				ts.add(t);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ts; 
	}

	public void save(TestCase t) {
		try  {
			String q = "INSERT INTO testcase(problem, input, output) VALUES(?, ?, ?);";
			PreparedStatement ps = connection.prepareStatement(q);
			
			ps.setLong(1, t.getProblem().getId());
			ps.setString(2, t.getInput());
			ps.setString(3, t.getOutput());
			
			ps.executeUpdate();
			ps.close();
			
			q = "SELECT LAST_INSERT_ID() FROM testcase;";
			ps = connection.prepareStatement(q);
			
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				t.setId(rs.getLong(1));
			}

			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}

	public void update(TestCase t) {
		try  {
			String q = "UPDATE testcase SET problem = ?, input = ?, output = ? WHERE id = ?;";
			PreparedStatement ps = connection.prepareStatement(q);
			
			ps.setLong(1, t.getProblem().getId());
			ps.setString(2, t.getInput());
			ps.setString(3, t.getOutput());
			ps.setLong(4, t.getId());
			ps.executeUpdate();
			ps.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void delete(TestCase t) {
		try  {
			String q = "DELETE FROM testcase WHERE id = ?;";
			PreparedStatement ps = connection.prepareStatement(q);
			
			ps.setLong(1, t.getId());
			ps.executeUpdate();
			ps.close(); 
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List<TestCase> getProblemTestCases(Problem problem) {
		List<TestCase> ts = new ArrayList<>(); 
		try  {
			String q = "SELECT t.id, t.input, t.output FROM testcase as t WHERE t.problem = ?;";
			PreparedStatement ps = connection.prepareStatement(q);
			ps.setLong(1, problem.getId());
			
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				TestCase t = new TestCase(); 
				t.setId(rs.getLong("id"));
				t.setInput(rs.getString("input"));
				t.setOutput(rs.getString("output"));
				t.setProblem(problem);
				
				ts.add(t);
			}
			
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ts; 
	}

	@Override
	public Optional<TestCase> get(Long id) {
		TestCase t = null; 
		try  {
			String q = "SELECT problem, input, output FROM testcase WHERE id = ?;";
			PreparedStatement ps = connection.prepareStatement(q);
			ps.setLong(1, id);
			
			ResultSet rs = ps.executeQuery();
			ps.close();
			
			while (rs.next()) {
				Long problemId = rs.getLong(1);
				String input = rs.getString(2);
				String output = rs.getString(3); 
				
				t = new TestCase();
				t.setInput(input);
				t.setOutput(output);
				t.setProblem(DAOFactory.getInstance().createProblemDAO(connection).get(problemId).get());
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Optional.ofNullable(t);
	}

	public void saveAll(List<TestCase> testcases) {
		if (testcases.isEmpty()) {
			return; 
		}
		
		try  {
			StringBuilder sb = new StringBuilder();
			sb.append("INSERT INTO testcase(problem, input, output) VALUES ");
			
			for(TestCase t : testcases) {
				sb.append("(").append(t.getProblem().getId().toString())
					.append(", '").append(t.getInput()).append("', '")
					.append(t.getOutput()).append("'), ");
			}
			
			sb.replace(sb.length() - 2, sb.length() - 1, ";");
			
			PreparedStatement ps = connection.prepareStatement(sb.toString());
			
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}

}
