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

import it.unical.pignolo.model.User;

public class UserDAO extends DAO<User> {

	public UserDAO(Connection c) {
		super(c);
	}
	
	public Optional<User> get(String username, String password) {
		User u = null; 
		try  {
			String q = "SELECT id, name, surname, email, is_admin FROM user WHERE username = ? AND password = password(?);";
			PreparedStatement ps = connection.prepareStatement(q);
			ps.setString(1, username);
			ps.setString(2,  password);
			
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Long id = rs.getLong(1);
				String name = rs.getString(2);
				String surname = rs.getString(3);
				String email = rs.getString(4); 
				Boolean isAdmin = rs.getBoolean(5);
				
				u = new User(id, username, name, surname, email, isAdmin); 
			}
			ps.close(); 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return Optional.ofNullable(u);
	}

	@Override
	public Optional<User> get(Long id) {
		User u = null; 
		try  {
			String q = "SELECT username, name, surname, email, is_admin FROM user WHERE id = ?;";
			PreparedStatement ps = connection.prepareStatement(q);
			ps.setLong(1, id);
			
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				String username = rs.getString(1);
				String name = rs.getString(2);
				String surname = rs.getString(3);
				String email = rs.getString(4); 
				Boolean isAdmin = rs.getBoolean(5);
				
				u = new User(id, username, name, surname, email, isAdmin); 
			}
			ps.close(); 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return Optional.ofNullable(u);
	}

	public Optional<User> get(String username) {
		User u = null; 
		try  {
			String q = "SELECT id, name, surname, email, is_admin FROM user WHERE username = ?;";
			PreparedStatement ps = connection.prepareStatement(q);
			ps.setString(1, username);
			
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Long id = rs.getLong(1);
				String name = rs.getString(2);
				String surname = rs.getString(3);
				String email = rs.getString(4); 
				Boolean isAdmin = rs.getBoolean(5);
				
				u = new User(id, username, name, surname, email, isAdmin); 
			}
			ps.close(); 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return Optional.ofNullable(u);
	}

	@Override
	public List<User> getAll() {
		List<User> us = new ArrayList<>(); 
		try  {
			String q = "SELECT * FROM user;";
			PreparedStatement ps = connection.prepareStatement(q);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				Long id = rs.getLong(1); 
				String username = rs.getString(2); 
				String name = rs.getString(3); 
				String surname = rs.getString(4); 
				String email = rs.getString(5); 
				Boolean isAdmin = rs.getBoolean(6);
				
				us.add(new User(id, username, name, surname, email, isAdmin)); 
			}		

			ps.close(); 
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return us; 
	}


	@Override
	public void save(User u) {
		try  {
			String q = "INSERT INTO user(username, name, surname, email, is_admin) VALUES(?, ?, ?, ?);";
			PreparedStatement ps = connection.prepareStatement(q);
			ps.setString(1, u.getUsername());
			ps.setString(2, u.getName());
			ps.setString(3, u.getSurname());
			ps.setString(4, u.getEmail());
			ps.setBoolean(5, u.getIsAdmin());

			ps.executeUpdate();
			ps.close(); 
			
			q = "SELECT LAST_INSERT_ID() FROM user;";
			ps = connection.prepareStatement(q);
			
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				u.setId(rs.getLong(1));
			}

			ps.close(); 
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void save(User u, String password) {
		try  {
			String q = "INSERT INTO user(username, name, surname, email, password, is_admin) VALUES(?, ?, ?, ?, password(?), ?);";
			PreparedStatement ps = connection.prepareStatement(q);
			ps.setString(1, u.getUsername());
			ps.setString(2, u.getName());
			ps.setString(3, u.getSurname());
			ps.setString(4, u.getEmail());
			ps.setString(5, password);
			ps.setBoolean(6, u.getIsAdmin());

			ps.executeUpdate();
			ps.close(); 
			
			q = "SELECT LAST_INSERT_ID() FROM user;";
			ps = connection.prepareStatement(q);
			
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				u.setId(rs.getLong(1));
			}

			ps.close(); 
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void update(User u) {
		try  {
			String q = "UPDATE user SET username = ?, name = ?, surname = ?, email = ?, is_admin = ? WHERE id = ?;";
			PreparedStatement ps = connection.prepareStatement(q);
			
			ps.setString(1, u.getUsername());
			ps.setString(2,  u.getName());
			ps.setString(3,  u.getSurname());
			ps.setString(4, u.getEmail());
			ps.setBoolean(5, u.getIsAdmin());
			ps.setLong(6, u.getId());
			
			ps.executeUpdate();
			ps.close(); 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void update(User u, String password) {
		try  {
			String q = "UPDATE user SET username = ?, name = ?, surname = ?, email = ?, password = password(?) WHERE id = ?;";
			PreparedStatement ps = connection.prepareStatement(q);
			
			ps.setString(1, u.getUsername());
			ps.setString(2,  u.getName());
			ps.setString(3,  u.getSurname());
			ps.setString(4, u.getEmail());
			ps.setString(5,  password); 
			ps.setLong(6, u.getId());
			
			ps.executeUpdate();
			ps.close(); 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void delete(User u) {
		try  {
			String q = "DELETE FROM user WHERE id = ?;";
			PreparedStatement ps = connection.prepareStatement(q);
			ps.setLong(1, u.getId());
			ps.executeUpdate();
			ps.close(); 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public Map<String, Integer> getUsersScoreboard() {
		Map<String, Integer> scoreboard = new HashMap<>();
		
		try {
			String query = "SELECT `user`.username, SUM(T.difficulty)\n" + 
					"FROM (SELECT `user`, submission.problem, problem.difficulty\n" + 
					"		FROM submission INNER JOIN problem ON submission.problem = problem.id\n" + 
					"		GROUP BY `user`, submission.problem, problem.difficulty, status\n" + 
					"		HAVING status = 0) AS T INNER JOIN `user` ON T.`user` = `user`.id\n" + 
					"GROUP BY `user`.username, `user`.is_admin\n"
					+ "HAVING `user`.is_admin = FALSE;";
			
			PreparedStatement ps = connection.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			ps.close();
			
			while(rs.next()) {
				String username = rs.getString(1);
				Integer score = rs.getInt(2);
				scoreboard.put(username, score);
			}
						
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return scoreboard;
	}
}
