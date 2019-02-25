package it.unical.pignolo.server;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.unical.pignolo.dao.DAOFactory;
import it.unical.pignolo.model.Tag;
import it.unical.pignolo.utils.ServerUtils;

public class AdministrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public AdministrationServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection connection;
		try {
			connection = ServerUtils.getConnection();
		} catch (SQLException e) {
			throw new ServletException(e.getMessage());
		} 
		
		response.setContentType("text/html");
		
		List<Tag> tags = DAOFactory.getInstance().createTagDAO(connection).getAll();
		request.setAttribute("tag_list", tags);
		
		try {
			connection.close();
		} catch (SQLException e) {
			throw new ServletException(e.getMessage());
		} 
		
		request.getRequestDispatcher("WEB-INF/administration.jsp").forward(request, response);
	}
}
