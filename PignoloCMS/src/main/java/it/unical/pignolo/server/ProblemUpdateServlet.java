package it.unical.pignolo.server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/problemUpdate")
public class ProblemUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public ProblemUpdateServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/htlm");
		request.getRequestDispatcher("/listProblems").include(request, response);
		request.getRequestDispatcher("WEB-INF/problemUpdate.jsp").forward(request, response);
	}
}
