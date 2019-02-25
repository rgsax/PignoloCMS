package it.unical.pignolo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import it.unical.pignolo.model.Problem;
import it.unical.pignolo.model.Tag;

public class TagDAO extends DAO<Tag> {

	public TagDAO(Connection c) {
		super(c);
	}

	@Override
	public Optional<Tag> get(Long id) {
		Tag tag = null;
		try {
			String query = "SELECT name FROM tag WHERE id=?;";
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setLong(1, id);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				String name = rs.getString(1);
				tag = new Tag(id, name);
			}
			
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return Optional.ofNullable(tag);
	}

	@Override
	public List<Tag> getAll() {
		List<Tag> tags = new ArrayList<>();
		
		try {
			String query = "SELECT * from tag;";
			PreparedStatement ps = connection.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				Long id = rs.getLong(1);
				String name = rs.getString(2);
				
				tags.add(new Tag(id, name));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return tags;
	}

	@Override
	public void save(Tag tag) {
		try {
			String query = "INSERT INTO tag(name) VALUES(?);";
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, tag.getName());
			ps.executeUpdate();
			ps.close();
			
			query = "SELECT LAST_INSERT_ID() FROM user;";
			ps = connection.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				tag.setId(rs.getLong(1));
			}
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(Tag tag) {
		try {
			String query = "UPDATE tag SET name=? WHERE id=?;";
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, tag.getName());
			ps.setLong(2, tag.getId());
			ps.executeUpdate();
			ps.close();			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete(Tag tag) {
		try {
			String query = "DELETE FROM tag WHERE id=?";
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setLong(1, tag.getId());
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List<Tag> getProblemTags(Problem problem) {
		List<Tag> tags = new ArrayList<>();
		
		try {
			String query = "SELECT tag, name FROM problem_tag INNER JOIN tag ON problem_tag.tag = tag.id WHERE problem=?;";
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setLong(1, problem.getId());
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				Long id = rs.getLong(1);
				String name = rs.getString(2);
				
				tags.add(new Tag(id, name));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return tags;
	}
	
	public void associateTagsToProblem(List<Tag> ts, Problem p) {
		try {
			String q = "DELETE FROM problem_tag WHERE problem = " + p.getId() + ";"; 
			PreparedStatement ps = connection.prepareStatement(q);
			ps.executeUpdate();
			
			q = "INSERT INTO problem_tag(problem, tag) VALUES ";
			StringBuilder sb = new StringBuilder(q); 
			
			ts.forEach(t -> {
				sb.append("(")
					.append(p.getId().toString())
					.append(", ")
					.append(t.getId().toString())
					.append("), ");
			});
			sb.replace(sb.length() - 2, sb.length() - 1, ";");

			ps = connection.prepareStatement(sb.toString());
			ps.executeUpdate();
			ps.close(); 
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
