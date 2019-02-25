package it.unical.pignolo.utils;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import it.unical.pignolo.dao.DAOFactory;
import it.unical.pignolo.model.Language;
import it.unical.pignolo.model.Status;
import it.unical.pignolo.model.Submission;
import it.unical.pignolo.model.TestCase;

public class Python3Solver extends Solver {
	
	public Python3Solver(File source, Language language) {
		super(source, language);
	}

	@Override
	protected Submission executeAndTest(List<TestCase> ts) throws IOException, SQLException {
		Connection connection;
		connection = ServerUtils.getConnection(); 
		
		Status status = DAOFactory.getInstance().createStatusDAO(connection).get(ServerUtils.STATUS_CORRECT).get();
		
		connection.close(); 
		Submission submission = new Submission();
		submission.setLanguage(language);
		
		for(TestCase testcase : ts) {
			ProcessBuilder pb = new ProcessBuilder(language.getRunCmd(), source.getAbsolutePath());
			
			File inputFile = new File(source.getParent() + "/input");
			File outputFile = new File(source.getParent() + "/output");
			inputFile.createNewFile();
			outputFile.createNewFile();
			FileUtils.writeStringToFile(inputFile, testcase.getInput());
			pb.redirectInput(inputFile);
			pb.redirectOutput(outputFile);
			
			Process process = pb.start();
			
			String errors = IOUtils.toString(process.getErrorStream());
			
			if (!errors.isEmpty()) {
		    	// che errore c'è + return + set status
				connection = ServerUtils.getConnection(); 
		    	status = DAOFactory.getInstance().createStatusDAO(connection).get(ServerUtils.STATUS_RUN_ERROR).get();
		    	connection.close();
		    } else {
		    	String output = FileUtils.readFileToString(outputFile);
		    	if(!output.equals(testcase.getOutput())) {
					connection = ServerUtils.getConnection(); 
		    		status = DAOFactory.getInstance().createStatusDAO(connection).get(ServerUtils.STATUS_WRONG).get();
			    	connection.close();

		    		submission.setFirstFailure(testcase);
		    		break;
		    	}
		    }
		}
		
		submission.setStatus(status);
		
		return submission;
	}

}
