package it.unical.pignolo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import it.unical.pignolo.model.Status;

public class StatusDAO extends DAO<Status> {
	public StatusDAO(Connection c) {
		super(c);
	}

	@Override
	public Optional<Status> get(Long id) {
		Status status = null;
		
		try {
			String query = "SELECT name FROM status WHERE id=?;";
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setLong(1, id);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				String name = rs.getString(1);
				status = new Status(id, name);
			}
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return Optional.ofNullable(status);
	}

	@Override
	public List<Status> getAll() {
		List<Status> statuses = new ArrayList<>();
		
		try {
			String query = "SELECT * FROM status;";
			PreparedStatement ps = connection.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Long id = rs.getLong(1);
				String name = rs.getString(2);
				statuses.add(new Status(id, name));
			}
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return statuses;
	}

	@Override
	public void save(Status status) {
		try {
			String query = "INSERT INTO status(name) VALUES(?);";
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, status.getName());
			ps.executeUpdate();
			ps.close();
			
			query = "SELECT LAST_INSERT_ID() FROM status;";
			ps = connection.prepareStatement(query);			
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				status.setId(rs.getLong(1));
			}
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}

	@Override
	public void update(Status status) {
		try {
			String query = "UPDATE status SET name=? WHERE id=?;";
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, status.getName());
			ps.setLong(2, status.getId());
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete(Status status) {
		try {
			String query = "DELETE FROM status WHERE id=?;";
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setLong(1, status.getId());
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}	
}
