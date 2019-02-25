package it.unical.pignolo.server;


import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.unical.pignolo.utils.ServerUtils;

public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public LogoutServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getSession().invalidate();
		response.addCookie(ServerUtils.createInvalidatedCookie(ServerUtils.LOGGED_USER_COOKIE_NAME));
		
		response.sendRedirect(request.getContextPath());
	}
}
