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
 * Servlet implementation class ProblemStatisticsServlet
 */
@WebServlet("/problemStatistics")
public class ProblemStatisticsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public ProblemStatisticsServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection c = null; 
		try {
		c = ServerUtils.getConnection(); 
		response.setContentType("application/json");
		Long problemId = Long.parseLong(request.getParameter("problemId"));
		Long userId = ((User) request.getSession(false).getAttribute(ServerUtils.LOGGED_USER_SESSION_NAME)).getId();
		ProblemStatisticsDAO psd = DAOFactory.getInstance().createProblemStatisticsDAO(c); 
		 
		// correct-submissions
		int CS = psd.getAcceptedSubmissionsForProblem(problemId); 
		// total-submissions
		int TS = psd.getTotalSubmissionsForProblem(problemId); 
		// avg-submissions
		int AS = psd.getAvgSubmissionsForProblem(problemId); 
		// logged-user-submissions
		int US = psd.getUserSubmissionsForProblem(problemId, userId); 
		// solved-by-user
		boolean S = psd.userHasSolvedProblem(problemId, userId);
		
		String json = "{\"avg\": " + AS + ",\"correct\": " + CS + ", \"total\": " + TS + ", \"user\": " + US + ", \"status\": " + S +"}";
		response.getWriter().write(json);
		} catch(Exception e) {
			throw new ServletException(); 
		} finally {
			try {
				if ( c != null ) c.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} 
		}
	}

}
