package it.unical.pignolo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import it.unical.pignolo.model.Problem;
import it.unical.pignolo.model.Question;
import it.unical.pignolo.model.User;

public class QuestionDAO extends DAO<Question> {

	public QuestionDAO(Connection c) {
		super(c);
	}

	@Override
	public Optional<Question> get(Long id) {
		Question question = null; 
		try {
			String q = "SELECT problem, user, question_msg, answered FROM question WHERE id = ?;";
			PreparedStatement ps = connection.prepareStatement(q); 
			ps.setLong(1,  id); 
			
			ResultSet rs = ps.executeQuery(); 
			ps.close(); 
			
			ProblemDAO pd = DAOFactory.getInstance().createProblemDAO(connection);
			UserDAO ud = DAOFactory.getInstance().createUserDAO(connection); 
			
			while (rs.next()) {
				question = new Question();  
				question.setId(id);
				question.setProblem(pd.get(rs.getLong(1)).get());
				question.setUser(ud.get(rs.getLong(2)).get());
				question.setQuestion(rs.getString(3));
				question.setAnswered(rs.getBoolean(4));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return Optional.ofNullable(question); 
	}

	@Override
	public List<Question> getAll() {
		List<Question> qs = new ArrayList<>(); 
		try {
			String query = "SELECT id, problem, user, question_msg, answered FROM question;"; 
			PreparedStatement ps = connection.prepareStatement(query); 
			
			ResultSet rs = ps.executeQuery(); 
			ps.close(); 
			
			ProblemDAO pd = DAOFactory.getInstance().createProblemDAO(connection);
			UserDAO ud = DAOFactory.getInstance().createUserDAO(connection); 
			
			while (rs.next()) {
				Question question = new Question(); 
				question.setId(rs.getLong(1));
				question.setProblem(pd.get(rs.getLong(2)).get());
				question.setUser(ud.get(rs.getLong(3)).get());
				question.setQuestion(rs.getString(4)); 
				question.setAnswered(rs.getBoolean(5));
				
				qs.add(question); 
			}
		} catch (SQLException e) {
			e.printStackTrace();
			
		}
		return qs; 
	}

	@Override
	public void save(Question t) {
		try {
			String query = "INSERT INTO question(problem, user, question_msg) VALUES(?, ?, ?); "; 
			PreparedStatement ps = connection.prepareStatement(query); 
			
			ps.setLong(1, t.getProblem().getId());
			ps.setLong(2, t.getUser().getId());
			ps.setString(3, t.getQuestion());
			
			ps.executeUpdate(); 
			ps.close(); 
			
			query = "SELECT LAST_INSERT_ID() FROM question; "; 
			ps = connection.prepareStatement(query); 
			
			ResultSet rs = ps.executeQuery(); 
			
			while (rs.next()) {
				t.setId(rs.getLong(1));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(Question t) {
		try {
			String query = "UPDATE question SET problem = ?, user = ?, question_msg = ?, answered = ? WHERE id = ?; ";
			PreparedStatement ps = connection.prepareStatement(query); 
			
			ps.setLong(1, t.getProblem().getId()); 
			ps.setLong(2, t.getUser().getId()); 
			ps.setString(3, t.getQuestion());
			ps.setBoolean(4, t.getAnswered());
			
			ps.executeUpdate(); 
			ps.close(); 
			
		} catch (SQLException e) {
			e.printStackTrace(); 
		}
	}

	@Override
	public void delete(Question t) {
		try {
			String query = "DELETE FROM question WHERE id = ?;";
			PreparedStatement ps = connection.prepareStatement(query); 
			ps.setLong(1,  t.getId());
			
			ps.executeUpdate(); 
			ps.close(); 
		} catch (SQLException e) {
			e.printStackTrace(); 
		}
	}

	public List<Question> getProblemQuestions(Problem problem) {
		List<Question> questions = new ArrayList<>();
		
		try {
			String query = "SELECT id, user, question_msg, answered FROM question WHERE problem = ?;";
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setLong(1, problem.getId());
			ResultSet rs = ps.executeQuery();
			ps.close();
			
			UserDAO ud = DAOFactory.getInstance().createUserDAO(connection);
			
			while(rs.next()) {
				Question question = new Question();
				question.setId(rs.getLong(1));
				question.setProblem(problem);
				question.setUser(ud.get(rs.getLong(2)).get());
				question.setQuestion(rs.getString(3));
				question.setAnswered(rs.getBoolean(4));
				
				questions.add(question);
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return questions;
	}

	public List<Question> getUserQuestions(User u) {
		List<Question> qs = new ArrayList<>(); 
		try {
			String query = "SELECT id, problem, question_msg FROM question WHERE user = ?;";
			PreparedStatement ps = connection.prepareStatement(query); 
			ps.setLong(1, u.getId());
			
			ResultSet rs = ps.executeQuery(); 
			ps.close(); 
			
			ProblemDAO pd = DAOFactory.getInstance().createProblemDAO(connection); 
			
			while (rs.next()) {
				Problem p = pd.get(rs.getLong(2)).get();
				Question q = new Question(); 
				q.setId(rs.getLong(1));
				q.setProblem(p);
				q.setQuestion(rs.getString(3));
				
				qs.add(q); 
			}
		} catch (SQLException e) {
			e.printStackTrace(); 
		}
		
		return qs; 
	}
}
