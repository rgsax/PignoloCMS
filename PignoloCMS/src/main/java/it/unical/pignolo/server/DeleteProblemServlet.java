package it.unical.pignolo.server;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.unical.pignolo.dao.DAOFactory;
import it.unical.pignolo.model.Problem;
import it.unical.pignolo.utils.ServerUtils;

/**
 * Servlet implementation class DeleteProblemServlet
 */
@WebServlet("/deleteProblem")
public class DeleteProblemServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public DeleteProblemServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/plain");
		Problem p = new Problem(); 
		p.setId(Long.parseLong(request.getParameter("id")));
		
		Connection connection;
		try {
			connection = ServerUtils.getConnection();
		} catch (SQLException e) {
			throw new ServletException(e.getMessage());
		}
		
		DAOFactory.getInstance().createProblemDAO(connection).delete(p);
		
		try {
			connection.close();
		} catch (SQLException e) {
			throw new ServletException(e.getMessage());
		}
	}
}
