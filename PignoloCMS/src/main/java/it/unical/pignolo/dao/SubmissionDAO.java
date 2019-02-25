package it.unical.pignolo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import it.unical.pignolo.model.Language;
import it.unical.pignolo.model.Problem;
import it.unical.pignolo.model.Status;
import it.unical.pignolo.model.Submission;
import it.unical.pignolo.model.TestCase;
import it.unical.pignolo.model.User;

public class SubmissionDAO extends DAO<Submission> {

	public SubmissionDAO(Connection c) {
		super(c);
	}
	
	public void createPastebinLink(Submission s, String link) {
		try {
			String query = "UPDATE submission SET pastebin_link = ? WHERE id = ?;"; 
			PreparedStatement ps = connection.prepareStatement(query); 
			ps.setString(1, link);
			ps.setLong(2, s.getId());
			ps.executeUpdate(); 
			ps.close(); 
		} catch (SQLException e) {
			e.printStackTrace(); 
		}
	}
	
	public String pastebinLink(Submission s) {
		String link = null; 
		try {
			String q = "SELECT pastebin_link FROM submission WHERE id =?; "; 
			PreparedStatement ps = connection.prepareStatement(q); 
			ps.setLong(1, s.getId());
			
			ResultSet rs = ps.executeQuery(); 
			ps.close(); 
			
			while (rs.next()) {
				link = rs.getString(1); 
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return link; 
	}
	
	
	public List<Submission> getUserSubmissions(User u) {
		List<Submission> us = new ArrayList<>(); 
		try  {
			String q = "SELECT id, problem, user, sourcecode, first_failure, status, language FROM submission WHERE user = " + u.getId() + ";";
			PreparedStatement ps = connection.prepareStatement(q);
			ResultSet rs = ps.executeQuery();
			ps.close(); 
			
			DAOFactory f = DAOFactory.getInstance();
			ProblemDAO pd = f.createProblemDAO(connection);
			TestCaseDAO td =  f.createTestCaseDAO(connection); 
			StatusDAO sd = f.createStatusDAO(connection); 
			LanguageDAO ld = f.createLanguageDAO(connection); 
			
			while (rs.next()) {
				Submission s = new Submission(); 
				s.setId( rs.getLong("id") );
				s.setProblem( pd.get( rs.getLong("problem") ).get() );
				s.setUser( u );
				s.setSource( rs.getString("sourcecode") );
				Optional<TestCase> tco = td.get(rs.getLong("first_failure"));
				if(tco.isPresent())
					s.setFirstFailure(tco.get());
				s.setStatus( sd.get( rs.getLong("status") ).get());
				s.setLanguage( ld.get(rs.getLong("language")).get() );
				
				us.add(s); 
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return us; 
	}

	public List<Submission> getAll() {
		List<Submission> ss = new ArrayList<Submission>(); 
		try  {
			String q = "SELECT * FROM submission;";
			PreparedStatement ps = connection.prepareStatement(q);
			
			ResultSet rs = ps.executeQuery();
			ps.close();
			
			while (rs.next()) {
				DAOFactory factory = DAOFactory.getInstance();
				Submission sub = new Submission(); 
				Long id = rs.getLong("id");
				User u = factory.createUserDAO(connection).get(rs.getLong("user")).get();
				String src = rs.getString("source");
				TestCase t = factory.createTestCaseDAO(connection).get(rs.getLong("first_failure")).get();
				Status s = factory.createStatusDAO(connection).get(rs.getLong("status")).get();
				Problem p = factory.createProblemDAO(connection).get(rs.getLong("problem")).get();
				Language l = factory.createLanguageDAO(connection).get(rs.getLong("language")).get(); 
				
				sub.setId(id);
				sub.setUser(u);
				sub.setSource(src);
				sub.setFirstFailure(t);
				sub.setProblem(p);
				sub.setStatus(s);
				sub.setLanguage(l);
				ss.add(sub); 
			}
			
		} catch (SQLException e) {
		
		}
		return ss; 
	}

	public void save(Submission s) {
		try  {
			String q = "INSERT INTO submission(problem, user, sourcecode, first_failure, status, language) "
					+ "VALUES(?, ?, ?, ?, ?, ?);";
			PreparedStatement ps = connection.prepareStatement(q);
			ps.setLong(1, s.getProblem().getId());
			ps.setLong(2, s.getUser().getId());
			ps.setString(3, s.getSource());
			if(s.getFirstFailure() != null)
				ps.setLong(4, s.getFirstFailure().getId());
			else
				ps.setNull(4, Types.NULL);
			ps.setLong(5, s.getStatus().getId());
			ps.setLong(6, s.getLanguage().getId());
			
			ps.executeUpdate();
			ps.close();
			
			q = "SELECT LAST_INSERT_ID() FROM submission";
			ps = connection.prepareStatement(q);
			
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				s.setId(rs.getLong(1));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void update(Submission t) {
		// non aggiornerò mai una submission 
	}

	public void delete(Submission s) {
		try  {
			String q = "DELETE FROM submission WHERE id = ?;";
			PreparedStatement ps = connection.prepareStatement(q);
			ps.setLong(1, s.getId());
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public Optional<Submission> get(Long id) {
		Submission s = null; 
		try  {
			String q = "SELECT problem, user, sourcecode, first_failure, status, language "+
					"FROM submission WHERE id = ?;";
			
			PreparedStatement ps = connection.prepareStatement(q);
			ps.setLong(1, id);
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				DAOFactory factory = DAOFactory.getInstance();
				
				s = new Submission(); 
				s.setId(id);
				s.setProblem(factory.createProblemDAO(connection).get(rs.getLong(1)).get());
				s.setUser(factory.createUserDAO(connection).get(rs.getLong(2)).get());
				s.setSource(rs.getString(3));
				Long firstFailureID = rs.getLong(4);
				if(firstFailureID != null) {
					Optional<TestCase> tco = factory.createTestCaseDAO(connection).get(firstFailureID);
					if(tco.isPresent())
					s.setFirstFailure(tco.get());
				}
				s.setStatus(factory.createStatusDAO(connection).get(rs.getLong(5)).get());
				s.setLanguage(factory.createLanguageDAO(connection).get(rs.getLong(6)).get());
			}
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return Optional.ofNullable(s);
	}

}
