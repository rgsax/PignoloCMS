package it.unical.pignolo.server;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import it.unical.pignolo.dao.DAOFactory;
import it.unical.pignolo.dao.SubmissionDAO;
import it.unical.pignolo.model.Language;
import it.unical.pignolo.model.Submission;
import it.unical.pignolo.utils.ServerUtils;

@WebServlet("/getSourcecode")
public class GetSourcecodeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public GetSourcecodeServlet() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { 
		response.setContentType("text/plain");
		Long id = Long.parseLong(request.getParameter("submission"));
		Connection connection = null; 
			
		try {
			connection = ServerUtils.getConnection();
			SubmissionDAO sd = DAOFactory.getInstance().createSubmissionDAO(connection);
			Submission submission = sd.get(id).get();
			
			String pastebinURL = sd.pastebinLink(submission); 
			if (pastebinURL == null) {
				Language language = submission.getLanguage();
				String languageFormat = "";
				if (language.getId().equals(ServerUtils.LANGUAGE_CPP))
					languageFormat = "cpp";
				else if (language.getId().equals(ServerUtils.LANGUAGE_PYTHON3))
					languageFormat = "python";
				
				pastebinURL = Unirest.post("https://pastebin.com/api/api_post.php")
					.field("api_dev_key", "11806c4e16bce2a24bd6633f3e2cc145")
					.field("api_option", "paste")
					.field("api_paste_format", languageFormat)
					.field("api_paste_code", submission.getSource()).asString().getBody();
				
				if (!pastebinURL.contains("Post limit, maximum pastes per 24h reached")) {
					sd.createPastebinLink(submission, pastebinURL);
				}
			}

			response.getWriter().print(pastebinURL);
			
		} catch (SQLException | UnirestException e) {
			throw new ServletException(e.getMessage());
		} finally {
			if (connection != null)
				try {
					connection.close();
				} catch (SQLException e) {
					throw new ServletException(e.getMessage());
				} 
		}
	}

}
