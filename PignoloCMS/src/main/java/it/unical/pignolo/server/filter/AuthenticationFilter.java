package it.unical.pignolo.server.filter;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import it.unical.pignolo.dao.DAOFactory;
import it.unical.pignolo.model.User;
import it.unical.pignolo.utils.ServerUtils;


public class AuthenticationFilter implements Filter {
	
    public AuthenticationFilter() {
        
    }

	public void destroy() {
		
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = ((HttpServletRequest)request);
		HttpSession session = req.getSession(false);
		
		
		if(session == null || session.getAttribute(ServerUtils.LOGGED_USER_SESSION_NAME) == null) {
			
			Cookie[] cookies = req.getCookies();
			if(cookies != null) {
				for(Cookie cookie : cookies) {
					if(cookie.getName().equals(ServerUtils.LOGGED_USER_COOKIE_NAME)) {
						session = req.getSession(true);
						Connection connection;
						try {
							connection = ServerUtils.getConnection();
						} catch (SQLException e) {
							throw new ServletException(e.getMessage());
						}
						User user = DAOFactory.getInstance().createUserDAO(connection).get(cookie.getValue()).get();
						try {
							connection.close();
						} catch (SQLException e) {
							throw new ServletException(e.getMessage());
						}
						session.setAttribute(ServerUtils.LOGGED_USER_SESSION_NAME, user);
						break;
					}
				}
			}
		}

		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
		
	}

}
