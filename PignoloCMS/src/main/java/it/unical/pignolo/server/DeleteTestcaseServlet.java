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
import it.unical.pignolo.dao.TestCaseDAO;
import it.unical.pignolo.model.TestCase;
import it.unical.pignolo.utils.ServerUtils;

/**
 * Servlet implementation class DeleteTestcaseServlet
 */
@WebServlet("/deleteTestcase")
public class DeleteTestcaseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public DeleteTestcaseServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("plain/text");
		
		Connection connection;
		try {
			connection = ServerUtils.getConnection();
		} catch (SQLException e) {
			throw new ServletException(e.getMessage());
		}
		
		TestCaseDAO tc = DAOFactory.getInstance().createTestCaseDAO(connection); 
		
		TestCase t = new TestCase(); 
		t.setId( Long.parseLong(request.getParameter("id")) ); 
		
		tc.delete(t);
		try {
			connection.close();
		} catch (SQLException e) {
			throw new ServletException(e.getMessage());
		}
	}


}
