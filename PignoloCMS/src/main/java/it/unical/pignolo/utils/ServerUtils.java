package it.unical.pignolo.utils;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.Cookie;

public class ServerUtils {
	private ServerUtils() { }

	private static final String[] allowedURLs = {
		"login",
		"registration.html",
		"register"
	};
	
	public static final Connection getConnection() throws SQLException {
		return (new DataSource()).getConnection();
	}
	
	public static final String LOGGED_USER_COOKIE_NAME = "user_cookie";
	public static final String LOGGED_USER_SESSION_NAME = "user";
	public static final Long STATUS_CORRECT = new Long(0);
	public static final Long STATUS_WRONG = new Long(1);
	public static final Long STATUS_TIMELIMIT = new Long(2);
	public static final Long STATUS_COMPILER_ERROR = new Long(3);
	public static final Long STATUS_RUN_ERROR = new Long(4);
	public static final Long LANGUAGE_CPP = new Long(12);
	public static final Long LANGUAGE_PYTHON3 = new Long(1);
	
	public static Cookie createLoggedUserCookie(String nickname) {
		Cookie cookie = new Cookie(LOGGED_USER_COOKIE_NAME, nickname);
		cookie.setMaxAge(60 * 60 * 24 * 365);
		return cookie;
	}
	
	public static Cookie createInvalidatedCookie(String name) {
		Cookie cookie = new Cookie(name, "");
		cookie.setMaxAge(0);
		return cookie;
	}
	
	public static boolean isAllowed(String url) {
		for(String allowedUrl : allowedURLs) {
			if(url.endsWith(allowedUrl))
				return true;
		}
		
		return false;
	}
}
