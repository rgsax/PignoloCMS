package it.unical.pignolo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import it.unical.pignolo.model.Answer;
import it.unical.pignolo.model.Question;

public class AnswerDAO extends DAO<Answer> {
	public AnswerDAO(Connection c) {
		super(c);
	}
	
	// TODO: Prendere tutte le risposte per una determinata domanda -> Question come parametro 
	public List<Answer> answersToQuestion(Question q) {
		List<Answer> as = new ArrayList<Answer>(); 		
		try {
			String query = "SELECT id, user, answer_msg FROM answer WHERE question = ?;";
			PreparedStatement ps = connection.prepareStatement(query); 
			
			ps.setLong(1, q.getId());
			ResultSet rs = ps.executeQuery(); 
			ps.close(); 
			
			UserDAO ud = DAOFactory.getInstance().createUserDAO(connection); 
			
			while (rs.next()) {
				Answer a = new Answer(); 
				a.setId(rs.getLong("id"));
				a.setQuestion(q);
				a.setUser(ud.get(rs.getLong("user")).get());
				a.setAnswer(rs.getString("answer_msg"));
				as.add(a); 
			}
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return as; 
	}


	@Override
	public Optional<Answer> get(Long id) {
		Answer ans = null; 
		
		try {
			String q = "SELECT question, user, answer_msg FROM answer WHERE id = ?; "; 
			PreparedStatement ps = connection.prepareStatement(q); 
			ps.setLong(1, id);
			
			ResultSet rs = ps.executeQuery(); 
			ps.close(); 
		
			QuestionDAO qd = DAOFactory.getInstance().createQuestionDAO(connection); 
			UserDAO ud = DAOFactory.getInstance().createUserDAO(connection); 
			
			while (rs.next()) {
				ans = new Answer(); 
				ans.setId(id); 
				ans.setQuestion(qd.get(rs.getLong(1)).get());
				ans.setUser(ud.get(rs.getLong(2)).get());
				ans.setAnswer(rs.getString(3));
			}
		} catch (SQLException e) {
			e.printStackTrace(); 
		}
		
		return Optional.ofNullable(ans); 
	}

	@Override
	public List<Answer> getAll() {
		List<Answer> as = new ArrayList<Answer>(); 
		try {
			String query = "SELECT id, question, user, answer_msg FROM answer; "; 
			PreparedStatement ps = connection.prepareStatement(query); 
			
			ResultSet rs = ps.executeQuery(); 
			ps.close(); 
			
			QuestionDAO qd = DAOFactory.getInstance().createQuestionDAO(connection);
			UserDAO ud = DAOFactory.getInstance().createUserDAO(connection); 
			
			while (rs.next()) {
				Answer a = new Answer();
				a.setId(rs.getLong(1));
				a.setQuestion(qd.get(rs.getLong(2)).get());
				a.setUser(ud.get(rs.getLong(3)).get());
				a.setAnswer(rs.getString(4));
				
				as.add(a); 
			}
			
		} catch (SQLException e) {
			e.printStackTrace(); 
		}
		return as; 
	}

	@Override
	public void save(Answer t) {
		try {
			String query = "INSERT INTO answer(question, user, answer_msg) VALUES(?, ?, ?);";
			PreparedStatement ps = connection.prepareStatement(query); 
			ps.setLong(1, t.getQuestion().getId());
			ps.setLong(2, t.getUser().getId());
			ps.setString(3, t.getAnswer());
			
			ps.executeUpdate(); 
			ps.close(); 
			
			query = "SELECT LAST_INSERT_ID() FROM answer; "; 
			ps = connection.prepareStatement(query); 
			
			ResultSet rs = ps.executeQuery();
			ps.close(); 
			
			while (rs.next()) {
				t.setId(rs.getLong(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(Answer t) {
		try {
			String query = "UPDATE answer SET question = ?, user = ?, answer_msg = ? WHERE id = ?; ";
			PreparedStatement ps = connection.prepareStatement(query); 
			
			ps.setLong(1, t.getQuestion().getId()); 
			ps.setLong(2, t.getUser().getId()); 
			ps.setString(3, t.getAnswer());
			
			ps.executeUpdate(); 
			ps.close(); 
			
		} catch (SQLException e) {
			e.printStackTrace(); 
		}
	}

	@Override
	public void delete(Answer t) {
		try {
			String query = "DELETE FROM answer WHERE id = ?;";
			PreparedStatement ps = connection.prepareStatement(query); 
			ps.setLong(1,  t.getId());
			
			ps.executeUpdate(); 
			ps.close(); 
		} catch (SQLException e) {
			e.printStackTrace(); 
		}
	}

}
