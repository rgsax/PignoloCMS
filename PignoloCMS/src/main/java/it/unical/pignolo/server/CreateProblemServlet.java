package it.unical.pignolo.server;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
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

public class CreateProblemServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public CreateProblemServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String name = request.getParameter("name");
		String description = request.getParameter("description");
		Double timelimit = Double.parseDouble(request.getParameter("timelimit"));
		Integer difficulty = Integer.parseInt(request.getParameter("difficulty"));
		
		DAOFactory factory = DAOFactory.getInstance();
		
		Connection connection;
		try {
			connection = ServerUtils.getConnection();
		} catch (SQLException e) {
			throw new ServletException(e.getMessage());
		}
		ProblemDAO pd = factory.createProblemDAO(connection);
		Problem problem = new Problem(null, name, difficulty, description, timelimit);
		pd.save(problem);
		
		
		JsonArray ja = (JsonArray)new JsonParser().parse(request.getParameter("testcases"));
		
		List<TestCase> testcases = new ArrayList<>();
		Gson gson = new Gson();
		ja.forEach(je -> {
			TestCase testcase = gson.fromJson(je, TestCase.class);
			testcase.setProblem(problem);
			testcases.add(testcase);
		});
		
		if (testcases.size() > 0) {
			TestCaseDAO tcd = factory.createTestCaseDAO(connection);
			tcd.saveAll(testcases);
		}
		
		List<Tag> tags = new ArrayList<>();
		ja = (JsonArray)new JsonParser().parse(request.getParameter("tags"));
		ja.forEach(je -> {
			tags.add(gson.fromJson(je, Tag.class));
		});
		
		if (tags.size() > 0) {
			TagDAO td = factory.createTagDAO(connection);
			td.associateTagsToProblem(tags, problem);
		}
		
		try {
			connection.close();
		} catch (SQLException e) {
			throw new ServletException(e.getMessage());
		}
		
		response.setContentType("text/plain");
	}

}
