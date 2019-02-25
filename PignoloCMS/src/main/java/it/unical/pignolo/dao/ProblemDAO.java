package it.unical.pignolo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import it.unical.pignolo.model.Problem;
import it.unical.pignolo.model.User;

public class ProblemDAO extends DAO<Problem> {
	
	public ProblemDAO(Connection c) {
		super(c);
	}

	public Map<Problem, Integer> problemSubmissionCount() {
		Map<Problem, Integer> m = new HashMap<Problem, Integer>(); 
		try {
			String q = "SELECT problem.id, name, difficulty, timelimit, COUNT(*) FROM problem, submission WHERE problem.id = submission.problem\n" + 
					"GROUP BY problem; ";
			
			PreparedStatement ps = connection.prepareStatement(q);
			ResultSet rs = ps.executeQuery(); 
			
			while (rs.next()) {
				Problem p = new Problem(); 
				p.setId(rs.getLong(1));
				p.setName(rs.getString(2));
				p.setDifficulty(rs.getInt(3));
				p.setTimelimit(rs.getDouble(4));
				
				int submissions = rs.getInt(5);
				
				m.put(p, submissions); 
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
		return m; 
	}
	
	@Override
	public Optional<Problem> get(Long id) {
		Problem problem = null;
		
		try  {
			String query = "SELECT name, difficulty, description, timelimit FROM problem WHERE id = ?;";
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setLong(1, id);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				String name = rs.getString(1);
				Integer difficulty = rs.getInt(2);
				String description = rs.getString(3);
				Double timelimit = rs.getDouble(4);
				
				problem = new ProblemProxy(id, name, difficulty, description, timelimit);
			}
			
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return Optional.ofNullable(problem);
	}

	@Override
	public List<Problem> getAll() {
		List<Problem> problems = new ArrayList<>();
		
		try  {
			String query = "SELECT * FROM problem;";
			PreparedStatement ps = connection.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				Long id = rs.getLong(1);
				String name = rs.getString(2);
				Integer difficulty = rs.getInt(3);
				String description = rs.getString(4);
				Double timelimit = rs.getDouble(5);
				
				problems.add(new ProblemProxy(id, name, difficulty, description, timelimit));
			}
			
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return problems;
	}
	
	public List<Problem> getSuggestedProblems(User user, int limit) {
		List<Problem> problems = new ArrayList<>();
		
		try  {
			String query = "SELECT * FROM problem LIMIT " + limit + ";";
			PreparedStatement ps = connection.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				Long id = rs.getLong(1);
				String name = rs.getString(2);
				Integer difficulty = rs.getInt(3);
				String description = rs.getString(4);
				Double timelimit = rs.getDouble(5);
				
				problems.add(new ProblemProxy(id, name, difficulty, description, timelimit));
			}
			
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return problems;
	}

	@Override
	public void save(Problem problem) {
		try  {
			String query = "INSERT INTO problem(name, difficulty, description, timelimit) VALUES(?, ?, ?, ?);";
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, problem.getName());
			ps.setInt(2, problem.getDifficulty());
			ps.setString(3, problem.getDescription());
			ps.setDouble(4, problem.getTimelimit());
			
			ps.executeUpdate();	
			ps.close();
			
			query = "SELECT LAST_INSERT_ID() FROM problem;";
			ps = connection.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				problem.setId(rs.getLong(1));
			}
			ps.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}

	@Override
	public void update(Problem problem) {
		try  {
			String query = "UPDATE problem SET name=?, difficulty=?, description=?, timelimit=? WHERE id = ?;";
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, problem.getName());
			ps.setInt(2, problem.getDifficulty());
			ps.setString(3, problem.getDescription());
			ps.setDouble(4, problem.getTimelimit());
			ps.setLong(5, problem.getId());
			
			ps.executeUpdate();	
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete(Problem problem) {
		try  {
			String query = "DELETE FROM problem WHERE id=?;";
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setLong(1, problem.getId());
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
}
