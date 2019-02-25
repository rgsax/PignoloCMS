package it.unical.pignolo.server;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.unical.pignolo.dao.DAOFactory;
import it.unical.pignolo.dao.ProblemDAO;
import it.unical.pignolo.model.Problem;
import it.unical.pignolo.utils.ServerUtils;

@WebServlet("/listProblems")
public class ListProblemsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public ListProblemsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/plain");
		Connection connection;
		try {
			connection = ServerUtils.getConnection();
		} catch (SQLException e) {
			throw new ServletException(e.getMessage());
		}
		
		ProblemDAO pd = DAOFactory.getInstance().createProblemDAO(connection);
		List<Problem> ps = pd.getAll();
		
		try {
			connection.close();
		} catch (SQLException e) {
			throw new ServletException(e.getMessage());
		}
		
		request.setAttribute("problems", ps);
	}
}
