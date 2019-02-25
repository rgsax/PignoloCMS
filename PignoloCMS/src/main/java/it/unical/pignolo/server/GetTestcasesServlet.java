package it.unical.pignolo.server;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

import it.unical.pignolo.dao.ProblemProxy;
import it.unical.pignolo.model.Problem;
import it.unical.pignolo.model.TestCase;

@WebServlet("/getTestcases")
public class GetTestcasesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public GetTestcasesServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		Problem p = new ProblemProxy();
		p.setId(Long.parseLong(request.getParameter("id")));
				
		Gson g = new Gson();
		JsonArray ja = new JsonArray();
		
		List<TestCase> ts = p.getProblemTestCases();		
		ts.forEach(t -> {
			t.setProblem(null);	
			ja.add(g.toJson(t));
		});
		
		response.getWriter().print(ja);
	}
}
