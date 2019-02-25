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

public class LanguageDAO extends DAO<Language> {
	public LanguageDAO(Connection c) {
		super(c);
	}

	@Override
	public Optional<Language> get(Long id) {
		Language language = null;
		
		try {
			String query = "SELECT name, compile_cmd, run_cmd FROM programming_language WHERE id=?;";
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setLong(1, id);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				String name = rs.getString(1);
				String compileCmd = rs.getString(2);
				String runCmd = rs.getString(3);
				language = new Language(id, name, compileCmd, runCmd);
			}
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return Optional.ofNullable(language);
	}

	@Override
	public List<Language> getAll() {
		List<Language> languagees = new ArrayList<>();
		
		try {
			String query = "SELECT * FROM programming_language;";
			PreparedStatement ps = connection.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Long id = rs.getLong(1);
				String name = rs.getString(2);
				String compileCmd = rs.getString(3);
				String runCmd = rs.getString(4);
				languagees.add(new Language(id, name, compileCmd, runCmd));
			}
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return languagees;
	}

	@Override
	public void save(Language language) {
		try {
			String query = "INSERT INTO programming_language(name, compile_cmd, run_cmd) VALUES(?, ?, ?);";
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, language.getName());
			String compileCmd = language.getCompileCmd();
			if(compileCmd != null)
				ps.setString(2, compileCmd);
			else
				ps.setNull(2, Types.NULL);
			ps.setString(3, language.getRunCmd());
			ps.executeUpdate();
			ps.close();
			
			query = "SELECT LAST_INSERT_ID() FROM programming_language;";
			ps = connection.prepareStatement(query);			
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				language.setId(rs.getLong(1));
			}
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

	@Override
	public void update(Language language) {
		try {
			String query = "UPDATE programming_language SET name=?, compile_cmd=?, run_cmd=? WHERE id=?;";
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, language.getName());
			String compileCmd = language.getCompileCmd();
			if(compileCmd != null)
				ps.setString(2, compileCmd);
			else
				ps.setNull(2, Types.NULL);
			ps.setString(3, language.getRunCmd());
			ps.setLong(4, language.getId());
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void delete(Language language) {
		try {
			String query = "DELETE FROM programming_language WHERE id=?;";
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setLong(1, language.getId());
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}	
}
