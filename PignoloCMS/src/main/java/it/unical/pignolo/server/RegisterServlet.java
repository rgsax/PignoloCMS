package it.unical.pignolo.server;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.unical.pignolo.dao.DAOFactory;
import it.unical.pignolo.dao.UserDAO;
import it.unical.pignolo.model.User;
import it.unical.pignolo.utils.ServerUtils;

/**
 * Servlet implementation class RegisterServlet
 */
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection connection = null; 
		
		try {
			User user = new User();
			
			response.setContentType("text/html");
			
			connection = ServerUtils.getConnection();
			
			UserDAO ud = DAOFactory.getInstance().createUserDAO(connection);
			String username = request.getParameter("username");
			if(ud.get(username).isPresent())
				throw new ServletException("user " + username + " already exists");
			
			user.setUsername(username);
			user.setName(request.getParameter("name"));
			user.setSurname(request.getParameter("surname"));
			user.setEmail(request.getParameter("email"));
			String password = request.getParameter("password");
			ud.save(user, password);		
			
			response.getWriter().println("<span>Utente " + username + " registrato con successo</span>");
			request.getRequestDispatcher("index.jsp").include(request, response);
		} catch (Exception e) {
			throw new ServletException(e.getMessage());
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}	
	}

}
