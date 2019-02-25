package it.unical.pignolo.server;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.unical.pignolo.dao.DAOFactory;
import it.unical.pignolo.dao.UserDAO;
import it.unical.pignolo.model.User;
import it.unical.pignolo.utils.ServerUtils;

@WebServlet("/updateUser")
public class UpdateUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public UpdateUserServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection c = null; 
		try {
			response.setContentType("text/plain");
			c = ServerUtils.getConnection(); 
			UserDAO ud = DAOFactory.getInstance().createUserDAO(c);
			Optional<User> currentUserOpt = ud.get(request.getParameter("username"), request.getParameter("old_password"));
			if ( currentUserOpt.isPresent() ) {
				// update dei campi 
				User currentUser = currentUserOpt.get(); 			
				currentUser.setName(request.getParameter("name"));
				currentUser.setSurname(request.getParameter("surname"));
				currentUser.setEmail(request.getParameter("email"));
				
				String np = request.getParameter("new_password");
				
				if (np.length() > 0) {
					ud.update(currentUser, np);
				} else {
					ud.update(currentUser);
				}
				
				request.getSession(false).setAttribute(ServerUtils.LOGGED_USER_SESSION_NAME, currentUser);
			} else {
				response.getWriter().print("Password errata!");
			}
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
