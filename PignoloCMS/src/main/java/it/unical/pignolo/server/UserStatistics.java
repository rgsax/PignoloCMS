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
import it.unical.pignolo.dao.ProblemStatisticsDAO;
import it.unical.pignolo.model.User;
import it.unical.pignolo.utils.ServerUtils;

/**
 * Servlet implementation class UserStatistics
 */
@WebServlet("/userStatistics")
public class UserStatistics extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public UserStatistics() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection c = null; 
		try {
			c = ServerUtils.getConnection(); 
			Long userId = ((User) request.getSession(false).getAttribute(ServerUtils.LOGGED_USER_SESSION_NAME)).getId();
			response.setContentType("application/json");
			
			ProblemStatisticsDAO pd = DAOFactory.getInstance().createProblemStatisticsDAO(c); 
			int accepted = pd.userAcceptedAnswers(userId);
			int wrong = pd.userWrongAnswers(userId);
			int runerror = pd.userRunErrorAnswers(userId);
			int timelimit = pd.userTimelimitAnswers(userId); 
			int compilationerror = pd.userCompilationErrorAnswers(userId); 
			
			String json = "{ \"accepted\":" + accepted + ", \"wrong\":" + wrong + ", \"runerror\":" + runerror + ", \"timelimit\":"+ timelimit
					+ ", \"compilationerror\":" + compilationerror + "}";
			response.getWriter().write(json);
		} catch (Exception e) {
			throw new ServletException(e.getMessage());
		} finally {
			if (c != null)
				try {
					c.close();
				} catch (SQLException e) {
					throw new ServletException(e.getMessage());
				} 
		}
	}
}
