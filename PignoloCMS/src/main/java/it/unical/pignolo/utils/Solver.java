package it.unical.pignolo.utils;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.io.IOUtils;

import it.unical.pignolo.dao.DAOFactory;
import it.unical.pignolo.model.Language;
import it.unical.pignolo.model.Status;
import it.unical.pignolo.model.Submission;
import it.unical.pignolo.model.TestCase;

public abstract class Solver {
	protected Language language;
	protected File source;
	
	public Solver() { }
	
	public Solver(File source, Language language) {
		this.source = source;
		this.language = language;
	}
	
	protected Status compile() throws IOException {
		Status status = null;
		String compileCmd = language.getCompileCmd();
		
		if(compileCmd != null) {
			String path = source.getAbsolutePath();
			ProcessBuilder pb = new ProcessBuilder(compileCmd, path, "-o", path.substring(0, path.lastIndexOf('.')));
			
			Process compileProcess = pb.start(); 
			
		    if(!IOUtils.toString(compileProcess.getErrorStream()).isEmpty()) {
		    	Connection connection;
				try {
					connection = ServerUtils.getConnection();
		    		status = DAOFactory.getInstance().createStatusDAO(connection).get(new Long(3)).get();//COMPILER_ERROR
		    		connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}

	    	}
		} 
			
		return status;
	}
	
	protected abstract Submission executeAndTest(List<TestCase> ts) throws IOException, SQLException;
	
	public Submission solve(List<TestCase> testcases) throws IOException, SQLException {
		Status status = compile();
		if(status != null) {
			Submission submission = new Submission();
			submission.setStatus(status);
			submission.setLanguage(language);
			return submission;
		} 
		
		return executeAndTest(testcases);
	}
}
