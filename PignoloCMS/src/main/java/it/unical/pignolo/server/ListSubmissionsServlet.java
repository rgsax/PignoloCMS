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
import it.unical.pignolo.dao.SubmissionDAO;
import it.unical.pignolo.model.Submission;
import it.unical.pignolo.model.User;
import it.unical.pignolo.utils.ServerUtils;

@WebServlet("/listSubmissions")
public class ListSubmissionsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public ListSubmissionsServlet() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection connection;
		try {
			connection = ServerUtils.getConnection();
		} catch (SQLException e1) {
			throw new ServletException(e1.getMessage());
		}
		SubmissionDAO sd = DAOFactory.getInstance().createSubmissionDAO(connection);
		List<Submission> submissions = sd.getUserSubmissions((User)request.getSession(false).getAttribute(ServerUtils.LOGGED_USER_SESSION_NAME));
		try {
			connection.close();
		} catch (SQLException e) {
			throw new ServletException(e.getMessage());
		}
		request.setAttribute("submissions", submissions);
		//request.getRequestDispatcher("WEB-INF/userSubmissions.jsp").forward(request, response);
	}

}
