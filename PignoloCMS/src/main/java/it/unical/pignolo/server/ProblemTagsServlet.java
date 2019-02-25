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

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import it.unical.pignolo.dao.DAOFactory;
import it.unical.pignolo.dao.ProblemProxy;
import it.unical.pignolo.dao.TagDAO;
import it.unical.pignolo.model.Problem;
import it.unical.pignolo.model.Tag;
import it.unical.pignolo.utils.ServerUtils;

/**
 * Servlet implementation class ProblemTagsServlet
 */
@WebServlet("/getProblemTags")
public class ProblemTagsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public ProblemTagsServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection connection = null; 
		
		try {
			response.setContentType("application/json");
			
			connection = ServerUtils.getConnection();
			
			Problem p = new ProblemProxy(); 
			p.setId(Long.parseLong(request.getParameter("id")));
							
			Gson g = new Gson(); 
			JsonArray ja = new JsonArray();
			
			TagDAO td = DAOFactory.getInstance().createTagDAO(connection);
			List<Tag> problemTags = td.getProblemTags(p);
			List<Tag> allTags = td.getAll();
			
			allTags.forEach(tag -> {
				JsonElement je = g.toJsonTree(tag, Tag.class);
				boolean hasTag = problemTags.contains(tag);
				je.getAsJsonObject().addProperty("is_checked", hasTag);
				ja.add(je);
			});
			
			response.getWriter().print(ja);
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
