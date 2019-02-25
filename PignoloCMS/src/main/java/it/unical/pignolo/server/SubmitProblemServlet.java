package it.unical.pignolo.server;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.io.FileUtils;

import it.unical.pignolo.dao.DAOFactory;
import it.unical.pignolo.dao.ProblemDAO;
import it.unical.pignolo.model.Language;
import it.unical.pignolo.model.Problem;
import it.unical.pignolo.model.Status;
import it.unical.pignolo.model.Submission;
import it.unical.pignolo.model.TestCase;
import it.unical.pignolo.model.User;
import it.unical.pignolo.utils.CppSolver;
import it.unical.pignolo.utils.Python3Solver;
import it.unical.pignolo.utils.ServerUtils;
import it.unical.pignolo.utils.Solver;

@WebServlet("/submitProblem")
@MultipartConfig
public class SubmitProblemServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private DAOFactory factory = DAOFactory.getInstance();

    public SubmitProblemServlet() {
        super();
    }
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection connection = null; 
		
		try {
			Part part = request.getPart("source");
			String filename = Paths.get(part.getSubmittedFileName()).getFileName().toString();
			
			File dest = File.createTempFile("temp_dir", "");
			dest.delete();
			dest.mkdir();
			File source = new File(dest.getAbsolutePath()+"/"+filename);
			source.createNewFile();
			
		    FileUtils.copyInputStreamToFile(part.getInputStream(), source);
	//		FileUtils.copyFileToDirectory(source, dest);
		    
		    connection = ServerUtils.getConnection();
		    
		    Language language = factory.createLanguageDAO(connection).get(Long.parseLong(request.getParameter("language"))).get();
		    
		    Solver solver = null;
		    if(language.getId().equals(ServerUtils.LANGUAGE_PYTHON3)) {
		    	solver = new Python3Solver(source, language);
		    }
		    else if(language.getId().equals(ServerUtils.LANGUAGE_CPP)) {
		    	solver = new CppSolver(source, language);
		    }
		    
		    ProblemDAO pd = DAOFactory.getInstance().createProblemDAO(connection);
		    Problem p = pd.get( Long.parseLong(request.getParameter("problem")) ).get();
		    
		    List<TestCase> ts = DAOFactory.getInstance().createTestCaseDAO(connection).getProblemTestCases(p);
		    
		    
		    Submission submission = solver.solve(ts);
		    submission.setProblem(p);
		    submission.setUser((User)request.getSession().getAttribute(ServerUtils.LOGGED_USER_SESSION_NAME));
		    submission.setSource(FileUtils.readFileToString(source));
		    
		    response.setContentType("text/plain");
		    Status status = submission.getStatus();
		    
		    if(status != null) {
		    	response.getWriter().print(status.getName());
		    }
		    
		    FileUtils.deleteDirectory(dest);
		    
		    factory.createSubmissionDAO(connection).save(submission);
		} catch (Exception e) {
			 throw new ServletException(e.getMessage());
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				throw new ServletException(e.getMessage());
			} 
		}
	}
}
