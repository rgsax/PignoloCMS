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
import it.unical.pignolo.dao.QuestionDAO;
import it.unical.pignolo.model.Question;
import it.unical.pignolo.utils.ServerUtils;

@WebServlet("/deleteQuestion")
public class DeleteQuestionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public DeleteQuestionServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection connection = null; 
		try {
			connection = ServerUtils.getConnection(); 
			QuestionDAO qd = DAOFactory.getInstance().createQuestionDAO(connection); 
			Long id = Long.parseLong(request.getParameter("question_id"));
		
			Question question = new Question(); 
			question.setId(id);
			qd.delete(question);
		} catch (Exception e) {
			throw new ServletException(e.getMessage());  
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					throw new ServletException(e.getMessage()); 
				} 
			}
		}
	}
}
