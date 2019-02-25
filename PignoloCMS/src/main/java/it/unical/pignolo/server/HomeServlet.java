package it.unical.pignolo.server;


import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.unical.pignolo.dao.DAOFactory;
import it.unical.pignolo.dao.ProblemDAO;
import it.unical.pignolo.model.Problem;
import it.unical.pignolo.utils.ServerUtils;

public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public HomeServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		Connection connection;
		try {
			connection = ServerUtils.getConnection();
		} catch (SQLException e1) {
			throw new ServletException(e1.getMessage());
		}
		
		ProblemDAO pd = DAOFactory.getInstance().createProblemDAO(connection);
		if (request.getSession().getAttribute("hotProblems") == null) {
			Map<Problem, Integer> hotProblems = pd.problemSubmissionCount();
			request.setAttribute("hotProblems" , hotProblems);
		}
		
		try {
			connection.close();
		} catch (SQLException e) {
			throw new ServletException(e.getMessage());
		}
		
		
		request.getRequestDispatcher("/listSubmissions").include(request, response);
		request.getRequestDispatcher("WEB-INF/home.jsp").forward(request, response);
	}
}
