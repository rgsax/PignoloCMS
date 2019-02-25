package it.unical.pignolo.server;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.unical.pignolo.dao.DAOFactory;
import it.unical.pignolo.utils.ServerUtils;

@WebServlet("/scoreboard")
public class ScoreboardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ScoreboardServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection connection;
		try {
			connection = ServerUtils.getConnection();
			Map<String, Integer> scoreboard = DAOFactory.getInstance().createUserDAO(connection).getUsersScoreboard();
			connection.close();
			
			request.setAttribute("scoreboard", scoreboard);
			request.getRequestDispatcher("WEB-INF/scoreboard.jsp").forward(request, response);
		} catch(SQLException e) {
			throw new ServletException(e.getMessage());
		}
	}
}
