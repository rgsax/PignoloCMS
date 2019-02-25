package it.unical.pignolo.server;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.unical.pignolo.dao.DAOFactory;
import it.unical.pignolo.dao.UserDAO;
import it.unical.pignolo.model.User;
import it.unical.pignolo.utils.ServerUtils;

/**
 * Servlet implementation class LoginServlet
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
    }


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection connection = null; 
		
		try {
			connection = ServerUtils.getConnection(); 
			String username = request.getParameter("username");
			String password = request.getParameter("password");
				
			UserDAO ud = DAOFactory.getInstance().createUserDAO(connection); 
			Optional<User> ou = ud.get(username, password);
			
			if (ou.isPresent()) {
				User user = ou.get();
				
				request.getSession().setAttribute(ServerUtils.LOGGED_USER_SESSION_NAME, user);
				
				Cookie cookie = ServerUtils.createLoggedUserCookie(username);
				response.addCookie(cookie);
				
				String r = request.getContextPath() + "/home"; 
				response.sendRedirect(r);
			} else {
				throw new ServletException("User does not exist OR password mismatch");
			}
		} catch (Exception e) {
			 throw new ServletException(e.getMessage());
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				throw new ServletException(e.getMessage());
			} 
		}
	}

}
