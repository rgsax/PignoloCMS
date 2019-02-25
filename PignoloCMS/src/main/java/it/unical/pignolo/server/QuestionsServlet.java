package it.unical.pignolo.server;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.unical.pignolo.dao.DAOFactory;
import it.unical.pignolo.model.Question;
import it.unical.pignolo.utils.ServerUtils;

@WebServlet("/questions")
public class QuestionsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public QuestionsServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Connection connection;
		try {
			connection = ServerUtils.getConnection();
			List<Question> questions = DAOFactory.getInstance().createQuestionDAO(connection).getAll();
			request.setAttribute("questions", questions);
			request.getRequestDispatcher("WEB-INF/questions.jsp").forward(request, response);
		} catch (SQLException e) {
			throw new ServletException(e.getMessage());
		}
		
	}

}
