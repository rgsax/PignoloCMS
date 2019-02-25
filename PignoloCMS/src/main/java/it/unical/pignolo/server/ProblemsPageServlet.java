package it.unical.pignolo.server;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.unical.pignolo.dao.AnswerDAO;
import it.unical.pignolo.dao.DAOFactory;
import it.unical.pignolo.dao.ProblemDAO;
import it.unical.pignolo.dao.QuestionDAO;
import it.unical.pignolo.model.Answer;
import it.unical.pignolo.model.Language;
import it.unical.pignolo.model.Problem;
import it.unical.pignolo.model.Question;
import it.unical.pignolo.model.Tag;
import it.unical.pignolo.model.User;
import it.unical.pignolo.utils.ServerUtils;

@WebServlet("/problems")
public class ProblemsPageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public ProblemsPageServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection connection = null; 
		try  {
			connection = ServerUtils.getConnection();
			response.setContentType("text/html");
			
			Optional<String> oid = Optional.ofNullable(request.getParameter("id"));
			
			if (oid.isPresent()) {
				DAOFactory factory = DAOFactory.getInstance();
				Long id = Long.parseLong(oid.get()); 
				ProblemDAO pd = factory.createProblemDAO(connection);
				QuestionDAO qd = factory.createQuestionDAO(connection); 
				AnswerDAO ad = factory.createAnswerDAO(connection); 
				
				Problem p = pd.get(id).get(); 
				request.setAttribute("problem", p);
				
				List<Language> languages = factory.createLanguageDAO(connection).getAll();
				request.setAttribute("languages", languages);
				
				List<Tag> tags = p.getProblemTags();
				request.setAttribute("tags", tags);
				
				List<Question> qs = qd.getProblemQuestions(p);
				Map<Question, List<Answer>> questionAnswerMap = new HashMap<>(); 
				for (Question q: qs) {
					if (q.getUser().getId().equals(
							((User) request.getSession(false).getAttribute(ServerUtils.LOGGED_USER_SESSION_NAME)).getId())) {
						List<Answer> answers = ad.answersToQuestion(q);
						questionAnswerMap.put(q, answers); 
					}
				}
				
				request.setAttribute("answers", questionAnswerMap);
				request.getRequestDispatcher("WEB-INF/problem.jsp").forward(request, response);
				
			} else {
				request.getRequestDispatcher("/listProblems").include(request, response);
				request.getRequestDispatcher("WEB-INF/problems.jsp").forward(request, response);
			}	
			
		} catch (Exception e) {
			throw new ServletException(e.getMessage());
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				throw new ServletException(e.getMessage());
			} 
		}
	}
}
