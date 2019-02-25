package it.unical.pignolo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import it.unical.pignolo.model.Problem;
import it.unical.pignolo.model.ProblemStatistics;
import it.unical.pignolo.utils.ServerUtils;

public class ProblemStatisticsDAO extends DAO<ProblemStatistics> {

	public ProblemStatisticsDAO(Connection c) {
		super(c);
	}
	
	public int userAcceptedAnswers(Long userId) { 
		int result = 0; 
		
		try {
			String query = "SELECT COUNT(*) as tot FROM submission WHERE user = ? and status = ?;";
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setLong(1, userId);
			ps.setLong(2, ServerUtils.STATUS_CORRECT);
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				result = (int) rs.getLong(1);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result; 	
	}
	
	public int userWrongAnswers(Long userId) {
		int result = 0; 
		
		try {
			String query = "SELECT COUNT(*) as tot FROM submission WHERE user = ? and status = ?;";
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setLong(1, userId);
			ps.setLong(2, ServerUtils.STATUS_WRONG);
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				result = (int) rs.getLong(1);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result; 	
	}
	
	public int userTimelimitAnswers(Long userId) {
		int result = 0; 
		
		try {
			String query = "SELECT COUNT(*) as tot FROM submission WHERE user = ? and status = ?;";
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setLong(1, userId);
			ps.setLong(2, ServerUtils.STATUS_TIMELIMIT);
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				result = (int) rs.getLong(1);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result; 		
	}
	
	public int userCompilationErrorAnswers(Long userId) {
		int result = 0; 
		
		try {
			String query = "SELECT COUNT(*) as tot FROM submission WHERE user = ? and status = ?;";
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setLong(1, userId);
			ps.setLong(2, ServerUtils.STATUS_COMPILER_ERROR);
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				result = (int) rs.getLong(1);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result; 	
	}
	
	public int userRunErrorAnswers(Long userId) {
		int result = 0; 
		
		try {
			String query = "SELECT COUNT(*) as tot FROM submission WHERE user = ? and status = ?;";
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setLong(1, userId);
			ps.setLong(2, ServerUtils.STATUS_RUN_ERROR);
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				result = (int) rs.getLong(1);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result; 	
	}
	
	public boolean userHasSolvedProblem(Long problemId, Long userId) {
		boolean result = false; 
		
		try {
			String query = "SELECT COUNT(*) as tot FROM submission WHERE problem = ? and user = ? and status = ?;";
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setLong(1, problemId);
			ps.setLong(2, userId);
			ps.setLong(3, ServerUtils.STATUS_CORRECT);
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				int s = (int) rs.getLong("tot");
				result = s > 0; 
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result; 
	}
	
	public int getAcceptedSubmissionsForProblem(Long problemId) {
		int result = 0; 
		
		try {
			String query = "SELECT accepted FROM problem_stats WHERE id = ?;";
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setLong(1, problemId);
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				result = (int) rs.getLong("accepted");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result; 
	}
	
	public int getTotalSubmissionsForProblem(Long problemId) {
		int result = 0; 
		
		try {
			String query = "SELECT (accepted + wrong + timelimit + compilation_error) as tot FROM problem_stats WHERE id = ?;";
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setLong(1, problemId);
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				result = (int) rs.getLong("tot");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result; 
	}
	
	public int getAvgSubmissionsForProblem(Long problemId) {
		int result = 0; 
		
		try {
			String query = "SELECT AVG(ct) as _avg FROM (SELECT COUNT(*) as ct FROM submission WHERE problem = ? GROUP BY `user`) as t";
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setLong(1, problemId);
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				result = (int) rs.getLong("_avg");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result; 
	}
	
	public int getUserSubmissionsForProblem(Long problemId, Long userId) {
		int result = 0; 
		
		try {
			String query = "SELECT COUNT(*) as tot FROM submission WHERE problem = ? and user = ?";
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setLong(1, problemId);
			ps.setLong(2,  userId);
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				result = (int) rs.getLong("tot");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result; 
	}

	@Override
	public List<ProblemStatistics> getAll() {
		List<ProblemStatistics> stats = new ArrayList<>();
		
		try {
			String query = "SELECT * FROM problem_stats;";
			PreparedStatement ps = connection.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				Problem problem = DAOFactory.getInstance().createProblemDAO(connection).get(rs.getLong(1)).get();
				Integer wrong = rs.getInt(2);
				Integer timelimit = rs.getInt(3);
				Integer accepted = rs.getInt(4);
				Integer compilationError = rs.getInt(5);
				
				stats.add(new ProblemStatistics(problem, wrong, timelimit, accepted, compilationError));
			}
			
			ps.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return stats;
	}
	
	@Override
	public void save(ProblemStatistics stat) {
		try {
			String query = "INSERT INTO problem_stats(wrong, timelimit, accepted, compilation_error) VALUES(?, ?, ?, ?);";
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setInt(1, stat.getWrong());
			ps.setInt(2, stat.getTimelimit());
			ps.setInt(3, stat.getAccepted());
			ps.setInt(4, stat.getCompilationError());
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(ProblemStatistics stat) {
		try {
			String query = "UPDATE problem_stats SET wrong=?, timelimit=?, accepted=?, compilation_error=?;";
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setInt(1, stat.getWrong());
			ps.setInt(2, stat.getTimelimit());
			ps.setInt(3, stat.getAccepted());
			ps.setInt(4, stat.getCompilationError());
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete(ProblemStatistics stat) {
		try {
			String query = "DELETE FROM problem_stats WHERE id=?;";
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setLong(1, stat.getProblem().getId());
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Optional<ProblemStatistics> get(Long problemId) {
		ProblemStatistics stat = null;
		
		try {
			String query = "SELECT wrong, timelimit, accepted, compilation_error FROM problem_stats WHERE id=?;";
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setLong(1, problemId);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				Problem problem = DAOFactory.getInstance().createProblemDAO(connection).get(problemId).get();
				Integer wrong = rs.getInt(1);
				Integer timelimit = rs.getInt(2);
				Integer accepted = rs.getInt(3);
				Integer compilationError = rs.getInt(4);
			
				stat = new ProblemStatistics(problem, wrong, timelimit, accepted, compilationError);
			}
			
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return Optional.ofNullable(stat);
	}

	

}
