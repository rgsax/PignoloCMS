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
import it.unical.pignolo.model.Submission;
import it.unical.pignolo.model.User;
import it.unical.pignolo.utils.ServerUtils;

@WebServlet("/userProfile")
public class UserProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public UserProfileServlet() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		if(username != null) {
			Connection connection;
			try {
				connection = ServerUtils.getConnection();
			} catch (SQLException e) {
				throw new ServletException(e.getMessage());
			}
			User userProfile = DAOFactory.getInstance().createUserDAO(connection).get(username).get();
			List<Submission> submissions = DAOFactory.getInstance().createSubmissionDAO(connection).getUserSubmissions(userProfile);
			try {
				connection.close();
			} catch (SQLException e) {
				throw new ServletException(e.getMessage());
			}
			
			request.setAttribute("userProfile", userProfile);
			request.setAttribute("submissions", submissions);
			request.getRequestDispatcher("WEB-INF/userProfile.jsp").forward(request, response);
		}
	}
}
