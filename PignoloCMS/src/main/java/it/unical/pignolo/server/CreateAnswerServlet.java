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
import it.unical.pignolo.model.Answer;
import it.unical.pignolo.model.Question;
import it.unical.pignolo.model.User;
import it.unical.pignolo.utils.ServerUtils;

@WebServlet("/createAnswer")
public class CreateAnswerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public CreateAnswerServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			Connection connection = ServerUtils.getConnection();
			User user = (User)request.getSession(false).getAttribute(ServerUtils.LOGGED_USER_SESSION_NAME);
			Question question = DAOFactory.getInstance().createQuestionDAO(connection).get(Long.parseLong(request.getParameter("question"))).get();
			String answerText = request.getParameter("answer");
			Answer answer = new Answer();
			answer.setAnswer(answerText);
			answer.setUser(user);
			answer.setQuestion(question);
			
			DAOFactory.getInstance().createAnswerDAO(connection).save(answer);
			
			connection.close();
			
			response.setContentType("text/plain");
			
		} catch (SQLException e) {
			throw new ServletException(e.getMessage());
		}
	}

}
