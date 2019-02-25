package it.unical.pignolo.server;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import it.unical.pignolo.dao.DAOFactory;
import it.unical.pignolo.dao.ProblemDAO;
import it.unical.pignolo.dao.TagDAO;
import it.unical.pignolo.dao.TestCaseDAO;
import it.unical.pignolo.model.Problem;
import it.unical.pignolo.model.Tag;
import it.unical.pignolo.model.TestCase;
import it.unical.pignolo.utils.ServerUtils;

@WebServlet("/updateProblem")
public class UpdateProblemServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public UpdateProblemServlet() {
        super();
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	Connection connection = null; 
    	try {
        	connection = ServerUtils.getConnection(); 
	    	response.setContentType("text/plain");
			
			Problem p = new Problem(); 
			p.setDescription(request.getParameter("description"));
			p.setDifficulty(Integer.parseInt(request.getParameter("difficulty")));
			p.setId(Long.parseLong(request.getParameter("id")));
			p.setName(request.getParameter("name"));
			p.setTimelimit(Double.parseDouble(request.getParameter("timelimit")));
	
			ProblemDAO pd = DAOFactory.getInstance().createProblemDAO(connection);
			pd.update(p);
			
			Gson gson = new Gson(); 
			JsonArray ja = (JsonArray) new JsonParser().parse(request.getParameter("testcases"));
			
			TestCaseDAO td = DAOFactory.getInstance().createTestCaseDAO(connection);
			
			ja.forEach(jsonTestcase -> {
				TestCase t = gson.fromJson(jsonTestcase, TestCase.class);
				t.setProblem(p);
				
				td.update(t);
			});
			
			List<TestCase> ts = new ArrayList<TestCase>(); 
			ja = (JsonArray) new JsonParser().parse(request.getParameter("new_testcases"));
			ja.forEach(jsonTestcase -> {
				TestCase t = gson.fromJson(jsonTestcase, TestCase.class);
				t.setProblem(p);
				
				ts.add(t);
			});
			
			td.saveAll(ts);
			
			ja = (JsonArray) new JsonParser().parse(request.getParameter("tags")); 
			TagDAO tagDao = DAOFactory.getInstance().createTagDAO(connection);  
			
			List<Tag> tagList = new ArrayList<>(); 
			ja.forEach(tag -> {
				tagList.add(gson.fromJson(tag, Tag.class)); 
			});
			
			if(!tagList.isEmpty())
				tagDao.associateTagsToProblem(tagList, p);
		}   catch(Exception e) {
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
