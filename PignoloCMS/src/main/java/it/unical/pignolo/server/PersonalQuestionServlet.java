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
import it.unical.pignolo.model.Question;
import it.unical.pignolo.model.User;
import it.unical.pignolo.utils.ServerUtils;

@WebServlet("/personalQuestions")
public class PersonalQuestionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public PersonalQuestionServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection connection = null; 
		try {
			connection = ServerUtils.getConnection();
			User u = (User) request.getSession(false).getAttribute(ServerUtils.LOGGED_USER_SESSION_NAME);
			List<Question> qs = DAOFactory.getInstance().createQuestionDAO(connection).getUserQuestions(u); 

			request.setAttribute("questions", qs);
			request.getRequestDispatcher("WEB-INF/personalQuestions.jsp").forward(request, response);
		} catch (Exception e) {
			throw new ServletException(e.getMessage()); 
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					throw new ServletException(e.getMessage());
				}	
			}
		}
	}
}
