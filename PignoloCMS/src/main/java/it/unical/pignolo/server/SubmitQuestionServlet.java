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
import it.unical.pignolo.model.Problem;
import it.unical.pignolo.model.Question;
import it.unical.pignolo.model.User;
import it.unical.pignolo.utils.ServerUtils;

@WebServlet("/submitQuestion")
public class SubmitQuestionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public SubmitQuestionServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user = (User)request.getSession(false).getAttribute(ServerUtils.LOGGED_USER_SESSION_NAME);
		
		Connection connection;
		try {
			connection = ServerUtils.getConnection();
			Problem problem = DAOFactory.getInstance().createProblemDAO(connection).get(Long.parseLong(request.getParameter("problem"))).get();
			
			
			Question question = new Question();
			question.setProblem(problem);
			question.setUser(user);
			question.setQuestion(request.getParameter("question"));
			
			DAOFactory.getInstance().createQuestionDAO(connection).save(question);
			
			connection.close();
		} catch (SQLException e) {
			throw new ServletException(e.getMessage());
		}		
	}
}
